package com.pm.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pm.bll.beans.UserBean;
import com.pm.bll.interfaces.IUserBaseOperation;
import com.pm.pojo.UserBase;



public class UserServlet extends HttpServlet{

    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter(); 
		out.write("hehe");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			   throws ServletException, IOException { 
		
		

		
		Map<String,Object> ret=new HashMap<String,Object>();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		System.out.println("INTO POST");
		try{
		
			String url =request.getRequestURL().toString();
			String MethodName = url.substring(url.lastIndexOf("/")+1);//get method name 
		 
			int len = request.getContentLength(); 
			System.out.println("got request len is "+ String.valueOf(len));
			InputStream is = request.getInputStream(); 

			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8")); 
			String buffer = null; 
			StringBuffer requestBody = new StringBuffer(); 
			while ((buffer = br.readLine()) != null) { 
				requestBody.append(buffer);
			}      
	    
			IUserBaseOperation operation =new UserBean();
			if(MethodName.equalsIgnoreCase("adduser"))
			{
				System.out.println("in the method ["+MethodName+"]");
				ret.put("MethodName", MethodName);
				UserBase user=gson.fromJson(requestBody.toString(), UserBase.class);
				user = operation.Save(user);
				ret.put("retVal", user);
				ret.put("status",Long.valueOf(0));
			 
			
			 
			}
			else if(MethodName.equalsIgnoreCase("existsuser"))
			{
				ret.put("MethodName", MethodName);
				String phone=gson.fromJson(requestBody.toString(), String.class);
				UserBase user=operation.GetUserByPhone(phone);
			
				ret.put("retVal", user);
				ret.put("status",Long.valueOf(0));
			 
			 
			}
		

		} catch(Exception e){
		
			
			ret.put("status",Long.valueOf(-1));
			ret.put("errormsg", e.getMessage());
			System.out.println("error is "+ e.getMessage());
		}
	
		PrintWriter out = response.getWriter(); 
		String retstr=gson.toJson(ret);
		out.write(retstr); 
		out.flush(); 
		out.close(); 
		System.out.println("send fin ");
		
}
	
}
