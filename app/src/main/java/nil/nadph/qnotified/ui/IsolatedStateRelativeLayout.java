package nil.nadph.qnotified.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.RelativeLayout;

public class IsolatedStateRelativeLayout extends RelativeLayout {

    public IsolatedStateRelativeLayout(Context context) {
        super(context);
    }

    public IsolatedStateRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IsolatedStateRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IsolatedStateRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        int id = getId();
        if (id == NO_ID) return;
        Parcelable superState = super.onSaveInstanceState();
        IsolatedLayoutSaveState iss = new IsolatedLayoutSaveState(superState);
        SparseArray<Parcelable> array = iss.childStates;
        super.dispatchSaveInstanceState(array);
        container.put(id, iss);
    }

    @Override
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        int id = getId();
        if (id == NO_ID) return;
        Parcelable state = container.get(id);
        if (state == null) return;
        IsolatedLayoutSaveState ss = (IsolatedLayoutSaveState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        SparseArray<Parcelable> array = ss.childStates;
        super.dispatchRestoreInstanceState(array);
    }
}
