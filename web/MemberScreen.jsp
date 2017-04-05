<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Member Welcome</title>
        <style>
            table.member-details{
                border-collapse: collapse;
            }
            table.member-details td, table.member-details th{
                padding: 6px;
                border: 1px solid #999;
            }
        </style>
    </head>
    <body>
        <h1>Club member Data</h1>
        <form id="memupdate" action="MemberUpdate" method="post">
            <table class="member-details">
            <tr>
                <td>Member ID:</td>
                <td><input type="text" id="memid" name="memid"
                           value="" readonly="true"></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><input type="text" id="lastname" name="lastname" 
                           value="" ></td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td><input type="text" id="firstname" name="firstname" 
                           value="" ></td>
            </tr>
            <tr>
                <td>Middle Nm:</td>
                <td><input type="text" id="middlename" name="middlename" 
                           value="" ></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td><input type="text" id="status" name="status" 
                           value="" ></td>
            <tr>
            <tr>
                <td>Member Date:</td>
                <td><input type="text" id="memdt" name="memdt" 
                           value="" ></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" id="psswd" name="psswd" 
                           value="" size="22"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Update Member data"></td>
            </tr>
            </table>
        </form>
        <br>
         <hr>
         <br>View Transaction History From:<br>
            <form action="ShowPurchases" method="post" >
                <table>
                    <tr>
                        <td>Month:</td><td><input type="text" name="month" 
                                                  id="month" value=""></td>
                        <td>Day:</td><td><input type="text" name="day" 
                                                id="day" value=""></td>
                        <td>Year:</td><td><input type="text" name="year" 
                                                 id="year" value=""></td>
                    </tr>
                </table><br>
                <input  type="submit" value="View Transactions">
            </form> <br>
         <br><br>
         <a href="/ClubDB">Back to the Login Screen</a>
       </body>
</html>