package github;

import org.junit.Test;

import java.io.*;

/**
 * auth: shi yi
 * create date: 2018/10/29
 */
public class SOAR {

    @Test
    public void ansSql() {
        String sql = "select * from afda";
        StringBuffer result = new StringBuffer();
        try {
            Runtime rt = Runtime.getRuntime();
            //执行命令, 最后一个参数，可以使用new File("path")指定运行的命令的位置
            String[] cmd = new String[]{"./soar", "-report-type text", sql};

            StringBuffer sb = new StringBuffer();
//            sb.append("cmd /c ").append("echo ").append("'").append(sql).append("'").append(" | ./Users/yi.shi/go/src/github.com/XiaoMi/soar");
            sb.append("./soar -query ").append("\'").append(sql).append("\'");

            Process proc = rt.exec(cmd, null, new File("/Users/yi.shi/go/src/github.com/XiaoMi/soar"));

            InputStream stderr = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stderr, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = "";


            while ((line = br.readLine()) != null) { // 打印出命令执行的结果
                System.out.println(line);
                result.append(line).append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @PostMapping("/ansSql")
//    @ApiOperation(value = "测试sql", notes = "测试sql")
    public String ansSql(String sql) {
        StringBuffer result = new StringBuffer();
        try {
            Runtime rt = Runtime.getRuntime();
            //执行命令, 最后一个参数，可以使用new File("path")指定运行的命令的位置
            String[] cmd = new String[]{"./soar", "-query", sql};

            Process proc = rt.exec(cmd, null, new File("/Users/yi.shi/go/src/github.com/XiaoMi/soar"));

            InputStream stderr = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stderr, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = "";

            while ((line = br.readLine()) != null) { // 打印出命令执行的结果
                System.out.println(line);
                result.append(line).append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
