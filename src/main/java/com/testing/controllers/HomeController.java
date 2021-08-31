package com.testing.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HomeController {

	private String htmlData;

	@Autowired
	ResourceLoader resourceLoader;

	private static final int BUFFER_SIZE = 4096;

	@GetMapping
	public String home(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Resource resource = new ClassPathResource("/");
		String path = resource.getFile().getAbsolutePath();
		// System.out.println(path);

		Resource resource2 = resourceLoader.getResource("/WEB-INF/jsp/index.jsp");
		String absolutePath = resource2.getFile().getAbsolutePath();
		// System.out.println(absolutePath);
		System.out.println("Path Fetch Success and attach a file Name");

		System.out.println("*************************************************");

		try {

			byte[] copyToByteArray = FileCopyUtils.copyToByteArray(resource2.getInputStream());
			htmlData = new String(copyToByteArray, StandardCharsets.UTF_8);
			// System.out.println(htmlData);

			System.out.println("**********************************************");
			// Creating Map and Putting Value
			Map<String, String> map = new LinkedHashMap();

			map.put("name", "Rahul jaiswal");
			map.put("mobile", "9845100000");
			map.put("email", "rahul@gmail.com");
			map.put("city", "DELHI");

			// System.out.println(map);
			System.out.println("############################################");

			System.out.println(htmlData);
			for (String key : map.keySet()) {
				String value = map.get(key);

				System.out.println(value);

				value = value == null ? "null" : value;
				htmlData = htmlData.replace("{{" + key + "}}", value);

			}

			System.out.println("***********************RESULT************************");

			// System.out.println(htmlData);

			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@Downloading code..");

			response.setContentType("text/html");  
			 
			String filepath = "e:\\";   
			response.setContentType("APPLICATION/OCTET-STREAM");   
			response.setHeader("Content-Disposition","attachment; filename="+ "ok.html");   
			
			
			
			ServletOutputStream outputStream = response.getOutputStream();
			  outputStream.write(htmlData.getBytes());
//			StringReader sr = new StringReader(htmlData);
//			FileInputStream fileInputStream = new FileInputStream(htmlData);  
//			            
//			int i;   
//			while ((i=fileInputStream.read()) != -1) {  
//			fileInputStream.readNBytes(i);
//			}   
//			fileInputStream.close();   
//			fileInputStream.close();   
			  outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

		return "index";
	}

}
