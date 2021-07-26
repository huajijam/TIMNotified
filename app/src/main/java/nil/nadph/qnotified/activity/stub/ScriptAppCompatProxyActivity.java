package nil.nadph.qnotified.activity.stub;

import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import de.robv.android.xposed.XC_MethodHook;
import nil.nadph.qnotified.activity.AppCompatTransferActivity;
import nil.nadph.qnotified.script.gui.ActivityProxyHandlerStaticHolder;
import nil.nadph.qnotified.util.internal.XMethodHookDispatchUtil;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

public class ScriptAppCompatProxyActivity extends AppCompatTransferActivity {
    private Map<String, XMethodHookDispatchUtil.HookHolder> mH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String uuid = getIntent().getStringExtra(ActivityProxyHandlerStaticHolder.TAG_ACTIVITY_PROXY_HANDLER);
        if (uuid == null) {
            throw new IllegalArgumentException("UUID for ScriptAppCompatProxyActivity not specified in Intent");
        }
        {
            Map<String, XMethodHookDispatchUtil.HookHolder> t = ActivityProxyHandlerStaticHolder.consume(uuid);
            if (t != null) mH = t;
        }
        if (mH == null) {
            throw new IllegalStateException("proxy handler not found, not in the same process?");
        }
        XMethodHookDispatchUtil.HookHolder h = mH.get("onCreate(Landroid/os/Bundle;)V");
        if (h == null) {
            super.onCreate(savedInstanceState);
        } else {
            XC_MethodHook.MethodHookParam p = XMethodHookDispatchUtil.createParam(h.hook, h.method, this, savedInstanceState);
            if (!XMethodHookDispatchUtil.callBeforeHook(h.hook, p)) {
                try {
                    super.onCreate((Bundle) p.args[0]);
                    p.setResult(null);
                } catch (Throwable e) {
                    p.setThrowable(e);
                }
            }
            XMethodHookDispatchUtil.callAfterHook(h.hook, p);
            try {
                p.getResultOrThrowable();
            } catch (RuntimeException | Error e) {
                throw e;
            } catch (Throwable t) {
                throw new UndeclaredThrowableException(t);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        String uuid = getIntent().getStringExtra(ActivityProxyHandlerStaticHolder.TAG_ACTIVITY_PROXY_HANDLER);
        if (uuid == null) {
            throw new IllegalArgumentException("UUID for ScriptAppCompatProxyActivity not specified in Intent");
        }
        {
            Map<String, XMethodHookDispatchUtil.HookHolder> t = ActivityProxyHandlerStaticHolder.consume(uuid);
            if (t != null) mH = t;
        }

        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        XMethodHookDispatchUtil.HookHolder h = mH.get("onResume()V");
        if (h == null) {
            super.onResume();
        } else {
            XC_MethodHook.MethodHookParam p = XMethodHookDispatchUtil.createParam(h.hook, h.method, this);
            if (!XMethodHookDispatchUtil.callBeforeHook(h.hook, p)) {
                try {
                    super.onResume();
                    p.setResult(null);
                } catch (Throwable e) {
                    p.setThrowable(e);
                }
            }
            XMethodHookDispatchUtil.callAfterHook(h.hook, p);
            try {
                p.getResultOrThrowable();
            } catch (RuntimeException | Error e) {
                throw e;
            } catch (Throwable t) {
                throw new UndeclaredThrowableException(t);
            }
        }
    }
}
