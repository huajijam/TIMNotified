package nil.nadph.qnotified.bridge;

import android.view.View;
import nil.nadph.qnotified.util.Initiator;
import nil.nadph.qnotified.util.Nullable;
import nil.nadph.qnotified.util.Utils;

import java.lang.reflect.Field;

public class AIOUtilsImpl {
    private static Class<?> c_tx_ListView = null;
    private static Field f_BaseHolder_ChatMsg = null;

    @Nullable
    public static Object getBaseHolder(View v) {
        if (v == null) return null;
        if (c_tx_ListView == null) {
            c_tx_ListView = Initiator.load("com.tencent.widget.ListView");
        }
        if (v.getParent() == null || c_tx_ListView.isInstance(v.getParent())) {
            return v.getTag();
        }
        return getBaseHolder((View) v.getParent());
    }

    @Nullable
    public static Object getChatMessage(View v) {
        Object holder = getBaseHolder(v);
        if (holder == null) return null;
        if (f_BaseHolder_ChatMsg == null) {
            Class<?> c_BaseHolder = holder.getClass();
            while (c_BaseHolder.getSuperclass() != Object.class) {
                c_BaseHolder = c_BaseHolder.getSuperclass();
            }
            Field[] fs = c_BaseHolder.getDeclaredFields();
            for (Field f : fs) {
                if (f.getType() == Initiator._ChatMessage()) {
                    f_BaseHolder_ChatMsg = f;
                    break;
                }
            }
        }
        try {
            return f_BaseHolder_ChatMsg.get(holder);
        } catch (Exception e) {
            Utils.log(e);
            //should not happen, it's public
            return null;
        }
    }
}
