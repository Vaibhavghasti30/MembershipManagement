package com.techpalle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.techpalle.model.Customer;

public class CustomerDao 
{
	private final static String dburl = "jdbc:mysql://localhost:3306/customer_management";
    private final static String dbusername = "root";
    private final static String dbpassword = "Admin";

    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static Statement st = null;
    private static ResultSet rs = null;
    
    private static final String customerlistQuery   = "select * from customer";
    private static final String customerinsertQuery = "insert into customer(name,email,mobile) values(?,?,?)";
    private static final String customerEditQuery   = "select * from customer where id=?";
    private static final String customerUpdateQuery = "update customer set name=?, email=?, mobile=? where id=?";
    private static final String customerDeleteQuery = "delete from customer where id=?";
    private static final String adminvalidateQuery  = "select username,password from admin where username=? and password=?";
    
    public static boolean validateAdmin(Admin a)
    {
    	boolean b  = false;
    	try {
        	con = getConnectionDef();
			ps = con.prepareStatement(adminvalidateQuery);
			ps.setString(1, a.getUsername());
			ps.setString(2, a.getPassword());
			rs = ps.executeQuery();
			b = rs.next();
		}
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    	finally 
    	{
    		if(ps != null)
    		{
    			try 
    			{
					ps.close();
				}
    			catch (SQLException e) 
    			{
					e.printStackTrace();
				}
    		}
    		if(con != null)
    		{
    			try
    			{
					con.close();
				} 
    			catch (SQLException e)
    			{
					e.printStackTrace();
				}
    		}
    	}
		return b;
    }
    public static void deleteCustomer(int id)
    {
    	try
    	{
        	con = getConnectionDef();
			ps = con.prepareStatement(customerDeleteQuery);
			ps.setInt(1, id);
			ps.executeUpdate();
		} 
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
    	finally 
    	{
    		if(ps != null)
    		{
    			try 
    			{
					ps.close();
				}
    			catch (SQLException e) 
    			{
					e.printStackTrace();
				}
    		}
    		if(con != null)
    		{
    			try
    			{
					con.close();
				} 
    			catch (SQLException e)
    			{
					e.printStackTrace();
				}
    		}
    	}
    }
    public static void updateCustomer(Customer c)
    {
    	
    	try {
        	con = getConnectionDef();
			ps  = con.prepareStatement(customerUpdateQuery);
			ps.setString(1, c.getName());
			ps.setString(2, c.getEmail());
			ps.setLong(3, c.getMobile());
			ps.setInt(4, c.getId());
			ps.executeUpdate();
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
    	finally 
    	{
    		if(ps != null)
    		{
    			try 
    			{
					ps.close();
				}
    			catch (SQLException e) 
    			{
					e.printStackTrace();
				}
    		}
    		if(con != null)
    		{
    			try
    			{
					con.close();
				} 
    			catch (SQLException e)
    			{
					e.printStackTrace();
				}
    		}
    	}
    }
    
    public static Customer getOneCustomer(int id)
    {
    	Customer c = null;
    	try {
        	con = getConnectionDef();
			ps = con.prepareStatement(customerEditQuery);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rs.next();
			
			int i = rs.getInt("id");
			String n = rs.getString("name");
			String em = rs.getString("email");
			long m = rs.getLong("mobile");
			
			 c = new Customer(i, n, em, m);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	finally {
    		if(rs != null)
    		{
    			try {
					rs.close();
				} 
    			catch (SQLException e)
    			{
					e.printStackTrace();
				}
    		}
    		if(ps != null)
    		{
    			try {
					ps.close();
				}
    			catch (SQLException e) 
    			{
					e.printStackTrace();
				}
    		}
    		if(con != null)
    		{
    			try {
					con.close();
				} 
    			catch (SQLException e)
    			{
					e.printStackTrace();
				}
    		}
    	}
		return c;
    }
    
    public static Connection getConnectionDef()
    {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dburl, dbusername, dbpassword);
		} 
    	catch (ClassNotFoundException e)
    	{
			e.printStackTrace();
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
		return con;
    }
    
    public static ArrayList<Customer> getAllCustomers()
    {
    	ArrayList<Customer> al = new ArrayList<Customer>();
    	try {
        	con = getConnectionDef();
			st = con.createStatement();
			rs = st.executeQuery(customerlistQuery);
			
			while(rs.next())
			{
				int i = rs.getInt("id");
				String n = rs.getString("name");
				String e = rs.getString("email");
				long m = rs.getLong("mobile");
				
				Customer c = new Customer(i, n, e, m);
				al.add(c);
		     }	
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
    	finally {
    		if(rs != null)
    		{
    			try {
					rs.close();
				} 
    			catch (SQLException e)
    			{
					e.printStackTrace();
				}
    		}
    		if(st != null)
    		{
    			try {
					st.close();
				}
    			catch (SQLException e) 
    			{
					e.printStackTrace();
				}
    		}
    		if(con != null)
    		{
    			try {
					con.close();
				} 
    			catch (SQLException e)
    			{
					e.printStackTrace();
				}
    		}
    	}
		return al;
    }
public static void insertUsers(Customer c)
{
	try {
		con = getConnectionDef();
		ps = con.prepareStatement(customerinsertQuery);
		ps.setString(1, c.getName());
		ps.setString(2, c.getEmail());
		ps.setLong(3, c.getMobile());
		ps.executeUpdate();
		
	} 
	catch (SQLException e)
	{
		e.printStackTrace();
	}
	finally {
		if(ps != null)
		{
			try {
				ps.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		if(con != null)
		{
			try {
				con.close();
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
}
}      
}
