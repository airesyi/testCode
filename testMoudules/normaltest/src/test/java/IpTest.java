import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import utils.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * auth by shiyi
 * updateTime 2018/7/3
 */
public class IpTest {
    public static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        return Long.parseLong(ip[0]) * 256 * 256 * 256 + Long.parseLong(ip[1]) * 256 * 256 + Long.parseLong(ip[2]) * 256 + Long.parseLong(ip[3]);
    }

    public static boolean isInner(long ip, long begin, long end) {
        return ip >= begin && ip <= end;
    }

    @Test
    public void test1() {
        System.out.println(getIpNum("127.0.0.1"));
        System.out.println(getIpNum("127.0.1.2"));

        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = addr.getHostAddress().toString(); //获取本机ip
        String hostName = addr.getHostName().toString(); //获取本机计算机名称
        System.out.println(ip);
        System.out.println(hostName);
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-real-ip");//先从nginx自定义配置获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Test
    public void test2() {
        String url = "http://127.0.0.1/authorization/getIpInfo";
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        String result = HttpUtil.sendPost(url, map);
        System.out.println(result);

        JSONObject object = JSON.parseObject(result);

        System.out.println(object.getString("data"));
        JSONObject data = JSON.parseObject(object.getString("data"));
        List<String> ipList = JSON.parseArray(data.getString("innerIp"), String.class);
        String excludeIp = data.getString("excludeIp");

        System.out.println(ipList);
        System.out.println(excludeIp);
    }

    @Test
    public void test3() {
        String url = "http://127.0.0.1/sys/getAuthenTime";
        Map<String, String> map = new HashMap<>();
        map.put("sysId", "GS_ANDROID_001");
        map.put("sysTicket", "123456");
        String result = HttpUtil.sendPost(url, map);
        System.out.println(result);

        JSONObject object = JSON.parseObject(result);

        System.out.println(object.getString("data"));
        JSONObject data = JSON.parseObject(object.getString("data"));
        int excludeIp = data.getInteger("block_ticket_timeout");

        System.out.println(excludeIp);
    }
}
