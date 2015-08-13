<%@ page import="model.Automobile" %>
<%--
  Created by IntelliJ IDEA.
  User: Tangent Chang
  Date: 8/12/15
  Time: 4:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Basic Car Choice</title>
  </head>
  <body>
  <h1>Basic Car Choice</h1>

  <% Automobile auto = (Automobile) session.getAttribute("Auto"); %>
  <% if(auto != null){ %>
  <p>Here is what you selected:</p>
  <table border="3">
    <tr>
      <td><%=auto.getMaker()+"/"+auto.getModelName()%></td><td>Base Price</td><td><%=auto.getBasePrice()%></td>
    </tr>
  <%
      for(int i = 0; i < auto.getOptionsets().size(); i++){
        String setName = auto.getOptionsetName(i);
        String optName = request.getParameter(setName);
        auto.setChoice(setName, optName);
        Float optPrice = auto.getChoicePrice(setName);
  %>
    <tr>
      <td><%=setName%></td><td><%=optName%></td><td><%=optPrice%></td>
    </tr>
  <%
      }
    }
  %>
    <tr>
      <td><b>Total Cost</b></td><td></td><td><b>$<%=auto.getTotalPrice()%></b></td>
    </tr>
  </table>
  </body>
</html>
