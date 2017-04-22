/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import business.ConnectionPool;
import business.Member;
import business.Purchase;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ekk
 */
public class ShowPurchasesServlet extends HttpServlet {

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
        String url = "/MemberScreen.jsp";
        String sql = "";
        String msg = "";
        String month = "";
        String day = "";
        String year = "";
        String sqlWhere = "";
        String from = "";
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        
        try {
            Member m = (Member) request.getSession().getAttribute("m");
            month = request.getParameter("month");
            day = request.getParameter("day");
            year = request.getParameter("year");
            
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = pool.getConnection();
            
            if(month.isEmpty() || day .isEmpty() || year.isEmpty()) {
                sqlWhere = "";
            } else {
                from = " - from " + month + "-" + day + "-" + year;
                sqlWhere = " AND p.purchasedt >= '" + year + "-" + month + "-" + day + "'";
            }
            request.setAttribute("from", from);
            sql = "SELECT p.MemID, p.PurchaseDt, p.TransType, p.TransCd, c.TransDesc, p.amount FROM tblPurchases p, tblCodes c WHERE p.transcd = c.transcd AND p.memid = '" + m.getMemID() + "'" + sqlWhere + "ORDER BY p.Purchasedt";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet r = ps.executeQuery(sql);
            ArrayList<Purchase> purchases = new ArrayList<>();
            double balanceDue = 0;
            while (r.next()) {
                String type = r.getString("TransType");
                double amount = r.getDouble("Amount");
                Purchase p = new Purchase(r.getString("PurchaseDt"), type, r.getString("TransCd"), 
                        r.getString("TransDesc"), amount);
                purchases.add(p);
                if (type.equals("D")) {
                    balanceDue += amount;
                } else {
                    balanceDue -= amount;
                }
            }
            
            msg = "Total Records = " + purchases.size() + " Total Balance Due = " + curr.format(balanceDue);
            url = "/Purchases.jsp";
            request.setAttribute("purchases", purchases);
        } catch (SQLException e) {
            msg = "SQL Exception: " + e.getMessage();
        }
        request.setAttribute("msg", msg);
        RequestDispatcher disp = getServletContext().getRequestDispatcher(url);
        disp.forward(request, response);
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
