package nil.nadph.qnotified.util;

import android.content.Context;

import java.net.URL;

public class HybridClassLoader extends ClassLoader {

    private final ClassLoader clPreload;
    private final ClassLoader clBase;
    private static final ClassLoader sBootClassLoader = Context.class.getClassLoader();

    public HybridClassLoader(ClassLoader x, ClassLoader ctx) {
        clPreload = x;
        clBase = ctx;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return sBootClassLoader.loadClass(name);
        } catch (ClassNotFoundException ignored) {
        }
        if (name != null && (name.startsWith("androidx.") || name.startsWith("android.support.v4.")
                || name.startsWith("kotlin.") || name.startsWith("kotlinx."))) {
            //Nevertheless, this will not interfere with the host application,
            //classes in host application SHOULD find with their own ClassLoader, eg Class.forName()
            //use shipped androidx and kotlin lib.
            throw new ClassNotFoundException(name);
        }
        //The ClassLoader for XPatch is terrible, XposedBridge.class.getClassLoader() == Context.getClassLoader(),
        //which mess up with my kotlin lib.
        if (clPreload != null) {
            try {
                return clPreload.loadClass(name);
            } catch (ClassNotFoundException ignored) {
            }
        }
        if (clBase != null) {
            try {
                return clBase.loadClass(name);
            } catch (ClassNotFoundException ignored) {
            }
        }
        throw new ClassNotFoundException(name);
    }

    @Override
    public URL getResource(String name) {
        URL ret = clPreload.getResource(name);
        if (ret != null) return ret;
        return clBase.getResource(name);
    }
}
