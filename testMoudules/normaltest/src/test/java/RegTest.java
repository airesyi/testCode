import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * auth: shi yi
 * create date: 2018/11/5
 */
public class RegTest {
    @Test
    public void urlTrans() {
        String url = "<p>< img src=\"https://umallimg.uselect.com.cn/group1/M00/00/01/CgoBnFvcJCaAV3WUAACJgvSw73g612.jpg\"></p >" +
                "<p>< img src=\"https://umallimg.uselect.com.cn/group1/M00/00/01/CgoBnFvcJCaAV3WUAACJgvSw73g612.gif\"></p >" +
                "<p>< img src=\"https://umallimg.uselect.com.cn/group1/M00/00/01/CgoBnFvcJCaAV3WUAACJgvSw73g612.png\"></p >";

        String reg = "(?<=img\\ssrc=)(.*?\\.)(jpg|png|gif)";

        Pattern pattern = Pattern.compile(reg);

        Matcher matcher = pattern.matcher(url);

        while (matcher.find()) {
            System.out.println(matcher.group(0) + ", pos: " + matcher.start());
            System.out.println(matcher.group(1) + ", pos: " + matcher.start(1));
        }

        url = url.replaceAll(reg, "$1$2_"+"200x200"+".$2");
        System.out.println(url);
    }

    @Test
    public void test7() {
        String aaa = "key1:aaa,key2：value2，key3:自洪文自洪文";
        String[] result = aaa.split("[w|x]");
        aaa = aaa.replaceAll("(key1)([:|：])", "zzz$2");

        System.out.println(aaa);
    }

    @Test
    public void test8() {

        System.out.println(String.format("%03d", 1));
        System.out.println(String.format("%03d", 12));
        System.out.println(String.format("%03d", 123));

    }
}
