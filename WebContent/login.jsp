<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/styles.css" rel="stylesheet" type="text/css" />
		<link rel="icon" href="favicon.ico" type="image/x-icon" />
		<title>Hangman Log-In</title>
    </head>
    <body>
    	<h1>Please Log In To Play Hangman</h1>

            <form action="game.jsp" method="post">
                <fieldset>
                    <legend>Hangman Log-In Form</legend>
                    <Table>
                    <tr><td></td><td></td></tr>
                    <tr>
                        <td style="text-align:right;min-width:100%;">Username</td>
                        <td style="text-align:center;">
                            <input type="text" name="username" style="min-width:100%"></td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">Password</td>
                        <td style="text-align:center;">
                            <input type="password" name="password" style="min-width:100%"></td>
                    </tr>
                    <tr><td colspan="2">${loginError}</td></tr>   
                    <tr>
                        <td style="text-align:left;">
                            <Button type="submit" value="register">Log In</Button></td>
                        <td><a href="register.jsp">Go register</a></td>
                    </tr> 
                    </Table>
                </fieldset>         
            </form> 
    </body>
</html>