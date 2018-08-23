package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Jdbc {
	
	Connection conn=null;
	Statement statement1;
	PreparedStatement statement2;
	ResultSet rs=null;
	String query1,
	 	   query2,
	 	   name,
	 	   uname,
	 	   password,
	 	   p,
	 	   id,
	 	   email;
	int q1,
		q2;
	Pattern pat;
	Matcher m;
	
	List<Cart> result = new ArrayList<Cart>();
	
	protected Jdbc() throws SQLException, ClassNotFoundException, IOException 
	{
		Properties p=new Properties();
		InputStream fis=null;
      	fis=Jdbc.class.getResourceAsStream("connection.properties");
        p.load(fis); 
        String driver = p.getProperty("DRIVER"); 
        String url=  p.getProperty("URL"); 
        String user=  p.getProperty("USER"); 
        String password= p.getProperty("PASSWORD"); 
    	Class.forName(driver.toString());
		conn=DriverManager.getConnection(url.toString(),user.toString()
				                         ,password.toString());
	}
	
	protected int userInsert(User obj) throws SQLException
	{
		try
		{
			email = obj.getEmail();
			pat = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
									Pattern.CASE_INSENSITIVE);
			m = pat.matcher(email);
			if(m.find())
			{
				query1="Insert into User Values(?,?,?,?);";
				statement2 = conn.prepareStatement(query1);
				statement2.setString(1, obj.getName());
				statement2.setString(2, obj.getUsername());
				statement2.setString(3, obj.getEmail());
				statement2.setString(4, obj.getPassword());
				statement2.executeUpdate();
				return 1;
			}
			else
			{
				return 2;
			}
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	
	
	protected int userCheck(User obj) throws SQLException
	{
		name = obj.getUsername();
		password = obj.getPassword();
		query1 = "Select * From User";
		statement1 = conn.createStatement();
        rs = statement1.executeQuery(query1);
        while(rs.next())
        {
        	uname = rs.getString("Username");
			p=rs.getString("Password");
			if((uname.equals(name))&&(password.equals(p)))
			{
				return 1;
			}
        }
        return 0;
	}
	
	
	protected int cartInsert(Cart obj2) throws SQLException
	{
		try
		{
			query1="Insert into Cart Values(?,?);";
		    statement2 = conn.prepareStatement(query1);
			if(obj2.getQuantity()<=0)
			{
				return 0;
			}
		    statement2.setString(1, obj2.getItem_id());
		    statement2.setInt(2, obj2.getQuantity());
		    statement2.executeUpdate();
		    return 1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	protected int cartUpdate(Cart obj2) throws SQLException
	{
		try
		{
			q1=obj2.getQuantity();
			id=obj2.getItem_id();
			query1="Select Quantity from Cart where Item_ID=(?);";
			statement2 = conn.prepareStatement(query1);
			statement2.setString(1, id);
			rs = statement2.executeQuery();
			while(rs.next())
			{
				q2=rs.getInt("Quantity");
			 
			}
			query2="Update Cart set Quantity=(?) where Item_ID=(?);";
			statement2 = conn.prepareStatement(query2);
			statement2.setInt(1,(q1+q2));
			statement2.setString(2, id);
			statement2.executeUpdate();
			return 1;
		  }
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	protected int cartDelete(Cart obj2) throws SQLException
	{
		try
		{
			query1="Select Quantity from Cart where Item_ID=(?);";
			statement2 = conn.prepareStatement(query1);
			statement2.setString(1, obj2.getItem_id());
			rs = statement2.executeQuery();
			if(!rs.next())
			{
				return 0;
			}
			query1="Delete From Cart Where Item_ID=(?);";
			statement2 = conn.prepareStatement(query1);
		    statement2.setString(1, obj2.getItem_id());
		    statement2.executeUpdate();
		    return 1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	protected List<Cart> cartDisplay(int start,int total) throws SQLException
	{
		try
		{
			query1="Select * from Cart,Items where Cart.Item_ID=Items.Item_ID limit "+(start-1)+","+total+";";
			statement1 = conn.createStatement();
			rs = statement1.executeQuery(query1);
			while(rs.next())
			{
				id = rs.getString("Item_ID");
			    q1 =rs.getInt("Quantity");
			    name=rs.getString("Item_Name");
			    Cart cart = new Cart();
	            cart.setItem_id(id);
	            cart.setQuantity(q1);
	            cart.setName(name);
	            result.add(cart);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}	
	
}

