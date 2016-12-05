<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/styles.css" rel="stylesheet" type="text/css" />
		<link rel="icon" href="favicon.ico" type="image/x-icon" />
		<title>Hangman!</title>
	</head>
	<body>
		<form action="logout" method="post" >
			<button class="logout" type="submit" name="logout" value="true">Log Out</Button>
		</form>
		<h1>Hangman!</h1>
		<fieldset>
            <legend>${ sessionScope.user.getUserName() }'s Wrong Guesses: ${ sessionScope.user.getWrongGuesses() }/7</legend>
            	<img src="img/${ sessionScope.user.getWrongGuesses() }.png" alt="${ sessionScope.user.getWrongGuesses() } wrong guesses!" height="220px" width="220px"> 
		</fieldset>
		<h3>Word To Find:</h3>
		<h1 style="letter-spacing: 5px;">${ sessionScope.user.getGuessedWord() }</h1>
		<form action="play" method="post">
			<p>Make A Letter Guess: <input autofocus type="text" name="letter" maxlength="1" size="1" onkeypress="return isAlpha(event)" />
			<Button type="submit" value="guess">Guess!</Button></p>
		</form>
		<h3> ${ letterError } </h3>
	</body>
	<script>
		function isAlpha(evt) {
		    evt = (evt) ? evt : window.event;
		    var charCode = (evt.which) ? evt.which : evt.keyCode;
		    if (charCode > 31 && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122)) 
		    {
		        return false;
		    }
		    return true;
		}
	</script>
</html>