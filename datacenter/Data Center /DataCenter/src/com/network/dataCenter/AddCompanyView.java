package com.network.dataCenter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.*;

public class AddCompanyView extends JDialog implements ActionListener,ItemListener{

	//define view components
	JLabel companyIDLabel;
	JLabel nameLabel;
	JLabel locationLabel;
	JLabel departmentLabel;
	JLabel descriptionLabel;
	JLabel locImgLabel;
	JLabel webImgLabel;
	JPanel basicInfoPanel;
	
	
	JTextField idText;
	JTextField nameText;
	JComboBox locList;
	String selectedLoc;
	JComboBox depList;
	String selectedDep;
	//TextField depText;
	JTextField locImg;
	JTextField webImg;
	JTextArea decText;
	JPanel summaryPanel;
	
	
	
	JButton finishButton;
	JButton cancelButton;
	JPanel southPanel;
	
	JPanel allPanels;
	
	
	
	//owner: father view
	//title: window name
	//model: model-window or non-model-window
	public AddCompanyView (Frame owner, String title, boolean model)
	{
		super(owner,title,model);
		
		companyIDLabel=new JLabel("companyID",JLabel.CENTER);
		nameLabel=new JLabel("name",JLabel.CENTER);
		locationLabel=new JLabel("location",JLabel.CENTER);
		departmentLabel=new JLabel("department",JLabel.CENTER);
		
		locImgLabel=new JLabel("locImage_address",JLabel.CENTER);
		webImgLabel=new JLabel("webImage_address",JLabel.CENTER);
				
		idText=new JTextField();
		idText.setFont(new Font("Dialog",0,15));
		nameText=new JTextField();
		nameText.setFont(new Font("Dialog",0,15));
		
		String locNameList[]={"AZ","CA","CO","CT","DE","FL","GA","IL","IA","MD","MA","NJ","NY","NC","OR","PA","TN","TX","UT","WA","WI"};
		locList=new JComboBox(locNameList);
		String depNameList[]={"electronic","sport","food","software","computer","business","market"};
		depList=new JComboBox(depNameList);
		locImg=new JTextField("image/");
		locImg.setFont(new Font("Dialog",0,15));
		webImg=new JTextField();
		webImg.setFont(new Font("Dialog",0,15));
		
		basicInfoPanel=new JPanel(new GridLayout(3,4));
			
		basicInfoPanel.add(companyIDLabel);
		basicInfoPanel.add(idText);
		basicInfoPanel.add(nameLabel);
		basicInfoPanel.add(nameText);
		basicInfoPanel.add(locationLabel);
		basicInfoPanel.add(locList);
		basicInfoPanel.add(departmentLabel);		
		basicInfoPanel.add(depList);
		
		basicInfoPanel.add(locImgLabel);
		basicInfoPanel.add(locImg);
		basicInfoPanel.add(webImgLabel);
		basicInfoPanel.add(webImg);
		

		descriptionLabel=new JLabel("description",JLabel.CENTER);		
		decText=new JTextArea("please type summary here!",10,5);
		decText.setFont(new Font("Dialog",0,15));
		summaryPanel=new JPanel(new GridLayout(2,1));
		summaryPanel.add(descriptionLabel);
		summaryPanel.add(decText);
		
		

		
		
		finishButton=new JButton("finish");
		finishButton.addActionListener(this);
		cancelButton=new JButton("cancel");
		southPanel=new JPanel();
		
		southPanel.add(finishButton);
		southPanel.add(cancelButton);
		
		allPanels=new JPanel(new GridLayout(2,1));
		allPanels.add(basicInfoPanel);
		allPanels.add(summaryPanel);
		
		
		this.add(allPanels,"Center");
		this.add(southPanel,"South");
		this.setSize(550,300);
		this.setVisible(true);		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==finishButton)
		{
			//get selected Loc
			selectedLoc=(String) locList.getSelectedItem();
			//get selected Department
			selectedDep=(String)depList.getSelectedItem();
			System.out.println("click finish Button");
			//define components for sql server
			PreparedStatement ps=null;
			Connection ct=null;
			ResultSet rs=null;
			
			try{
				//set driver
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				ct=DriverManager.getConnection
						("jdbc:sqlserver://127.0.0.1:1433;databaseName=COManagerDatabase;integratedSecurity=true");
				ps=ct.prepareStatement("insert into company values(?,?,?,?,?,?,?)");
				System.out.println("begin get info from textField");
				ps.setInt(1,Integer.parseInt(idText.getText()) );
				ps.setString(2, nameText.getText());
				//put selected Location
				ps.setString(3, selectedLoc);
				//put selected department
				ps.setString(4, selectedDep);
				
				ps.setString(5, locImg.getText());
				ps.setString(6, webImg.getText());
				
				ps.setString(7, decText.getText());

				System.out.println("get item details successfully");
				//implement add new company item
				int flag=ps.executeUpdate();
				System.out.println("add new item successfully");
				if(flag!=0)
				{
					JOptionPane.showMessageDialog(this,"add new company successfully");
				}
				
				//close addCOview
				this.dispose();				
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
		}
	}



	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		System.out.println((String)locList.getSelectedItem());
	}
}
