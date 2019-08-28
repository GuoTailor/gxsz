package gyh.gxsz;

import gyh.gxsz.common.SmsSample;
import gyh.gxsz.common.Util;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class GxszApplicationTests {
    private static final char[] hexadecimal = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    @Test
    public void contextLoads() {
        System.out.println(Util.createDate(1564675200000L));
    }


    public static String encode(byte[] binaryData) {
        if (binaryData.length != 16) {
            return null;
        } else {
            char[] buffer = new char[32];

            for(int i = 0; i < 16; ++i) {
                int low = binaryData[i] & 15;
                int high = (binaryData[i] & 240) >> 4;
                buffer[i * 2] = hexadecimal[high];
                buffer[i * 2 + 1] = hexadecimal[low];
            }

            return new String(buffer);
        }
    }
}
