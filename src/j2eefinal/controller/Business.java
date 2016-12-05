package j2eefinal.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import j2eefinal.model.User;
import j2eefinal.utility.FileHandler;

/**Some settings for this web servlet**/
@WebServlet(
		    //Servlet Description:
        description = "This servlet handles the login/register/game", 
        
            //Patterns that this servlet catches:
		urlPatterns = { "/play",
		                "/logout",
		                //"/game", 
        				//"/login", 
        				//"/register" 
		        },
		
		    //Initialization of files (which need to be handled by FileHandler.java):
		initParams = {@WebInitParam(name="wordlist",value="/WEB-INF/wordlist.txt"),
		              @WebInitParam(name="userlist",value="/WEB-INF/userlist.txt")},
		
		    //Set this to run init() on startup:
		loadOnStartup = 1)

/**Class Business is a Servlet**/
public class Business extends HttpServlet 
{    
    /**Class Variables**/
    private static final long serialVersionUID = 1L; // This is Auto-generated with servlet (no idea what it does)
	    //Initialize an array of User objects:
    public static ArrayList<User> users;
        //Initialize an array of words:
    public static ArrayList<String> words;
        //initialize a userFilePath (it is private as it's found on the init() but written to on destroy()):
    private static String userFilePath = "";
    
       
    /**Business constructor**/
    public Business() 
    {
        super(); // This is an Auto-generated constructor stub
    }

    
    /**Business Methods**/
        /**This is what happens when the servlet is initialized**/
	public void init(ServletConfig config) throws ServletException 
	{
	        /**Prints to the console to show that init works**/
	    //System.out.println("init() triggered");
	    
	        //Set up a URL variable to handle the two files in initParams (above)
	    URL url;
	    
	        /**1 - Try to populate the words ArrayList with the words in 'wordlist'**/
        try
        {
                //Set url to the wordlist:
            url = config.getServletContext().getResource(config.getInitParameter("wordlist"));
            
                //Set a String variable to hold url's path:
            String path = url.getPath();
            
                //get the words ArrayList from FileHandler:
            words = FileHandler.BuildWords(path);
        }
            //If this didn't work:
        catch (IOException e)
        {
            throw new RuntimeException ("ERROR READING THE WORDLIST");
        }
	        
            /**Prints all the words to the console to test if it worked:**/
	    //for (int i = 0; i < words.size(); i++){System.out.println(words.get(i));}
        
	    
            /**2 - Try to populate the Populate users ArrayList with the users 'userlist'**/
        try
        {
                //Set url to the userlist:
            url = config.getServletContext().getResource(config.getInitParameter("userlist"));
            
                //Set a String variable to hold url's path:
            userFilePath = url.getPath();
            
                //get the words ArrayList from FileHandler:
            users = FileHandler.BuildUsers(userFilePath);
        }
            //If this didn't work:
        catch (IOException e)
        {
            throw new RuntimeException ("ERROR READING THE USERLIST");
        }
            
            /**Prints all the user data to the console to test if it worked:**/
        /*for (int i = 0; i < users.size(); i++)
        {
            System.out.println("-USER-" + i+"---------------------");
            System.out.println("userName:\t" + users.get(i).getUserName());
            System.out.println("password:\t" + users.get(i).getPassword());
            System.out.println("gameWord:\t" + users.get(i).getGameWord());
            System.out.println("guessedWord:\t" + users.get(i).getGuessedWord());
            System.out.println("wrongGuesses:\t" + users.get(i).getWrongGuesses());
            System.out.println("highScore:\t" + users.get(i).getHighScore());
            System.out.println("----------------------------");
        } */ 
        System.out.println(userFilePath);
            
	}/**end init()**/

	    /**This is what happens when the servlet is destroyed:**/
	public void destroy() 
	{
            /**Try to write the users ArrayList to the 'userlist'**/
         try
         {
                 //Call the filehandler function:
             FileHandler.WriteUsers(users, userFilePath);
         }
             //If this didn't work:
         catch (IOException e)
         {
             throw new RuntimeException ("ERROR WRITING THE USERLIST");
         }
		
	}

