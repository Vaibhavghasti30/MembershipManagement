package com.techpalle.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techpalle.dao.Admin;
import com.techpalle.dao.CustomerDao;
import com.techpalle.model.Customer;


@WebServlet("/")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 String path = request.getServletPath();
		 switch(path)
		 {
		 case "/admin" :
			 getAdminPage(request,response);
			 break;
			 
		 case "/login":
			 getCustomerListPage(request, response);
			 break;
			 
		 case "/delete":
			 deleteCustomer(request,response);
			 break;
			 
		 case "/edit":
			 updateCustomer(request,response);
			 break;
		
		 case "/editForm":
			getEditForm(request,response); 
			 break;
			 
		 case "/add":
			 addCustomer(request,response);
			 break;
			 
		 case "/save":
			 savingCustomerData(request,response);
			 break;
		 
		   default :
			   getStartUpPage(request, response);
			   break;
		 }
	}

	
	private void getAdminPage(HttpServletRequest request, HttpServletResponse response)
	{
		 try {
	        	ArrayList<Customer> alcustomer =  CustomerDao.getAllCustomers();
	             RequestDispatcher rd = request.getRequestDispatcher("customer.list.jsp");
	             request.setAttribute("al", alcustomer);
				rd.forward(request, response);
			} 
	         catch (ServletException e)
	         {
				e.printStackTrace();
			} 
	         catch (IOException e)
	         {
				e.printStackTrace();
			}
	}


	private void getCustomerListPage(HttpServletRequest request, HttpServletResponse response) 
	{
      String u = request.getParameter("tbusername");
      String p = request.getParameter("tbpassword");
            
      Admin a = new Admin(u, p);
      boolean res = CustomerDao.validateAdmin(a);
      
      if(res)
      {
 		getAdminPage(request,response);
      }
      else
      {
    	  getStartUpPage(request,response);
      }
	}

	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) 
	{
		//Read the id from url
        int i = Integer.parseInt(request.getParameter("id"));
             
        //call the dao method to delete the row in database
        CustomerDao.deleteCustomer(i);
       
        //Redirect to customer.list page
        getAdminPage(request,response);

	}


	private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
	{	
		//Read the data from url
       int i =  Integer.parseInt(request.getParameter("tbid"));
       String n = request.getParameter("tbname");
       String em = request.getParameter("tbemail");
       long m = Long.parseLong(request.getParameter("tbmobile"));
       
       //storing data in object variable
       Customer c = new Customer(i, n, em, m);
       
       //calling the dao method to update the row in database
       CustomerDao.updateCustomer(c);
       
       //Redirect to the customer-list page
       getAdminPage(request,response);
	}


	private void getEditForm(HttpServletRequest request, HttpServletResponse response)
	{
       //Fetching the Id from url:
		int i = Integer.parseInt(request.getParameter("id"));
		
		Customer c = CustomerDao.getOneCustomer(i);
	   
				try {
					RequestDispatcher rd = request.getRequestDispatcher("customer.form.jsp");
		            request.setAttribute("customer", c);
					rd.forward(request, response);
				} 
				catch (ServletException e) 
				{
					e.printStackTrace();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}	
	}


	private void savingCustomerData(HttpServletRequest request, HttpServletResponse response)
	{
		    String n = request.getParameter("tbname");
	        String em = request.getParameter("tbemail");
	        long m = Long.parseLong(request.getParameter("tbmobile"));

	        Customer c = new Customer(n, em, m);
	        
	        //Inserting Customer
	        CustomerDao.insertUsers(c);
	        
	        //Redirect to admin Page   
			getAdminPage(request,response);             		
	}


	private void addCustomer(HttpServletRequest request, HttpServletResponse response) 
	{
        try {
            RequestDispatcher rd = request.getRequestDispatcher("customer.form.jsp");
			rd.forward(request, response);
		} 
        catch (ServletException e)
        {
			e.printStackTrace();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}   
	}

	private void getStartUpPage(HttpServletRequest request, HttpServletResponse response)
	{
		
         try {
             RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		    	rd.forward(request, response);
		} 
         catch (ServletException e)
         {
			e.printStackTrace();
		} 
         catch (IOException e)
         {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
