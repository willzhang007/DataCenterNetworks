 /*
 * all the different operations about company are done in this class
 */

package com.network.dataCenter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.*;
public class COTableModel extends AbstractTableModel{
	
	public Vector rowData;
	public Vector columnName;
	//public String companySummary;
	
	//define components for sql server
	PreparedStatement ps=null;
	Connection ct=null;
	ResultSet rs=null;
	
	public void initTableModel(String sql)
	{
		if(sql.equals(""))
		{
			sql="select * from company";
		}
		//define column name
		columnName=new Vector();
		columnName.add("company ID");
		columnName.add("name");
		columnName.add("location");
		columnName.add("department");
		columnName.add("image address");
						
		//add rows
		rowData=new Vector();

		try{
			//set driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			ct=DriverManager.getConnection
					("jdbc:sqlserver://127.0.0.1:1433;databaseName=COManagerDatabase;integratedSecurity=true");
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
							
			while(rs.next()){
				Vector hang=new Vector();
				hang.add(rs.getInt(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getString(4));
				hang.add(rs.getString(5));
								
				rowData.add(hang);							
			}
			

							
							
		}catch(Exception e){
			e.printStackTrace();
		}finally{
							
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
				if(ct!=null)
					ct.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	
	//init tablemodel through sql sentence
	public COTableModel(String sql)
	{
		this.initTableModel(sql);
	}
	
	//init tablemodel
	public COTableModel()
	{
		this.initTableModel("");
	}
	
	public void addCompany(String sql)
	{
		
	}
	
	@Override 
	//get num of rows
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	
	@Override 
	//get num of columns
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnName.size();
	}

	
	@Override 
	//get value from certain row and column
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector)this.rowData.get(rowIndex)).get(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.columnName.get(column);
	}

}
