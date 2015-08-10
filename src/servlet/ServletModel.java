package servlet;

import client.Client;

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

    ArrayList<String> nameList;
    //private ServletClient client;
    private Client client;
    public void init(){
        /*Thread tServer = new Thread(new Server());
        Thread tClient = new Thread(new Client());
        tServer.start();
        System.out.println("server start in servlet");
        tClient.start();
        System.out.println("client start in servlet");*/
        //client = ServletClient.getInstance();
        client = new Client();
        client.run();

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        //out.println("this is servlet");
        //String user = request.getParameter("user"); // 取得請求參數
        //request.setAttribute("user", user);         // 設定請求屬性
        //request.getRequestDispatcher("configure.jsp").forward(request, response);

        //out.println("<%@ page contentType=\"text/html;charset=UTF-8\" language=\"java\" %>");
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.println("<title></title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Basic Car Choice</h1>");
        out.println("<form action=\"ServletOptionsets\" method=\"post\">");
        out.println("<table border=\"3\">");
        out.println("<tr>");
        out.println("<td>Make/Model</td><td><select><option>test1</option></select></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>Color</td><td><select><option>test1</option></select></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>Transmission</td><td><select><option>test1</option></select></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>ABS/Traction Control</td><td><select><option>test1</option></select></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>Side Impact Air Bag</td><td><select><option>test1</option></select></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>Power Moonroof</td><td><select><option>test1</option></select></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td colspan=\"2\"><button type=\"submit\">Done</button></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        /*out.println("in doGet method");
        BuildAuto auto = new BuildAuto();
        out.println("new buildauto");
        nameList = auto.getModelList();
        out.println("getModelList");
        //out.println(nameList.get(0));
        out.println(nameList.size());
        out.println("end of buildauto");*/

    }
}