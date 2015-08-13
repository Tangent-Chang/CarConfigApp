package servlet;

import client.Client;
import model.Automobile;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Tangent Chang on 7/24/15.
 */
@WebServlet(urlPatterns={"/ServletOptionsets"})
public class ServletOptionsets extends HttpServlet{
    private Client client;
    private HttpSession session;

    public ServletOptionsets(){
        super();
    }

    @Override
    public void init(){
        client = Client.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String modelName = request.getParameter("modelName");
        Automobile auto = client.getAutoObj(modelName);
        session = request.getSession(true);
        session.setAttribute("Auto", auto);
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
        out.println("<form action=\"result.jsp\" method=\"post\">");
        out.println("<table border=\"3\">");
        out.println("<tr>");
        out.println("<td>Make/Model</td><td>" + auto.getMaker() + "/" + auto.getModelName() + "</td>");
        out.println("</tr>");

        ArrayList<String> setsNames = auto.getOptionsetsNames();
        for(String eachSet : setsNames){
            out.println("<tr><td>" + eachSet+ "</td><td><select name=\""+eachSet+"\">");
            ArrayList<String> optionsNames = auto.getOptionsNames(eachSet);
            for(String eachOption : optionsNames){
                out.println("<option value=\""+eachOption+"\">"+eachOption+"</option>");
            }
            out.println("</select></td></tr>");
        }

        out.println("<tr>");
        out.println("<td colspan=\"2\"><button type=\"submit\">Done</button></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
