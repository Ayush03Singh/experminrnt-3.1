import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AttendanceServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/nimbusdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String studentID = request.getParameter("studentID");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO Attendance(StudentID, Date, Status) VALUES(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(studentID));
            ps.setString(2, date);
            ps.setString(3, status);
            ps.executeUpdate();

            out.println("<h2>Attendance recorded successfully!</h2>");
            out.println("<a href='attendance.jsp'>Go Back</a>");

            ps.close();
            con.close();

        } catch (Exception e) {
            out.println("<h3>Error saving attendance!</h3>");
            e.printStackTrace(out);
        }
    }
}
