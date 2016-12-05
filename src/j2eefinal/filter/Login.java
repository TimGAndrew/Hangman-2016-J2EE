package j2eefinal.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import j2eefinal.controller.Business;
import j2eefinal.model.User;

/**Servlet Filter implementation class Login**/
@WebFilter(filterName="login", 
           description = "To handle login and make sure user is logged in before accessing these pages:", 
               //url patterns to catch:
           urlPatterns = { "/", 
                           "/login.jsp", 
                           "/game.jsp",
                           "/gameover.jsp",
                           "/play.jsp"})

public class Login implements Filter {

    /**Default constructor.*/
    public Login() {/*empty*/}

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

            //If session exists for the user:
        if (session.getAttribute("user") !=null)
        {
            
                //Next, create a variable to hold the high scores:
            String HighScores = Business.getHighScoresTable();
            
                //set the variable for the high scores
            session.setAttribute("HighScores", HighScores);
             
        
                //Send the user to their requested page:
            request.getRequestDispatcher("game.jsp").forward(request, response);
        }
            //Otherwise, if this is the first page load and username/password are null:
        else if ((username == null) || (password == null))
        {
                //invalidate session (I think - NOT SURE IF THIS WORKS?)
            session.invalidate();
            
                //send them to the login page:
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
            //otherwise if either box has empty strings when the user clicks login:
        else if ((username.equals("")) || (password.equals("")))
        {
                //Set a session variable to display in the page:
            session.setAttribute("loginError", "Please Enter Valid Credentials or Register!");
            
                //Then end them back to the login page:
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
           //Otherwise check if the username and password exists:
        else if(checkUser(users, username, password))
        {
                //if so, set up an index variable to get the user from the users list
            int index = -1;
            
                //Iterate through users to get the index of the user:
            for (int i = 0; i < users.size() ; i++)
            {
                if (users.get(i).getUserName().equals(username) && users.get(i).getPassword().equals(password))
                {
                    index = i;
                    break;
                }
            }
            
                //Then set the session variables for that user:
            User user = users.get(index);
            session.setAttribute("user",  user);
            
                //Next, create a variable to hold the high scores:
            String HighScores = Business.getHighScoresTable();
            
                //set the variable for the high scores
            session.setAttribute("HighScores", HighScores);

           
                //Send the user to their requested page:
            request.getRequestDispatcher("/play").forward(request, response);
            //chain.doFilter(request, response);
         }
            //Otherwise, there was no valid user entered, so send the user to register.jsp:
        else
            {
                    //invalidate session (I think - NOT SURE IF THIS WORKS?)
                session.invalidate();
                
                    //then end them to the login page:
                RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
                rd.forward(request, response);
            }
	}

	/**Filter's init()**/
	public void init(FilterConfig fConfig) throws ServletException {/*empty*/}
	
	/**Check if user exists or not**/
	private boolean checkUser(ArrayList<User> users, String username, String password)
	{
	    for (int i = 0; i < users.size() ; i++)
	    {
	        if (users.get(i).getUserName().equals(username) && users.get(i).getPassword().equals(password))
	        {
	            return true;
	        }
	    }
	    
	    return false;
	}

}
