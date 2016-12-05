package j2eefinal.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import j2eefinal.controller.Business;
import j2eefinal.model.User;

/**Servlet Filter implementation class Register**/
@WebFilter(filterName="register", 
           description = "To handle registration:", 
               //url patterns to catch:
           urlPatterns = { "/register.jsp"})

public class Register implements Filter {

    /**Default constructor.*/
    public Register() {/*empty*/}

    /**destroy filter**/
    public void destroy() {/*empty*/}

    /**login filter**/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
            //Set up an ArrayList of users from the Business class:
        ArrayList<User> users = Business.users;
        
            //Set up some variables to handle username/password:
        String username = request.getParameter("username");
        String password = request.getParameter("password");
            /**The following line test the output of these**/
        //System.out.println(password + ", " + username);  // output:  "null, null" on load, but ", " on login button
         
            //Create an http session for the user:
        HttpSession session = ((HttpServletRequest) request).getSession(true);
        
            //If a session exists for the user:
         if (session.getAttribute("user") !=null)
         {
                 //Send the user to the game:
             request.getRequestDispatcher("game.jsp").forward(request, response);
         }
             //Otherwise, if this is the first page load and username/password are null:
         else if ((username == null) || (password == null))
         {
                 //invalidate session (I think - NOT SURE IF THIS WORKS?)
             session.invalidate();
             
                 //Send the user to their register.jsp:
             chain.doFilter(request, response);
         }
             //otherwise if either box has empty strings when the user clicks register:
         else if ((username.equals("")) || (password.equals("")))
         {
                 //Set a session variable to display in the page:
             session.setAttribute("registerError", "Please Enter A Username And Password!");
             
                 //Then send them back to the register page:
             request.getRequestDispatcher("register.jsp").forward(request, response);
         }
             //Otherwise if the username is already taken:
         else if (isUsernameTaken(users, username))
         {
                 //Set a session variable to display in the page:
             session.setAttribute("registerError", "That Username is already taken!");
             
                 //Then send them back to the register page:
             request.getRequestDispatcher("register.jsp").forward(request, response);
         }
             //Otherwise add the user:
         else
         {
                 //create a new user:
             User newUser = new User(username, password);
             
                 //Add the user to the Business.users array:
             Business.users.add(newUser);
             
                 //Then set the session variables for that user:
             session.setAttribute("user",  newUser);
             
                 //Next, create a variable to hold the high scores:
             String HighScores = Business.getHighScoresTable();
             
                 //set the variable for the high scores
             session.setAttribute("HighScores", HighScores);
              
                 //Send the user to the game:
             request.getRequestDispatcher("game.jsp").forward(request, response);

         }
	}

	/**init filter**/
	public void init(FilterConfig fConfig) throws ServletException {/*empty*/}
	
	   /**Check if user exists or not**/
    private boolean isUsernameTaken(ArrayList<User> users, String username)
    {
            //for each element in the users array:
        for (int i = 0; i < users.size() ; i++)
        {
                //find out if the username entered exists:
            if (users.get(i).getUserName().equals(username))
            {
                return true;
            }
        }
        
        return false;
    }

}
