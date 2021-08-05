package com.vinodh.jsonvalidation.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vinodh.jsonvalidation.service.SchemaService;

public class ServiceNowAddServiceTestForVendor {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("inputvalidator-context.xml");
		SchemaService schemaService = (SchemaService) context.getBean("schemaService");
		schemaService.addServiceSchemaValidation();
	}
	
	public static String trimFirstWord(String s) {
	    return s.contains(" ") ? s.substring(s.indexOf(' ')).trim() : "";
	}

}
