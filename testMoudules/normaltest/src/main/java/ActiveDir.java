import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class ActiveDir {
    @Test
    public void test() {
        JSONObject json = JSONObject.parseObject("123");
        Acccount acccount = JSONObject.toJavaObject(json, Acccount.class);
    }
}
