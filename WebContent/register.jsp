<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/styles.css" rel="stylesheet" type="text/css" />
		<link rel="icon" href="favicon.ico" type="image/x-icon" />
    </head>
    <body>
    	<h1>Only Registered Users Can Play Hangman</h1>

            <form action="register.jsp" method="post">
                <fieldset>
                    <legend>Hangman Registration Form</legend>
                    <Table>
                    <tr><td></td><td></td></tr>
                     <tr>
                        <td style="text-align:right;">Desired Username</td>
                        <td style="text-align:center;">
                            <input type="text" name="username" style="min-width:100%"></td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">Desired Password</td>
                        <td style="text-align:center;">
                            <input type="password" name="password" style="min-width:100%"></td>
                    </tr>
                    <tr><td colspan="2">${registerError}</td></tr>    
                    <tr>
                        <td style="text-align:left;">
                            <Button type="submit" value="register">Register</Button></td>
                        <td></td>
                    </tr> 
                    </Table>
                </fieldset>         
            </form> 
    </body>
</html>