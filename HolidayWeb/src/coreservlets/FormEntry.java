package coreservlets;


import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/FormEntry")
public class FormEntry extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

	  String Response="";
String home = "LON";
	  String url = request.getParameter("url");
	  String startdate = request.getParameter("startdate");
	  String duration = request.getParameter("duration");
	  String variance = request.getParameter("variance");
	  String destination = request.getParameter("destination");
	  String checkhotel = request.getParameter("hotelcheck");
	  String checkflight = request.getParameter("flightcheck");
	
	  
	  if (checkhotel!=null)
	  {
	  String Hoteljson = Main.SingleHotelRequest(url,startdate,duration,variance);
	  	
	  String[] array = Hoteljson.split(";");
	
	  request.setAttribute("hotelname", array[0]); // This will be available as ${message}
	  request.setAttribute("hotelchart", array[1]); // This will be available as ${message}
	  request.setAttribute("hotelurlmap", array[2]);
	  }
	  if (checkflight !=null)
	  {
	  String OutboundFlightjson = Main.FlightRequest(startdate,duration,variance,home,destination);
	  String InboundFlightjson = Main.FlightRequest(startdate,duration,variance,destination, home);
	  request.setAttribute("outboundflightchart", OutboundFlightjson);
	  request.setAttribute("inboundflightchart", InboundFlightjson);
	  }
	  
	  
	  
	  request.getRequestDispatcher("/Results.jsp").forward(request, response);
	
	
/*
  
    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE></TITLE>" +
                "<meta http-equiv=\"refresh\" content=\"2; URL=/TradingWeb/Index.jsp\">" +
                "</HEAD>\n" +
                "<BODY>\n" +
                Response+"\n"+
                ". Auto redirect back in 2 seconds "+
                "</BODY></HTML>");
                */
	  
  }
  }