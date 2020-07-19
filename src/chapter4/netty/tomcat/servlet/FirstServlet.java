package chapter4.netty.tomcat.servlet;


import chapter4.netty.tomcat.http.GPRequest;
import chapter4.netty.tomcat.http.GPResponse;
import chapter4.netty.tomcat.http.GPServlet;

/**
 * @author Akuma
 * @date 2020/7/18 17:57
 */
public class FirstServlet extends GPServlet {

    @Override
    public void doGet(GPRequest request, GPResponse response) throws Exception {
        this.doPost(request, response);
    }

    @Override
    public void doPost(GPRequest request, GPResponse response) throws Exception {
        response.write("This is First Servlet");
    }
}
