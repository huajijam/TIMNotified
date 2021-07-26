package nil.nadph.qnotified.script.api;

import de.robv.android.xposed.XC_MethodHook;
import nil.nadph.qnotified.util.DexMethodDescriptor;

import java.lang.reflect.Method;
import java.util.HashMap;

public class RestrictedProxyParamList {

    private final HashMap<String, XC_MethodHook> proxyCallbacks = new HashMap<>();

    public RestrictedProxyParamList() {
    }

    public RestrictedProxyParamList addMethod(Method method, XC_MethodHook hook) {
        proxyCallbacks.put(method.getName() + DexMethodDescriptor.getMethodTypeSig(method), hook);
        return this;
    }

    public RestrictedProxyParamList addMethod(Method method, XC_MethodHookImpl hook) {
        proxyCallbacks.put(method.getName() + DexMethodDescriptor.getMethodTypeSig(method), XMethodHookFactory.create(hook));
        return this;
    }

    public RestrictedProxyParamList removeMethod(Method method) {
        proxyCallbacks.remove(method.getName() + DexMethodDescriptor.getMethodTypeSig(method));
        return this;
    }

    public HashMap<String, XC_MethodHook> getProxyCallbacks() {
        return proxyCallbacks;
    }
}