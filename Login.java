package servlet;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ObjectMapper map = new ObjectMapper();
	ServletInputStream serv;
	ServletOutputStream servout;
	User obj;
	int y;
	
    public Login() 
    {
        super();
    }

    
    
    protected void doGet(HttpServletRequest request,
    		             HttpServletResponse response) throws ServletException, IOException 
	{
	
	}
    
    
	protected void doPost(HttpServletRequest request, 
				          HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			serv=request.getInputStream();
			obj = map.readValue(serv,User.class);
			Jdbc j=new Jdbc();
			y = j.userCheck(obj);
			servout = response.getOutputStream();
			if(y==1)
			{
				servout.println("Login Successful");
			}
			else
			{
				servout.println("Login Failed");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
	}
}
