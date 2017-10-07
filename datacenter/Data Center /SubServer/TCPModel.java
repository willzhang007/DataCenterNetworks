package com.network.tcp;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import com.network.common.*;
public class TCPModel extends Thread{

	public static Socket socket2SubServer;
	public static BufferedImage image;
	public TCPModel() {
		
	}
	
	public void run()
	{
		try {
			System.out.println("go in to the run function");
			socket2SubServer=new Socket("127.0.0.1",8001);
			ObjectInputStream obInput=new ObjectInputStream(socket2SubServer.getInputStream());
			System.out.println("has got stream from subserver");
			com.network.common.Company company=(Company)obInput.readObject();
			System.out.println(company.getCoId()+" "+company.getCoName());
			
			image=ImageIO.read(socket2SubServer.getInputStream());
			File file=new File("image\\netscape.png");
			file.createNewFile();
			ImageIO.write(image, "png", file);
			System.out.println("complete read image");
			obInput.close();
			socket2SubServer.close();
			System.out.println("has close dataCenter stream");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TCPModel dataCenter=new TCPModel();
		dataCenter.start();
		System.out.println("start");
	}

}
