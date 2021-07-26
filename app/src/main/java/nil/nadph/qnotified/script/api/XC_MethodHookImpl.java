package nil.nadph.qnotified.script.api;

import de.robv.android.xposed.XC_MethodHook;

public interface XC_MethodHookImpl {
    void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable;

    void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable;
}
