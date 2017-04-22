/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author ekk
 */
public class Member {
    private String memID;
    private String memDate;
    private String fname;
    private String lname;
    private String midName;
    private String status;
    private long password;
    private long pwAttempt;
    
    public Member() {
        memID = "";
        memDate = "";
        fname = "";
        lname = "";
        midName = "";
        status = "";
        password = -1;
        pwAttempt = 0;
    }

    /**
     * @return the memID
     */
    public String getMemID() {
        return memID;
    }

    /**
     * @param memID the memID to set
     */
    public void setMemID(String memID) {
        this.memID = memID;
    }

    /**
     * @return the memDate
     */
    public String getMemDate() {
        return memDate;
    }

    /**
     * @param memDate the memDate to set
     */
    public void setMemDate(String memDate) {
        this.memDate = memDate;
    }

    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return the midName
     */
    public String getMidName() {
        return midName;
    }

    /**
     * @param midName the midName to set
     */
    public void setMidName(String midName) {
        this.midName = midName;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the password
     */
    public long getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(long password) {
        this.password = password;
    }
    
    /**
    * @param attempt the attempt to set
    */
    public void setPwAttempt(long attempt) {
        this.pwAttempt = attempt;
    }
    
    public boolean isAuthenticated() {
        if (this.password > 0) {
            if (this.password == this.pwAttempt) {
                return true;
            }
        }
        return false;
    }
}
