package nil.nadph.qnotified.remote;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

import java.io.IOException;

public class Utf8JceUtils {

    public static final String[] DUMMY_STRING_ARRAY = new String[]{""};
    public static final byte[] NO_DATA = new byte[0];

    public static <T extends JceStruct> T decodeJceStruct(T struct, byte[] bs) throws IOException {
        JceInputStream is = newInputStream(bs);
        struct.readFrom(is);
        return struct;
    }

    public static byte[] encodeJceStruct(JceStruct struct) throws IOException {
        JceOutputStream os = newOutputStream();
        struct.writeTo(os);
        return os.toByteArray();
    }

    public static JceOutputStream newOutputStream() {
        JceOutputStream is = new JceOutputStream();
        is.setServerEncoding("UTF-8");
        return is;
    }

    public static JceInputStream newInputStream(byte[] bs) {
        JceInputStream os = new JceInputStream(bs);
        os.setServerEncoding("UTF-8");
        return os;
    }
}
