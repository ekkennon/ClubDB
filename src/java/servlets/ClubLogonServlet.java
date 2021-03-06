/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import business.Member;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ekk
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
            
            sql = "SELECT * FROM tblMembers WHERE MemId = '" + userId + "'";
            Connection conn = DriverManager.getConnection(dbUrl,dbUser,dbPw);
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet r = ps.executeQuery(sql);
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
