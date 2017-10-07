package com.network.json;


import com.network.common.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/*
 * get object type
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.sql.*;
public class JsonService {
	
	PreparedStatement ps=null;
	Connection ct=null;
	ResultSet rs=null;

	public JsonService() {
		
	}
	
	//get all companies info
	public List<Company> listAllCompany()
	{
		List<Company> list=new ArrayList<Company>();
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			ct=DriverManager.getConnection
					("jdbc:sqlserver://127.0.0.1:1433;databaseName=COManagerDatabase;integratedSecurity=true");
			ps=ct.prepareStatement("select * from company");
			rs=ps.executeQuery();
							
			while(rs.next())
            {
				Company company=new Company();
				company.setCoId(rs.getInt(1));
				company.setCoName(rs.getString(2));
				company.setLocation(rs.getString(3));
				company.setDepartment(rs.getString(4));
				company.setWebImageAddress(rs.getString(6));
				company.setSummary(rs.getString(7));
				company.setOnlinePL(rs.getString(8));
				list.add(company);
			}
										
		}catch(Exception e1){
			e1.printStackTrace();
		}finally{
							
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
				if(ct!=null)
					ct.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}			
		return list;
	}
		

	
	//send this company info to client (Activity 3)
	public Company getCompany(String coName)
	{
		Company company=new Company();
		try{
			//set driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			ct=DriverManager.getConnection
					("jdbc:sqlserver://127.0.0.1:1433;databaseName=COManagerDatabase;integratedSecurity=true");
			ps=ct.prepareStatement("select * from company where coName='"+coName+"'");
			rs=ps.executeQuery();
							
			while(rs.next())
            {
				company.setCoId(rs.getInt(1));
				company.setCoName(rs.getString(2));
				company.setLocation(rs.getString(3));
				company.setDepartment(rs.getString(4));
				company.setWebImageAddress(rs.getString(6));
				company.setSummary(rs.getString(7));
				company.setOnlinePL(rs.getString(8));
			}
										
		}catch(Exception e1){
			e1.printStackTrace();
		}finally{
							
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
				if(ct!=null)
					ct.close();
			}
            catch (Exception e1)
            {
				e1.printStackTrace();
			}
		}			
			
		return company;
	}
	
	
	
	
	
	
	
	
}
