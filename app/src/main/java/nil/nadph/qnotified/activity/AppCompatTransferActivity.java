package nil.nadph.qnotified.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import nil.nadph.qnotified.util.CliOper;
import nil.nadph.qnotified.util.Utils;

public class AppCompatTransferActivity extends AppCompatActivity {
    @Override
    public ClassLoader getClassLoader() {
        return AppCompatTransferActivity.class.getClassLoader();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Bundle windowState = savedInstanceState.getBundle("android:viewHierarchyState");
            if (windowState != null) {
                windowState.setClassLoader(AppCompatTransferActivity.class.getClassLoader());
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CliOper.enterModuleActivity(Utils.getShort$Name(this));
    }
}
