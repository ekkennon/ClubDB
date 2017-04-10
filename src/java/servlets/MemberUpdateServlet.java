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
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

/**
 *
 * @author raefo
 */
public class MemberUpdateServlet extends HttpServlet {

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
        String sql = "";
        String msg = "";
        String url = "/MemberScreen.jsp";
        String lname;
        String fname;
        String midName;
        String status;
        String memDate;
        long newpw = 0;
        
        String dbUrl = "jdbc:mysql://localhost:3306/club?useSSL=false";
        String dbUser = "root";
        String dbPw = "sesame";
        
        try {
            Member newmem = new Member();
            Member m = (Member) request.getSession().getAttribute("m");
            newmem.setMemID(m.getMemID());
            newmem.setPwAttempt(m.getPassword());
            newmem.setPassword(m.getPassword());
            
            lname = request.getParameter("lastname");
            if (lname.isEmpty()) {
                msg += "Last name cannot be blank.";
            } else {
                newmem.setLname(lname);
            }
            
            fname = request.getParameter("firstname");
            if (fname.isEmpty()) {
                msg += "First name cannot be blank.";
            } else {
                newmem.setFname(fname);
            }
            
            midName = request.getParameter("middlename");
            if (midName.isEmpty()) {
                msg += "Middle name cannot be blank.";
            } else {
                newmem.setMidName(midName);
                
            }
            
            memDate = request.getParameter("memdt");
            if (memDate.isEmpty()) {
                msg += "Date cannot be blank.";
            } else {
                newmem.setMemDate(memDate);
            }
            
            status = request.getParameter("status");
            if (status.isEmpty()) {
                msg += "status cannot be blank.";
            } else {
                newmem.setStatus(status);
            }
            
            try {
               newpw = Long.parseLong(request.getParameter("psswd"));
                if (newpw > 0) {
                    newmem.setPassword(newpw);
                } else {
                    msg += "Password is not > 0<br/>";
                } 
            } catch (Exception e) {
                msg = "Password is illegal<br/>";
            }
            
            if (msg.isEmpty()) {
                
                sql = "UPDATE tblmembers SET " +
                        "LastName = ?, " +
                        "FirstName = ?, " +
                        "MiddleName = ?, " +
                        "Status = ?, " +
                        "MemDt = ?, " +
                        "Password = ? " +
                        "WHERE MemId = ?";
                
                Connection conn = DriverManager.getConnection(dbUrl,dbUser,dbPw);
                PreparedStatement ps = conn.prepareStatement(sql);
                
                ps.setString(1, newmem.getLname());
                ps.setString(2, newmem.getFname());
                ps.setString(3, newmem.getMidName());
                ps.setString(4, newmem.getStatus());
                ps.setString(5, newmem.getMemDate());
                ps.setLong(6, newmem.getPassword());
                ps.setString(7, newmem.getMemID());
                
                int recordCount = ps.executeUpdate();
                
                if (recordCount == 0) {
                    msg += "Update failed - no changes<br/>";
                } else if (recordCount == 1) {
                    msg += "Member Updated!<br/>";
                    m = newmem;
                    request.getSession().setAttribute("m", m);
                } else {
                    msg += "fatal error: " + recordCount + " records were changed<br/>";
                }
                request.setAttribute("msg", msg);
                RequestDispatcher disp = getServletContext().getRequestDispatcher(url);
                disp.forward(request,response);
            }
            
        } catch (SQLException e) {
            
            msg += "SQL Error: command = " + sql + "<br/>";
        } catch (Exception e) {
            
            msg += "General error: " + e.getMessage();
        }
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
