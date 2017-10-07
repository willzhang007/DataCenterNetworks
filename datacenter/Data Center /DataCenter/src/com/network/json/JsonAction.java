package com.network.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JsonAction
 */
@WebServlet("/JsonAction")
public class JsonAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonAction()
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		JsonService jsonService=new JsonService();
		PrintWriter out=response.getWriter();
		String jsonString="";
		String action_flag=request.getParameter("action_flag");
        
		if(action_flag.equals("network2015"))
		{
			jsonString="login succeed";
		}
        else if(action_flag.equals("companys"))
        {
			 jsonString=JsonTools.createJsonString("companys", jsonService.listAllCompany());			
		}
        else
        {
			 jsonString=JsonTools.createJsonString("company", jsonService.getCompany(action_flag));
		}		
		
		out.println(jsonString);
		out.flush();
		out.close();
		
	}

}
