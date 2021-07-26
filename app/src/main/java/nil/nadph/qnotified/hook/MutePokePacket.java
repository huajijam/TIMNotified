package nil.nadph.qnotified.hook;

import android.os.Looper;
import android.widget.Toast;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import nil.nadph.qnotified.SyncUtils;
import nil.nadph.qnotified.config.ConfigManager;
import nil.nadph.qnotified.step.Step;
import nil.nadph.qnotified.util.LicenseStatus;
import nil.nadph.qnotified.util.Utils;

import static nil.nadph.qnotified.util.Initiator.load;
import static nil.nadph.qnotified.util.Utils.*;

public class MutePokePacket extends BaseDelayableHook {
    private static final MutePokePacket self = new MutePokePacket();
    private boolean inited = false;
    private String qn_mute_poke = "qn_mute_poke";

    private MutePokePacket() {
    }

    public static MutePokePacket get() {
        return self;
    }

    @Override
    public boolean init() {
        if (inited) return true;
        try {
            XposedHelpers.findAndHookMethod(load("com.tencent.mobileqq.data.MessageForPoke"), "doParse", new XC_MethodHook(200) {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if (LicenseStatus.sDisableCommonHooks || !isEnabled()) return;
                    XposedHelpers.setObjectField(param.thisObject, "isPlayed", true);
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
    public void setEnabled(boolean enabled) {
        try {
            ConfigManager mgr = ConfigManager.getDefaultConfig();
            mgr.getAllConfig().put(qn_mute_poke, enabled);
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
            return ConfigManager.getDefaultConfig().getBooleanOrFalse(qn_mute_poke);
        } catch (Exception e) {
            log(e);
            return false;
        }
    }

    @Override
    public boolean checkPreconditions() {
        return true;
    }

    @Override
    public int getEffectiveProc() {
        return SyncUtils.PROC_MAIN;
    }

    @Override
    public Step[] getPreconditions() {
        return new Step[0];
    }

    @Override
    public boolean isInited() {
        return inited;
    }

}
