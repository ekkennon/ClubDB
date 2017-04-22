/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.text.NumberFormat;

/**
 *
 * @author ekk
 */
public class Purchase {
    private String purchaseDate;
    private String purchaseType;
    private String transCode;
    private String transDesc;
    private double amnt;
    private NumberFormat curr = NumberFormat.getCurrencyInstance();
    
    public Purchase() {
        purchaseDate = "";
        purchaseType = "";
        transCode = "";
        transDesc = "";
        amnt = 0;
    }
    
    public Purchase(String date, String type, String code, String desc, double amnt) {
        purchaseDate = date;
        purchaseType = type;
        transCode = code;
        transDesc = desc;
        amnt = amnt;
    }

    /**
     * @return the purchaseDate
     */
    public String getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate the purchaseDate to set
     */
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * @return the purchaseType
     */
    public String getPurchaseType() {
        return purchaseType;
    }

    /**
     * @param purchaseType the purchaseType to set
     */
    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    /**
     * @return the transCode
     */
    public String getTransCode() {
        return transCode;
    }

    /**
     * @param transCode the transCode to set
     */
    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    /**
     * @return the transDesc
     */
    public String getTransDesc() {
        return transDesc;
    }

    /**
     * @param transDesc the transDesc to set
     */
    public void setTransDesc(String transDesc) {
        this.transDesc = transDesc;
    }

    /**
     * @return the amnt
     */
    public String getAmnt() {
        return curr.format(this.amnt);
    }

    /**
     * @param amnt the amnt to set
     */
    public void setAmnt(double amnt) {
        this.amnt = amnt;
    }
}
