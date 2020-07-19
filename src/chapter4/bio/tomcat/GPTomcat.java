package chapter4.bio.tomcat;

import chapter4.bio.tomcat.http.GPRequest;
import chapter4.bio.tomcat.http.GPResponse;
import chapter4.bio.tomcat.http.GPServlet;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 基于传统 I/O 手写 Tomcat
 * GPTomcat 真正 Web 容器的实现逻辑，分为三个阶段：初始化阶段、服务就绪阶段、接受请求阶段
 *
 * @author Akuma
 * @date 2020/7/18 18:10
 */
public class GPTomcat {

    private int port = 8081;

    private ServerSocket server;

    private Map<String, GPServlet> servletMapping = new HashMap<>();

    private Properties webxml = new Properties();

    /**
     * 初始化阶段，主要完成对 web.xml 文件的解析
     */
    private void init() {
        // 加载web.xml文件，同时初始化ServletMapping对象
        try {
            // 从WEB-INF中读取web.properties文件并对其进行解析，然后将URL规则和GPServlet的对应关系保存到servletMapping中
            String WEB_INF = this.getClass().getResource("/").getPath() + "chapter4/bio/tomcat/";
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");

            webxml.load(fis);

            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    // 单实例，多线程
                    GPServlet obj = (GPServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第二阶段：服务就绪阶段，完成 ServerSocket 的准备工作
     */
    public void start() {
        // 1.加载配置文件，初始化ServletMapping
        init();
        try {
            server = new ServerSocket(this.port);
            System.out.println("GPTomcat已启动，监听的端口是：" + this.port);
            // 2.等待用户请求，用一个死循环来等待用户请求
            while (true) {
                Socket client = server.accept();
                // 3.HTTP请求，发送的数据就是字符串————有规律的字符串（HTTP）
                process(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第三阶段：接受请求阶段，完成每一次请求的处理
     *
     * @param client
     * @throws Exception
     */
    private void process(Socket client) throws Exception {
        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();

        // 4.Request(InputStream)/Response(OutputStream)
        GPRequest request = new GPRequest(is);
        GPResponse response = new GPResponse(os);

        // 5.从协议内容中获得URL，把相应的Servlet用反射进行实例化
        String url = request.getUrl();

        if (servletMapping.containsKey(url)) {
            // 6.调用实例化对象的service()方法，执行具体的逻辑doGet()/doPost()方法
            servletMapping.get(url).service(request, response);
        } else {
            response.write("404 - Not Found");
        }

        os.flush();
        os.close();

        is.close();
        client.close();
    }

    public static void main(String[] args) {
        new GPTomcat().start();
    }

}