	    /**This is what happens when a Get request is received through a Pattern this servlet catches**/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	    request.getRequestDispatcher("game.jsp").forward(request, response);
	}

	    /**This is what happens when a Post request is received through a Pattern this servlet catches**/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

	        //get logout value:
	    String logout = request.getParameter("logout");
	    
	        //get the http session:
	    HttpSession session = request.getSession(true);
	    
            //get the user's object fromt that session:
         User user = (User) session.getAttribute("user");
     
            //handles /logout :

	    if (logout != null)
	    {
                //update and save user:
            UpdateAndSaveUser(user);
	            //destroy session and send user to login.jsp
	        session.invalidate();
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    
	        //handles /play :
	    

	    

	    
	        //request the letter (if there is one) from the game
	    String letter = request.getParameter("letter");
	    
	        //handle if the letter is not null: 
	    if (letter != null)
	    {
	            //handle if the user didn't enter a letter:
	        if (letter.equals(""))
	        {
	                //set an error:
	            session.setAttribute("letterError", "Enter a Letter!");
	                //send the user back to the page:
	            request.getRequestDispatcher("play.jsp").forward(request, response);
	            
	        }
	            //handle the user entered letter
	        else
	        {
	                //set the letter to lowercase
	            letter = letter.toLowerCase();
	                
	                    //if the gameword and guessedWord contains the letter (and it was already picked)
	                if (user.getGameWord().contains(letter) && user.getGuessedWord().contains(letter))
	                {
	                        //set an error:
	                    session.setAttribute("letterError", "You already found that letter!");
	                        //send the user back to the page:
	                    request.getRequestDispatcher("play.jsp").forward(request, response);
	                }
	                    //if the gameword doesn't contain that letter
	                else if  (!user.getGameWord().contains(letter))
	                {
	                        //increment the wrong guesses by one
	                    user.setWrongGuesses(user.getWrongGuesses() + 1);
	                    
	                    if (user.getWrongGuesses() == 7)
	                    {
	                            //set that the user has lost
	                        session.setAttribute("gameResult", "Lost");
	                        session.setAttribute("theWordWas", user.getGameWord());
	                        
	                            //reset the user's values:
	                        user.setGuessedWord("");
	                        user.setGameWord("");
	                        user.setWrongGuesses(0);
	                     
	                            //set the user as the user:
                            session.setAttribute("user", user);
                                //update and save user:
                            UpdateAndSaveUser(user);
                                //get the variable for the high scores
                            session.setAttribute("HighScores", getHighScoresTable());
	                            //send the user to the gameover screen:
	                        request.getRequestDispatcher("gameover.jsp").forward(request, response);
	                    }
	                    else
	                    {
    	                       //set an error:
                            session.setAttribute("letterError", "That letter isn't in the word! Wrong guesses = " + user.getWrongGuesses() + "!");
                                //set the user as the user:
                            session.setAttribute("user", user);
                                //update and save user:
                            UpdateAndSaveUser(user);
                                //send the user back to the page:
                            request.getRequestDispatcher("play.jsp").forward(request, response);
	                    }
	                }
	                    //otherwise letter is in the word and wasn't picked yet:
	                else
	                {
	                        //convert things to chars:
	                    char[] gameword = user.getGameWord().toCharArray();
	                    char[] guessedword = user.getGuessedWord().toCharArray();
	                    char searchLetter = letter.charAt(0);

	                    
	                        //iterate through gameword :
	                    for (int i = 0 ; i < gameword.length ; i++)
	                    {
	                            //if the letter in the gameword is the search letter
                            if (gameword[i] == searchLetter)
                            {
                                        //replace that index in guessed word:
	                                guessedword[i] = searchLetter;
	                        }
	                    }
	                        //then set the updated guessed word to the user:
	                    String newGuessedWord = String.valueOf(guessedword);
	                    user.setGuessedWord(newGuessedWord);
	                    
	                        //check if the guessedword still contains _'s
	                    if (newGuessedWord.contains("_"))
	                    {
	                            //send the user back to the page:
	                            //set an error:
                          session.setAttribute("letterError", "You got that right!");
                              //set the user as the user:
                          session.setAttribute("user", user);
                              //update and save user:
                          UpdateAndSaveUser(user);
                              //send the user back to the page:
                          request.getRequestDispatcher("play.jsp").forward(request, response);
	                        
	                    }
	                        //otherwise the user has won the game:
	                    else
	                    {

	                            //calculate theri score
	                        double newScore;
	                        double NumberOfLetters = (double)  user.getGameWord().length();
	                        double NumberOfWrongGuesses = (double)  user.getWrongGuesses();
	                        
	                        newScore = ((NumberOfLetters - NumberOfWrongGuesses) / NumberOfLetters) *100;
	                        
	                            //set the double to just 2 decimal places:
	                        newScore = Math.round(newScore*1e2)/1e2;
	                        
	                            //compare the newScore with the user's current high score:
	                        if (newScore > user.getHighScore())
	                        {
	                                //if it's higher set it as the new high score:
	                            user.setHighScore(newScore);
	                        }
	                        
	                        
	                            //set that the user has lost
                            session.setAttribute("gameResult", "Won");
                            session.setAttribute("theWordWas", user.getGameWord());
                            
                                //reset the user's values:
                            user.setGuessedWord("");
                            user.setGameWord("");
                            user.setWrongGuesses(0);
                         
                                //set the user as the user:
                            session.setAttribute("user", user);
                                //update and save user:
                            UpdateAndSaveUser(user);
                                //get the variable for the high scores
                            session.setAttribute("HighScores", getHighScoresTable());
                                //send the user to the gameover screen:
                            request.getRequestDispatcher("gameover.jsp").forward(request, response);
	                    }
	                    
	                }
	                
	            
	            
	                //Send the user back to the page:
	            //request.getRequestDispatcher("play.jsp").forward(request, response);
	        }
	        
	        
	    }
	        //if the user has a game word (game is in progress):
	    else if (!user.getGameWord().equals(""))
	    {
	            //then send the user to the game page:
	        request.getRequestDispatcher("play.jsp").forward(request, response);
	    }
            //if the user has no game word (game yet to be started):
	    else if (user.getGameWord().equals(""))
	    {
	            //set a new game word:
	        String newGameWord = pickRandomWord();
	        
	            //set a new guessed word:
	        String newGuessedWord = "";
	        
	            //build an empty guessed word:
	        for (int i = 0 ; i < newGameWord.length() ; i++)
	        {
	                //add underscores to it:
	            newGuessedWord += "_";
	        }
	        
	            //set the new strings to the user.
	        user.setGameWord(newGameWord);
	        user.setGuessedWord(newGuessedWord);
	        
                //set the user as the user:
            session.setAttribute("user", user);
                //send the user to the play screen:
            request.getRequestDispatcher("play.jsp").forward(request, response);
	        
	    }

	}
	
	    //a method to pick a random word to play the game:
	public static String pickRandomWord()
	{
	        //set up a new random generator to pick from the word list:
	    int index = new Random().nextInt(words.size());
	    
            //set up a variable to hold the word:
	    String word = (words.get(index));
	    
	        //return the word:
	    return word;
	}
	
	public static void UpdateAndSaveUser(User user)
	{
	        //set up an index variable
	    int index = -1;
	    
	        //iterate through users array to find user name
	    for (int i = 0 ; i < users.size() ; i++)
	    {
	            //to find the user's index that equals to the same username:
	        if (users.get(i).getUserName().equals(user.getUserName()))
	        {
	            index = i;
	            break;
	        }
	    }
	    
	        //update the fields for the user:
	    users.get(index).setUserName(user.getUserName());
	    users.get(index).setPassword(user.getPassword());
	    users.get(index).setGameWord(user.getGameWord());
	    users.get(index).setGuessedWord(user.getGuessedWord());
	    users.get(index).setWrongGuesses(user.getWrongGuesses());
	    users.get(index).setHighScore(user.getHighScore());
	    
	    
	    
            /**Try to write the users ArrayList to the 'userlist'**/
         try
         {
                 //Call the filehandler function:
             FileHandler.WriteUsers(users, userFilePath);
         }
             //If this didn't work:
         catch (IOException e)
         {
             throw new RuntimeException ("ERROR WRITING THE USERLIST");
         }
    	    
	    
	}
	
	public static String getHighScoresTable()
	{
	    
            //create variables to hold the strings:
        String openString = "<table class=\"hs\"><tr style=\"background:black;\"><td><h3>User:</h3></td><td><h3>Score:</h3></td></tr>";
        String closeString = "</table>";
        String Scores = "";
        
            //create a new user array:
        ArrayList<User> newArray = new ArrayList<User>();
            //set it to users
        newArray = users;
        
            //sort the new array based on High Score (highest to lowest):
        Collections.sort(newArray, Collections.reverseOrder());
 
            //determine high scores:
        for (int i = 0 ; i < 5 ; i++)
        {
            Scores += ("<tr><td>" + 
                        newArray.get(i).getUserName() + 
                        "</td><td>" +
                        newArray.get(i).getHighScore() +
                        "</td></tr>");
        }

            //concatenate the string:
        String HighScores = openString + Scores + closeString;

        return HighScores;
	}

}
