<!--
- Author: Brian Coveney
- Date: 20/03/2017
-->
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:useBean id="ScreechBean" scope="session" class="model.CarBean"/>
    <jsp:setProperty name="ScreechBean" property="*"/>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Screech Web Application</title>

    <%-- Implicit object:
    'pageContext' provides access to the request object, which recives the contextPath  --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/stylesheet.css">

</head>
<div id="container">
<jsp:include page="headder.jsp"></jsp:include>
<form method="get" action="ScreechServlet" method="post">

    <%--Hidden form field is used to maintain the session--%>
    <input type="hidden" name="from" value="${pageContext.request.requestURI}"/>

        <%!
            // Ensure blank form inputs (when page first loads), by setting variables equal to empty strings,
            String carName = "";
            String skid1 = "";
            String skid2 = "";
            String skid3 = "";
            String skid4 = "";
        %>

        <%
            // create an ArrayList and add a fixed-sized list of the four skid marks
            ArrayList<String> skidArrayList = new ArrayList<String>(Arrays.asList(skid1, skid2, skid3, skid4));

            // Iterate through the ArrayList
            // - for each of the skid mark length parameters
            // - if value is not null
            // - set the list at each index, equal to the form value input
            for(int i = 0; i < skidArrayList.size(); i++) {
                if (request.getParameter("skidmarklength" + i) != null) {
                    skidArrayList.set(i, request.getParameter("skidmarklength" + i));
                }
            }


            if (request.getParameter("carname") != null) {
                carName = request.getParameter("carname");
            }

        %>

    <fieldset>
        <ul>
            <li>
                <label class="carname_lable">Name of car:</label>
                <input type="text" name="carname" class="carname_input"
                       value="<%= carName %>"><span>${errorMsg}</span>
            </li>
            <li>
                <label>Number of skid marks:</label>
                <select id="num_skids" name="skidmarks" class="skid_marks">
                    <option value="1" selected>1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                </select>
                <%--<span>${errorMsg2}</span><span>${message}</span><br>--%>
            </li>
        </ul>

        <div class="inputs">
            <ul>
                <li>
                    <label>Skidmark length no. 1:</label>

                    <%--
                        If user inputs an incorrect value, an error message is displayed.
                        The value is persisted in the form input, so that the user can see their error.

                        Value is blank on first page load.
                     --%>
                    <input type="text"  name="skidmarklength0" class="skid_lenghts one" value="<%= skidArrayList.get(0) %>">
                    <span>${errorMsgSkid0}</span><span>${errorMsg2Skid0}</span><br>
                </li>
                <li>
                    <label>Skidmark length no. 2:</label>
                    <input type="test" name="skidmarklength1" class="skid_lenghts" value="<%= skidArrayList.get(1) %>">
                    <span>${errorMsgSkid1}</span><span>${errorMsg2Skid1}</span><br>
                </li>
                <li>
                    <label>Skidmark length no. 3:</label>
                    <input type="text" name="skidmarklength2" class="skid_lenghts" value="<%= skidArrayList.get(2) %>">
                    <span>${errorMsgSkid2}</span><span>${errorMsg2Skid2}</span><br>
                </li>
                <li>
                    <label>Skidmark length no. 4:</label>
                    <input type="text" name="skidmarklength3" class="skid_lenghts" value="<%= skidArrayList.get(3) %>">
                    <span>${errorMsgSkid3}</span><span>${errorMsg2Skid3}</span>
                </li>
            </ul>
        </div>
        <div>
            <label class="surface_lable">Enter type of road surface:</label>
            <ul>
                <li><input type="radio" name="surface" value="Cement" class="radio_btn" checked>Portland Cement</li>
                <li><input type="radio" name="surface" value="Asphalt" class="radio_btn">Asphalt</li>
                <li><input type="radio" name="surface" value="Gravel" class="radio_btn">Gravel</li>
                <li><input type="radio" name="surface" value="Ice" class="radio_btn">Ice</li>
                <li><input type="radio" name="surface" value="Snow" class="radio_btn">Snow</li>
            </ul>
        </div>
    </fieldset>
    <fieldset>
        <div  class="submit_container">
            <input class="submit_btn" type="submit" name="submit" value="Submit">
            <input class="reset_btn" type="reset" name="reset" value="Reset">
        </div>
    </fieldset>
</form>
<footer>
    <div id="footer_container">
        <%-- include Directive  --%>
        <%@ include file="footer.jsp" %>
    </div>
</footer>
</div>
</body>
</html>