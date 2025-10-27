import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/nimbusdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String empIdParam = request.getParameter("empid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String query;
            PreparedStatement ps;
            if (empIdParam != null && !empIdParam.isEmpty()) {
                query = "SELECT * FROM Employee WHERE EmpID = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(empIdParam));
            } else {
                query = "SELECT * FROM Employee";
                ps = con.prepareStatement(query);
            }

            ResultSet rs = ps.executeQuery();

            out.println("<h2>Employee Records</h2>");
            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Salary</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr><td>" + rs.getInt("EmpID") + "</td><td>" +
                        rs.getString("Name") + "</td><td>" +
                        rs.getDouble("Salary") + "</td></tr>");
            }

            out.println("</table>");
            if (!found) out.println("<p>No employee found.</p>");

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
