package servlet;

import client.ServletClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Tangent Chang on 7/24/15.
 */

@WebServlet(urlPatterns={"/ServletModel"})
public class ServletModel extends HttpServlet {

    private ServletClient client;

    public ServletModel(){
        super();
    }

    @Override
    public void init(){
        client = ServletClient.getInstance();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> modelList = client.getModelList();
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.println("<title></title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Basic Car Choice</h1>");
        out.println("<form action=\"ServletOptionsets\" method=\"post\">");
        for(String each : modelList){
            out.println("<input type=\"radio\" name=\"modelName\" value=\"" + each + "\">" + each);
        }
        out.println("<br />");
        out.println("<input type=\"submit\" value=\"Submit\" >");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}