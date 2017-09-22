package com.ibm.pear.controller;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.uima.simpleserver.Service;
import org.apache.uima.simpleserver.output.Result;
import org.apache.uima.simpleserver.output.ResultConverter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PearController {
	
	//@Autowired
	public Service server;
	public File baseWebappDirectory = null;
	private Logger logger = Logger.getAnonymousLogger();
	@Autowired
	ServletContext context; 

	public void setServer(Service server) {
		this.server = server;
	}
	
	protected Logger getLogger() {
	    return this.logger;
	  }
	
	/*@PostConstruct
	public void init() {
	    System.out.println("Starting UIMA servlet initialization");
	    	    
	    System.out.println("Servlet " + this.getClass().getCanonicalName()
	            + " -- initialisation begins:"+ context.getRealPath(""));
	    this.baseWebappDirectory = new File(context.getRealPath(""));
	    //this.baseWebappDirectory = new File("C:/workspace-sts-3.7.2.RELEASE/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/SimpleServerTest");
	    //this.baseWebappDirectory = new File("/home/vcap/app/wlp/usr/servers/defaultServer/apps/myapp.war");
	    
	    this.server = new Service();
	    boolean initSuccess = initServer();
	    //declareServletParameters();
	  }*/
	
	// If UIMA-AS is included as an ADD-ON to SimpleServer and UIMA-AS servlet 
	// is invoked, UIMA-AS servlet will override this method
		protected boolean initServer() {
		    File resultSpec = null;
		    String resultSpecParamValue = null;//getInitParameter("ResultSpecFile");
		    String pearPath = "WEB-INF/resources/IdentifyComplaintAsk_v3.pear"; //getInitParameter("PearPath");
		    String descriptorPath = null; //getInitParameter("DescriptorPath");
		    String pearInstallPath = null; //getInitParameter("PearInstallPath");

		    try {
		      if (descriptorPath == null) {
		        File pear = new File(this.baseWebappDirectory.getAbsoluteFile(), pearPath);
		        // get default servlet working directory
		        File pearInstallDir = (File) context.getAttribute("javax.servlet.context.tempdir");
		        getLogger().log(Level.INFO, "Install PEAR file to: " + pearInstallDir.getAbsolutePath());
		        this.server.configurePear(pear, pearInstallDir, resultSpec);
		      }
		    } catch (Exception e) {
		      getLogger().log(Level.SEVERE, "UIMA Simple Service configuaration failed", e);
		      return false;
		    }
		    return true;
		  }

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		
		model.put("message", this.message);
		return "index";
		
	}
	
	@RequestMapping("/form")
	public String Process(Map<String, Object> model,@RequestParam String text, @RequestParam String lang, @RequestParam String mode) {
		System.out.println("Inside Controller!!!!!!!!!!!!!!!!!!!");
		

	    System.out.println(this.getClass().getName() + ": POST request received: " + new Date());
	    System.out.println("Given text: " + text.substring(0, Math.min(50, text.length())));
	    if ((lang == null) || lang.equals("")) {
	      lang = "en";
	    }
	    System.out.println("MODE_PARAMETER: " + mode);
	    System.out.println("lang: " + lang);
	    
	    // process the text
	    Result result = this.server.process(text, lang);
	    
	    System.out.println(result);
	    if ("xml".equals(mode)) {
	    	message = ResultConverter.getXMLString(result);
	    } else
	    if ("inline".equals(mode)) {
	    	message = ResultConverter.getInlineXML(result);
	    } else
	    if ("text".equals(mode)) {
	    	message = ResultConverter.getInlineXML(result);
	    } else {
	    	message = ResultConverter.getXMLString(result);
	    }
	    
	    
	    
	   // message.replaceAll("<Question", "<div class='starter-template'>");
	   // message.replaceAll("</Question>", "</div>");
	    
	    //String soapmessageString = "<xml>yourStringURLorFILE</xml>";
	    JSONObject soapDatainJsonObject = null;
		try {
			soapDatainJsonObject = XML.toJSONObject(message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("JSON :::");
	    System.out.println(soapDatainJsonObject);
	    System.out.println(message);
		
		model.put("message", this.message);
		return "index";
	}
	
		  
		

}
