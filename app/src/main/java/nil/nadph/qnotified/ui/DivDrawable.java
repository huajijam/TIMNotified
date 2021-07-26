package nil.nadph.qnotified.ui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class DivDrawable extends Drawable {

    public static final int TYPE_HORIZONTAL = 1;
    public static final int TYPE_VERTICAL = 2;
    private final int iThickness;
    private final int iType;
    private final Paint p = new Paint();

    public DivDrawable(int type, int thickness) {
        iType = type;
        iThickness = thickness;
    }

    @Override
    public void draw(Canvas canvas) {
        int h = getBounds().height();
        int w = getBounds().width();
        if (iType == TYPE_HORIZONTAL) {
            float off = (h - iThickness) / 2f;
            Shader s = new LinearGradient(0, off, 0, h / 2f, new int[]{0x00363636, 0x36363636}, new float[]{0f, 1f}, Shader.TileMode.CLAMP);
            p.setShader(s);
            //p.setColor(0x36000000);
            canvas.drawRect(0, off, w, h / 2f, p);
            s = new LinearGradient(0, h / 2f, 0, h / 2f + iThickness / 2f, new int[]{0x36C8C8C8, 0x00C8C8C8}, new float[]{0f, 1f}, Shader.TileMode.CLAMP);
            p.setShader(s);
            //p.setColor(0x36FFFFFF);
            canvas.drawRect(0, h / 2f, w, h / 2f + iThickness / 2f, p);
        } else throw new UnsupportedOperationException("iType == " + iType);
    }

    @Override
    public void setAlpha(int alpha) {
        //throw new RuntimeException("Unsupported operation");
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        //throw new RuntimeException("Unsupported operation");
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getOpacity() {
        return android.graphics.PixelFormat.TRANSLUCENT;
    }

}
