package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Cart")
public class CartOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ServletInputStream serv;
	ServletOutputStream servout;
    ObjectMapper map = new ObjectMapper();
    int y,
        page,
        total;
    String out,
    	   pageid;
    Cart obj2;
    
    public CartOperations() 
    {
        super();
    }

    
	protected void doGet(HttpServletRequest request, 
						HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			servout = response.getOutputStream();
			response.setContentType("application/json");
			pageid=request.getParameter("page");
			total=5; 
			if(pageid==null)
			{
				page=1;
			}
			else
			{
				page=Integer.parseInt(pageid);
				page=((page-1)*total)+1;
			}
			Jdbc j=new Jdbc();
			List<Cart> cartList=j.cartDisplay(page,total);
		   	out = map.writeValueAsString(cartList);
		   	
		   	if(out.contains("[]"))
		   	{
		   		response.setContentType("text");
		   		servout.println("No Data to Display");
		   	}
		   	else
		   	{
		   		servout.println(out);	
		   	} 
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	protected void doPost(HttpServletRequest request, 
						  HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			serv=request.getInputStream();
			obj2 = map.readValue(serv,Cart.class);
			Jdbc j=new Jdbc();
			y = j.cartInsert(obj2);
			servout = response.getOutputStream();
		    if(y==1)
		    {
		    	servout.println("Addition Success");
		    }
		    else
		    {
		    	servout.println("Failed to add item");
		    }
		   } 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	protected void doPut(HttpServletRequest request, 
						 HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			serv=request.getInputStream();
			obj2 = map.readValue(serv,Cart.class);
			Jdbc j=new Jdbc();
			y = j.cartUpdate(obj2);
		    servout = response.getOutputStream();
		    if(y==1)
		    {
		    	servout.println("Updation Success");
		    }
		    else
		    {
		    	servout.println("Failed to update item");
		    }
		   } 
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	protected void doDelete(HttpServletRequest request, 
							HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			serv=request.getInputStream();
			obj2 = map.readValue(serv,Cart.class);
			Jdbc j=new Jdbc();
			y = j.cartDelete(obj2);
			servout = response.getOutputStream();
		    if(y==1)
		    {
		    	servout.println("Deletion Success");
		    }
		    else
		    {
		    	servout.println("Failed to delete item");
		    }
		   } 
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
