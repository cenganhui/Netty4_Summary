package chapter4.bio.tomcat.http;

import java.io.OutputStream;

/**
 * GPResponse 按照 HTTP 规范从 Output 输出格式化的字符串
 *
 * @author Akuma
 * @date 2020/7/18 18:06
 */
public class GPResponse {

    private OutputStream out;

    public GPResponse(OutputStream out) {
        this.out = out;
    }

    public void write(String s) throws Exception {
        // 输出也要遵循 HTTP
        // 状态码为 200
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 200 0K\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append(s);
        out.write(sb.toString().getBytes());
    }

}
