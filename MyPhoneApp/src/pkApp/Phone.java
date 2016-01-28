package pkApp;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Phone extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		process(req,resp);	
	}
	
	private void process(HttpServletRequest req,HttpServletResponse resp)
	{
		String sPhoneNum=(String)req.getParameter("num");
		try
		{	
			if(sPhoneNum==null)	
			{
				resp.getWriter().println("Welcome to Pratima Phone app please provide phone number");
			}
			else
			{
			
			String sPageNum=(String)req.getParameter("page");
			if(sPageNum==null)
			{
				sPageNum=new String("1");
			}
			//resp.getWriter().println("Welcome to Pratima Phone app");

			PhoneCombGen phoneComb= new PhoneCombGen();
			Vector<String> list=phoneComb.generate(sPhoneNum);
			genHtml(req,resp,list,sPageNum);
			}
		}
		catch(Exception e)
		{

		}
	}
	
	private void genHtml(HttpServletRequest req,HttpServletResponse resp,Vector<String> list,String sPageNum)
	{
		String sPhoneNum=(String)req.getParameter("num");
		int nPage=0;
		String sPageToken="";
		try
		{
			String[] sPageTokens=sPageNum.split("-");
			if(sPageTokens.length==2)
			{
				nPage=Integer.parseInt(sPageTokens[0]);
				sPageToken=sPageTokens[1];
			}
			else
			nPage=Integer.parseInt(sPageNum);
		}
		catch(Exception e)
		{
			nPage=0;
		}
		
		//Each page contains 20 data
		int nCount=20;
		StringBuilder sb=new StringBuilder();
		
		
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>");
		sb.append(" <script> "
				+ "$(document).ready(function(){ $(\"button\").click(function(){ "
				+ "var phoneNum="+sPhoneNum+";" 
		    	+ "var pageNum=$(this).val();" 
		    	+ "var url=\"application_phone?num=\" + phoneNum+\"&page=\"+pageNum;"
		        + "$('#result').load(url);});});");		
		sb.append("</script></head>");
		sb.append("<body>");
		
		int nStart=(nPage-1)*nCount;
		if(nStart==0) nStart=1;
		
		int nComb=list.size()-1;
		
		sb.append("<div id=\"result\">");		
		sb.append("Total number of combinations: "+ nComb+"<br>");	
		
		sb.append("<table>");
		
		int nCountTracker=0;
		int idx=0;
		for(idx=nStart;idx<list.size();idx++)
		{
	        if(nCountTracker>=nCount) break;
			String s=list.elementAt(idx);
			sb.append("<tr>");
			sb.append("<td>");			
			sb.append(s);			
			sb.append("</td>");			
			sb.append("</tr>");	
			nCountTracker++;
		}	
		
		
		
		//Add Pagination Logic
		float fTotal=list.size();
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("&nbsp;&nbsp");
		sb.append("</td>");
		sb.append("</tr>");
		fTotal/=nCount;
		//sb.append(" total: "+fTotal);
		int nTotalPage=(int)fTotal;	
		//sb.append(" total: "+nTotalPage);
		if(fTotal>nTotalPage)nTotalPage++;
		
		
		sb.append("<tr>");
		sb.append("<td>");
		sb.append(" Page: ");
		int nMaxPage=9; //total page displayed as number
		int pageStart=1;
		int pageEnd=nMaxPage;
		//if(sPageToken.equals("N"))
		{
			pageStart=nPage;
			pageEnd=pageStart+nMaxPage;	
			if(pageEnd>=nTotalPage)
			{
				pageStart=nTotalPage-nMaxPage;
				if(pageStart<1) pageStart=1;
				pageEnd=nTotalPage;				
			}
		}
		
		/*if(sPageToken.equals("P"))
		{
			pageEnd=nPage;
			pageStart=pageEnd-nMaxPage;	
			if(pageStart<=1) 
			{
				pageStart=1;
				pageEnd=pageStart+nMaxPage;
			}
		}*/
		
		if(pageStart!=1) //Need to display Previous
		{
			int pStart=nPage-1;
			sb.append("<button value="+pStart+"-P>previous</button>");			
		}
		
		for(int i=pageStart;i<=nTotalPage;i++)
		{
			if(i>pageEnd)
			{   
				//int pStart=nPage+1;
				//sb.append("<button value="+pStart+"-N>next</button>");
				break;
			}
			
			if(i==nPage)
			{
				sb.append("<button value="+i+"><b>"+i+"</b></button>");
			}
			else		
			sb.append("<button value="+i+">"+i+"</button>");			
		}
		
		if(nPage<nTotalPage)
		{   
			int pStart=nPage+1;
			sb.append("<button value="+pStart+"-N>next</button>");			
		}
		sb.append("</td>");		
		sb.append("</tr>");	
		sb.append("</table>");	
		sb.append("</div>");
		sb.append("</body>");		
		sb.append("</html>");
		try
		{		
			resp.getWriter().println(sb.toString());
		}
		catch(Exception e)
		{
		
		}
		
	}








}
