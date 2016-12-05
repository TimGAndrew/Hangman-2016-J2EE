package j2eefinal.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import j2eefinal.model.User;

/**Class FileHandler handles the file reading**/
public class FileHandler
{
    /**private constructor to prevent instantiation.**/
    private FileHandler(){}
    
    /**class variables for file writing and reading:**/
    private static final String FILE_DELIMITER = ":";


    /**public method to write the users ArrayList data file**
     * @throws IOException when an error is thrown while writing the data file.**/
    public static void WriteUsers(ArrayList<User> users, String path) throws IOException
    {
            //Create a string to hold all the userlist data:
        String userData = "";
        
            //try to overwirte the file (PS: If you wanted to append change (path) to (path, true):
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path)))
        {
                //iterate through the users list:
            for (int i = 0 ; i < users.size() ; i++)
            {
                    //create a string for the user:
                userData = ( users.get(i).getUserName() + FILE_DELIMITER + 
                            users.get(i).getPassword() + FILE_DELIMITER + 
                            users.get(i).getGameWord() + FILE_DELIMITER +
                            users.get(i).getGuessedWord() + FILE_DELIMITER +
                            users.get(i).getWrongGuesses() + FILE_DELIMITER +
                            users.get(i).getHighScore()
                            );
                    //Write the user:
                bw.write(userData);
                bw.newLine();
            }
                //Close the file
            bw.close();
        } 
        /**no catch here, because it's handled by Business class**/
  
    }/**end WriteUsers**/
    
    
    /**Public method to build an array list of words**
     * @return ArrayList of words
     * @throws IOException when unable to read the file**/
    public static ArrayList<String> BuildWords(final String fileName) throws IOException
    {
            //Create an Array List to store the words from the wordlist file:
        ArrayList<String> words = new ArrayList<String>();
            
            //Create a temporary string to hold each line of data from the file:
        String temp;
        
            //Try reading the data file:
        try (BufferedReader bfr = new BufferedReader (new FileReader(fileName)))
        {
                //while the next line is not empty:
            while ((temp = bfr.readLine()) !=null)
            {
                    //add the word to the array:
                words.add(temp);
            }
        }
        /**no catch here, because it's handled by Business class**/
        
            //Return the words array:
        return words;
    }/**end of BuildWords()**/
    
    
    /**public method to build an array list of users**
     * @return ArrayList of users
     * @throws IOException when an error is thrown while reading the data file.**/
    public static ArrayList<User> BuildUsers(final String fileName) throws IOException
    {
            //create a new array list of users to hold the data in the userlist file:
        ArrayList<User> users = new ArrayList<User>();
        
            //Create a new temporary string to hold each line of data to parse
        String temp;
      
            //Try reading the data file
        try (BufferedReader bfr = new BufferedReader(new FileReader(fileName)))
        {
                //While each line is not null
            while ((temp = bfr.readLine()) != null)
            {
                    //Take the line (userName:password:gameWord:guessedWord:wrongGuesses:highScore) and split it at the :'s:
                String[] data = temp.split(FILE_DELIMITER);

                    //Create the new user
                User newUser = new User(data[0], data[1], data[2], data[3], Integer.valueOf(data[4]), Double.parseDouble(data[5]));
                
                    //Add that new user to the array of users:
                users.add(newUser);
            }
        }
        /**no catch here, because it's handled by Business class**/
        
            //return the users:
        return users;
        
    }/**end of BuildUsers()**/
}
