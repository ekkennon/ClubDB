/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import business.Member;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author raefo
 */
public class ClubLogonServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "/Logon.jsp";
        String msg = "";
        String sql = "";
        String userId = "";
        long pwAttempt;
        Member m;
        String dbUrl = "jdbc:mysql://localhost:3306/club?useSSL=false";
        String dbUser = "root";
        String dbPw = "sesame";
        
        try {
            userId = request.getParameter("userid").trim();
            pwAttempt = Long.parseLong(request.getParameter("password").trim());
            
            Connection conn = DriverManager.getConnection(dbUrl,dbUser,dbPw);
            Statement s = conn.createStatement();
            sql = "SELECT * FROM tblMembers WHERE MemId = '" + userId + "'";
            ResultSet r = s.executeQuery(sql);
            //TODO in logon.jsp if there is a member object auto fill username (first from active user, second from cookie)
            //TODO Purchase class
            //TODO purchase servlet
            //TODO Required Elements -> e. has sample if-else for expression language (needed for logon.jsp)
            //TODO use a prepared statement on logon servlet
            //TODO use ConnectionPool instance for viewing purchases?
            //TODO SQL Code -> 3 has sample sql statement for purchases
            //TODO use "from" date on purchases view
            if (r.next()) {
                m = new Member();
                m.setMemID(userId);
                m.setPwAttempt(pwAttempt);
                m.setPassword(r.getLong("Password"));
                if (m.isAuthenticated()) {
                    msg = "Member Authenticated!<br/>";
                    m.setLname(r.getString("LastName"));
                    m.setFname(r.getString("FirstName"));
                    m.setMidName(r.getString("MiddleName"));
                    m.setStatus(r.getString("status"));
                    m.setMemDate(r.getString("MemDt"));
                    url = "/MemberScreen.jsp";
                } else {
                    msg = "Member failed to authenticate<br/>";
                }
                request.getSession().setAttribute("m", m);
            } else {
                msg = "User not found in DB<br/>";
            }
        } catch (NumberFormatException e) {
            msg = "Illegal password: "  + e.getMessage() + "<br/>";
        } catch (SQLException e) {
            msg = "SQL Error" + e.getMessage() + "<br/>";
        }
        request.setAttribute("msg", msg);
        Cookie uid = new Cookie("userid",userId);
        uid.setMaxAge(60*10);
        uid.setPath("/");
        response.addCookie(uid);
        RequestDispatcher disp = getServletContext().getRequestDispatcher(url);
        disp.forward(request,response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
