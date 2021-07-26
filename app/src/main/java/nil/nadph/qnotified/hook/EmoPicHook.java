package nil.nadph.qnotified.hook;

import android.app.Application;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import nil.nadph.qnotified.SyncUtils;
import nil.nadph.qnotified.bridge.AIOUtilsImpl;
import nil.nadph.qnotified.config.ConfigManager;
import nil.nadph.qnotified.step.DexDeobfStep;
import nil.nadph.qnotified.step.Step;
import nil.nadph.qnotified.util.DexKit;
import nil.nadph.qnotified.util.LicenseStatus;
import nil.nadph.qnotified.util.Utils;

import java.lang.reflect.Field;

import static nil.nadph.qnotified.util.Initiator._PicItemBuilder;
import static nil.nadph.qnotified.util.Utils.*;

public class EmoPicHook extends BaseDelayableHook {

    public static final String qn_sticker_as_pic = "qn_sticker_as_pic";
    private static final EmoPicHook self = new EmoPicHook();
    private boolean inited = false;

    private EmoPicHook() {
    }

    public static EmoPicHook get() {
        return self;
    }

    @Override
    public boolean init() {
        if (inited) return true;
        try {
            final ConfigManager cfg = ConfigManager.getDefaultConfig();
            boolean canInit = checkPreconditions();
            if (!canInit && ConfigManager.getDefaultConfig().getBooleanOrFalse(qn_sticker_as_pic)) {
                if (Looper.myLooper() != null) {
                    showToast(getApplication(), TOAST_TYPE_ERROR, "QNotified:表情转图片功能初始化错误", Toast.LENGTH_LONG);
                }
            }
            if (!canInit) return false;
            XposedHelpers.findAndHookMethod(_PicItemBuilder(), "onClick", View.class, new XC_MethodHook(51) {

                Field f_picExtraData = null;
                Field f_imageBizType = null;
                Field f_imageType = null;

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if (LicenseStatus.sDisableCommonHooks) return;
                    if (!isEnabled()) return;
                    Object chatMsg = AIOUtilsImpl.getChatMessage((View) param.args[0]);
                    if (chatMsg == null) return;
                    if (f_picExtraData == null) {
                        f_picExtraData = Utils.findField(chatMsg.getClass(), null, "picExtraData");
                        f_picExtraData.setAccessible(true);
                    }
                    Object picMessageExtraData = f_picExtraData.get(chatMsg);
                    if (f_imageType == null) {
                        f_imageType = Utils.findField(chatMsg.getClass(), null, "imageType");
                        f_imageType.setAccessible(true);
                    }
                    f_imageType.setInt(chatMsg, 0);
                    if (picMessageExtraData != null) {
                        if (f_imageBizType == null) {
                            f_imageBizType = Utils.findField(picMessageExtraData.getClass(), null, "imageBizType");
                            f_imageBizType.setAccessible(true);
                        }
                        f_imageBizType.setInt(picMessageExtraData, 0);
                    }
                }
            });
            inited = true;
            return true;
        } catch (Throwable e) {
            log(e);
            return false;
        }
    }

    @Override
    public Step[] getPreconditions() {
        return new Step[]{new DexDeobfStep(DexKit.C_AIO_UTILS)};
    }

    @Override
    public int getEffectiveProc() {
        return SyncUtils.PROC_MAIN;
    }

    @Override
    public boolean isInited() {
        return inited;
    }

    @Override
    public void setEnabled(boolean enabled) {
        try {
            ConfigManager mgr = ConfigManager.getDefaultConfig();
            mgr.getAllConfig().put(qn_sticker_as_pic, enabled);
            mgr.save();
        } catch (final Exception e) {
            Utils.log(e);
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Utils.showToast(getApplication(), TOAST_TYPE_ERROR, e + "", Toast.LENGTH_SHORT);
            } else {
                SyncUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showToast(getApplication(), TOAST_TYPE_ERROR, e + "", Toast.LENGTH_SHORT);
                    }
                });
            }
        }
    }

    @Override
    public boolean isEnabled() {
        try {
            Application app = getApplication();
            if (app != null && isTim(app)) return false;
            return ConfigManager.getDefaultConfig().getBooleanOrFalse(qn_sticker_as_pic);
        } catch (Exception e) {
            log(e);
            return false;
        }
    }
}
