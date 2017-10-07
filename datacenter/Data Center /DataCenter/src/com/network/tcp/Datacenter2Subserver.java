package com.network.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTable;

import com.network.common.Company;
import com.network.dataCenter.COTableModel;



public class Datacenter2Subserver extends Thread {

		
	public Datacenter2Subserver() {
		// TODO Auto-generated constructor stub
		
	}
	
	public void run()
	{
		obtainMsgFromSubserver();
	}


	
	public boolean obtainMsgFromSubserver()
	{
		boolean obtainMsg_flag=false;
		
		try {
			System.out.println("now DataCenter is waiting connection from subservers");
			ServerSocket Serversocket = new ServerSocket(9101);
			Socket Server = Serversocket.accept();
            
			ObjectInputStream recevie = new ObjectInputStream(Server.getInputStream());
			Company subCompany = (Company)recevie.readObject();
			System.out.println(subCompany.toString());
			System.out.println("message update");
            
			int onlineNum=1;
			subCompany.setOnlinePL(String.valueOf(onlineNum));
			System.out.println(subCompany.onlinePL);
			boolean insertFlag=insertSubServer2DB(subCompany);
			System.out.println("insertFlag>>>"+insertFlag);
			
			
			DataInputStream receviee = new DataInputStream(Server.getInputStream());
			while(true){
				onlineNum++;
				subCompany.setOnlinePL(String.valueOf(onlineNum));
				String stringfromsubserver = receviee.readUTF();
				System.out.println("message update");
				System.out.println(stringfromsubserver);
				System.out.println(subCompany.toString());
				boolean updateFlag=updateSubServer2DB(subCompany.getCoName(),subCompany.getOnlinePL());
				System.out.println("updateFlag>>>"+updateFlag);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
				
		return obtainMsg_flag;
	}
	
	public static boolean insertSubServer2DB(Company company)
	{
		PreparedStatement ps=null;
		Connection ct=null;
		ResultSet rs=null;
		boolean insert_flag=false;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			ct=DriverManager.getConnection
					("jdbc:sqlserver://127.0.0.1:1433;databaseName=COManagerDatabase;integratedSecurity=true");
		    ps=ct.prepareStatement("insert into company values(?,?,?,?,?,?,?,?)");
			System.out.println("insert subserver to Database");
			ps.setInt(1,company.getCoId() );
			ps.setString(2, company.getCoName());
			//put selected Location
			ps.setString(3,company.getLocation());
			//put selected department
			ps.setString(4, company.getDepartment());		
			ps.setString(5, company.getLocImageAddress());
			ps.setString(6, company.getWebImageAddress());			
			ps.setString(7, company.getSummary());
			ps.setString(8, company.getOnlinePL());
			
			
			ps.executeUpdate();
			insert_flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				return insert_flag;
				
	}
	
	public static boolean updateSubServer2DB(String coName, String onlinePerson)
	{
		PreparedStatement ps=null;
		Connection ct=null;
		ResultSet rs=null;
		boolean update_flag=false;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			ct=DriverManager.getConnection
					("jdbc:sqlserver://127.0.0.1:1433;databaseName=COManagerDatabase;integratedSecurity=true");
		    ps=ct.prepareStatement("update company set onlinePL='"+onlinePerson+"'where coName='"+coName+"'");
			System.out.println("insert subserver to Database");
	
			ps.executeUpdate();
			update_flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				return update_flag;
	}
	
	

}
