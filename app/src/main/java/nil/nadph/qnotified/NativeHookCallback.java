package nil.nadph.qnotified;

import de.robv.android.xposed.XC_MethodHook;
import nil.nadph.qnotified.util.NonNull;

public final class NativeHookCallback extends XC_MethodHook {
    public final long a;

    public NativeHookCallback(long a, int b) {
        super(b);
        this.a = a;
    }

    @Override
    protected native void beforeHookedMethod(@NonNull MethodHookParam param) throws Throwable;

    @Override
    protected native void afterHookedMethod(@NonNull MethodHookParam param) throws Throwable;
}
