package subserver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.network.common.Company;

public class SubServer {
	public static BufferedImage image;
	static ServerSocket subServerSocket=null;
	static Socket subServer2DataCenter=null;
	Company company;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Company company=new Company();
		SubServer subServer=new SubServer(company);
		System.out.println(company.getCoId()+" "+company.getCoName());
		try {
			subServerSocket=new ServerSocket(8001);
			subServer2DataCenter=subServerSocket.accept();
			
			ObjectOutputStream obOutput=new ObjectOutputStream(subServer2DataCenter.getOutputStream());
			obOutput.writeObject(company);
			System.out.println("complete write company object steam");
			
			image=ImageIO.read(new File("E:\\2015 network material\\image\\netscape.png"));
			ImageIO.write(image, "png", subServer2DataCenter.getOutputStream());
			System.out.println("complete write image");
			
			obOutput.close();
			subServer2DataCenter.close();
			subServerSocket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SubServer(Company company)
	{
		this.company=company;
		company.setCoId(1012);
		company.setCoName("netscape");
		company.setLocation("CA");
		company.setDepartment("department");
		company.setLocImageAddress("image/netscape.png");
		company.setWebImageAddress("netscape.png");
		company.setSummary("I am netscape");
	}
	

}
