package nil.nadph.qnotified.ui;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;

import nil.nadph.qnotified.util.Initiator;

public class IsolatedLayoutSaveState extends View.BaseSavedState {
    public static final Parcelable.Creator<IsolatedLayoutSaveState> CREATOR
            = new Creator<IsolatedLayoutSaveState>() {
        @Override
        public IsolatedLayoutSaveState createFromParcel(Parcel source) {
            return new IsolatedLayoutSaveState(source);
        }

        @Override
        public IsolatedLayoutSaveState[] newArray(int size) {
            return new IsolatedLayoutSaveState[size];
        }
    };

    public final SparseArray<Parcelable> childStates;

    public IsolatedLayoutSaveState(Parcel source) {
        super(source);
        childStates = source.readSparseArray(Initiator.getPluginClassLoader());
    }

    public IsolatedLayoutSaveState(Parcelable superState) {
        super(superState);
        childStates = new SparseArray<>();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeSparseArray(childStates);
    }
}
