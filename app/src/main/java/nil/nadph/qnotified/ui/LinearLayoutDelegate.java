package nil.nadph.qnotified.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class LinearLayoutDelegate extends LinearLayout {

    private View delegate;

    public void setDelegate(View delegate) {
        this.delegate = delegate;
    }

    public View getDelegate() {
        return delegate;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (delegate != null) delegate.setOnClickListener(l);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        if (delegate != null) delegate.setOnLongClickListener(l);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (delegate != null)
            delegate.setPadding(left, top, right, bottom);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBackgroundDrawable(Drawable background) {
        if (delegate != null)
            //delegate.setBackgroundDrawable(background);
            ViewCompat.setBackground(delegate,background);
    }

    @Override
    public int getPaddingLeft() {
        if (delegate != null)
            return delegate.getPaddingLeft();
        return 0;
    }

    @Override
    public int getPaddingRight() {
        if (delegate != null)
            return delegate.getPaddingRight();
        return 0;
    }

    @Override
    public int getPaddingTop() {
        if (delegate != null)
            return delegate.getPaddingTop();
        return 0;
    }

    @Override
    public int getPaddingBottom() {
        if (delegate != null)
            return delegate.getPaddingBottom();
        return 0;
    }

    public static LinearLayoutDelegate setupRudely(View v) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int index = 0;
        ViewGroup.LayoutParams currlp = v.getLayoutParams();
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChildAt(i) == v) {
                index = i;
                break;
            }
        }
        parent.removeView(v);
        LinearLayoutDelegate layout = new LinearLayoutDelegate(v.getContext());
        ViewGroup.LayoutParams lpOuter;
        LayoutParams lpInner;
        if (currlp == null) {
            lpOuter = new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            lpInner = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        } else if (currlp instanceof MarginLayoutParams) {
            lpOuter = currlp;
            lpInner = new LayoutParams(currlp.width, currlp.height);
            lpInner.bottomMargin = ((MarginLayoutParams) currlp).bottomMargin;
            lpInner.topMargin = ((MarginLayoutParams) currlp).topMargin;
            lpInner.leftMargin = ((MarginLayoutParams) currlp).leftMargin;
            lpInner.rightMargin = ((MarginLayoutParams) currlp).rightMargin;
            ((MarginLayoutParams) currlp).bottomMargin = ((MarginLayoutParams) currlp).topMargin
                    = ((MarginLayoutParams) currlp).leftMargin = ((MarginLayoutParams) currlp).rightMargin = 0;
            lpOuter.height = lpOuter.width = WRAP_CONTENT;
        } else {
            lpOuter = new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            lpInner = new LayoutParams(currlp.width, currlp.height);
        }
        layout.addView(v, lpInner);
        parent.addView(layout, index, lpOuter);
        layout.setDelegate(v);
        return layout;
    }


    public LinearLayoutDelegate(Context context) {
        super(context);
    }

}
