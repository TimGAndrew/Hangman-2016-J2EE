<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/styles.css" rel="stylesheet" type="text/css" />
		<link rel="icon" href="favicon.ico" type="image/x-icon" />
		<title>Play Hangman!</title>
	</head>
	<body>
		<form action="logout" method="post" >
				<button class="logout" type="submit" name="logout" value="true">Log Out</Button>
		</form>
		<h1>Welcome, ${ sessionScope.user.getUserName() }!!</h1>
		<h2>Play Hangman!</h2>
		
		<form action="play" method="post">
			<Button type="submit">O.K!</Button>
		</form>
		<br/>
		
			<!-- The High Scores Box: -->
		<fieldset>
            <legend>Current Hangman High Scores</legend>
				${ sessionScope.HighScores }
		</fieldset>
		
		<h3>Your high score is ${ sessionScope.user.getHighScore() }!</h3>

	</body>
</html>