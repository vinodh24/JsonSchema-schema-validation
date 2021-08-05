package com.vinodh.jsonvalidation.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vinodh.jsonvalidation.service.SchemaService;

public class ServiceNowRefreshPortServiceTest {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("inputvalidator-context.xml");
		SchemaService schemaService = (SchemaService) context.getBean("schemaService");
		schemaService.refreshPortService();
	}
	
	public static String trimFirstWord(String s) {
		String example = "/abc/def/ghfj.doc";
		System.out.println(example.substring(example.lastIndexOf("/") + 1));
	    return s.contains(" ") ? s.substring(s.indexOf(' ')).trim() : "";
	}

}
