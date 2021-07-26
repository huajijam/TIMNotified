package nil.nadph.qnotified.util;

import android.app.Application;

import com.microsoft.appcenter.crashes.Crashes;

public class BugCollector {

    public static void onThrowable(Throwable th) {
        try {
            if (Utils.isCallingFrom("BugCollector")) return;
            Application ctx = Utils.getApplication();
            if (ctx != null) {
                CliOper.__init__(ctx);
                Crashes.trackError(th);
            }
        } catch (Throwable ignored) {
        }
    }
}
