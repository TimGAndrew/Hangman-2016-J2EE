package j2eefinal.model;

    //class to handle the user data, implements COmparable so it can be sorted by high scores.
public class User  implements Comparable<User>
{
    
/**CLASS VARIABLES**/
        //String to hold the user's name:
    private String userName;
        //String to hold the user's password:
    private String password;
        //double to hold the users high score (max: 100.00)
    private double highScore;
        //string to hold the user's current game word:
    private String gameWord;
        //string to hold the current user's word found (will have underscores. ex: gameWord is 'superman', guessedWord='s_per_an'):
    private String guessedWord;
        //string to hold the user's current wrongGuesses:
    private int wrongGuesses;
    
    
/**CONSTRUCTORS**/
        //Constructor for a new player: requires userName and Password, and sets the user's high score to 0.0 and wrongGuesses to 0;
    public User(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
        this.gameWord = "";
        this.guessedWord = "";
        this.wrongGuesses = 0;
        this.highScore = 0.0;
    }
    
        //Constructor for a saved player: requires all user data in the file:
    public User(String userName, String password, String gameWord, String guessedWord, int wrongGuesses, double highScore)
    {
        this.userName = userName;
        this.password = password;
        this.highScore = highScore;
        this.gameWord = gameWord;
        this.guessedWord = guessedWord;
        this.wrongGuesses = wrongGuesses;
    }


/**GETTERS & SETTERS**/
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public double getHighScore()
    {
        return highScore;
    }

    public void setHighScore(double highScore)
    {
        this.highScore = highScore;
    }

    public String getGameWord()
    {
        return gameWord;
    }

    public void setGameWord(String gameWord)
    {
        this.gameWord = gameWord;
    }

    public String getGuessedWord()
    {
        return guessedWord;
    }

    public void setGuessedWord(String guessedWord)
    {
        this.guessedWord = guessedWord;
    }

    public int getWrongGuesses()
    {
        return wrongGuesses;
    }

    public void setWrongGuesses(int wrongGuesses)
    {
        this.wrongGuesses = wrongGuesses;
    }

        //compareTo method to sort the class by high scores:
    @Override
    public int compareTo(User o)
    {
            return new Double(highScore).compareTo(o.highScore);
    }


}
