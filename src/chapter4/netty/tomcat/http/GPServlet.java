package chapter4.netty.tomcat.http;


/**
 * GPServlet 抽象类
 *
 * @author Akuma
 * @date 2020/7/18 17:50
 */
public abstract class GPServlet {

    public void service(GPRequest request, GPResponse response) throws Exception {
        // 由 service() 方法决定是调用 doGet() 还是 doPost()
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    /* doGet() 和 doPost() 方法中有两个参数 GPRequest 和 GPResponse 对象，这两个对象是由 Web 容器创建的，主要是对底层
     * Socket 的输入输出的封装，其中 GPRequest 是对 Input 的封装， GPResponse 是对 Output 的封装。*/

    public abstract void doGet(GPRequest request, GPResponse response) throws Exception;

    public abstract void doPost(GPRequest request, GPResponse response) throws Exception;

}
