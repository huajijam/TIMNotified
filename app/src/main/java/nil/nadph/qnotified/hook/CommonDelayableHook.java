package nil.nadph.qnotified.hook;

import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import nil.nadph.qnotified.SyncUtils;
import nil.nadph.qnotified.config.ConfigManager;
import nil.nadph.qnotified.step.Step;
import nil.nadph.qnotified.util.Utils;

import static nil.nadph.qnotified.util.Utils.TOAST_TYPE_ERROR;
import static nil.nadph.qnotified.util.Utils.getApplication;
import static nil.nadph.qnotified.util.Utils.log;

public abstract class CommonDelayableHook extends BaseDelayableHook {
    
    private final String mKeyName;
    private final boolean mDefaultEnabled;
    private final int mTargetProcess;
    private final Step[] mPreconditions;
    private boolean mInited = false;
    
    protected CommonDelayableHook(@NonNull String keyName, @NonNull Step... preconditions) {
        this(keyName, SyncUtils.PROC_MAIN, false, preconditions);
    }
    
    protected CommonDelayableHook(@NonNull String keyName, int targetProcess, @NonNull Step... preconditions) {
        this(keyName, targetProcess, false, preconditions);
    }
    
    protected CommonDelayableHook(@NonNull String keyName, int targetProcess, boolean defEnabled, @NonNull Step... preconditions) {
        mKeyName = keyName;
        mTargetProcess = targetProcess;
        mDefaultEnabled = defEnabled;
        if (preconditions == null) {
            preconditions = new Step[0];
        }
        mPreconditions = preconditions;
    }
    
    @Override
    public final boolean isInited() {
        return mInited;
    }
    
    @Override
    public final boolean init() {
        if (mInited) {
            return true;
        }
        mInited = initOnce();
        return mInited;
    }
    
    protected abstract boolean initOnce();
    
    @Override
    public boolean isEnabled() {
        try {
            return ConfigManager.getDefaultConfig().getBooleanOrDefault(mKeyName, mDefaultEnabled);
        } catch (Exception e) {
            log(e);
            return false;
        }
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        try {
            ConfigManager mgr = ConfigManager.getDefaultConfig();
            mgr.getAllConfig().put(mKeyName, enabled);
            mgr.save();
        } catch (Exception e) {
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
    
    @NonNull
    @Override
    public Step[] getPreconditions() {
        return mPreconditions;
    }
    
    @Override
    public int getEffectiveProc() {
        return mTargetProcess;
    }
}