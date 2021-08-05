package com.vinodh.jsonvalidation.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vinodh.jsonvalidation.service.SchemaService;

public class ServiceNowPOrtIsTest {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("inputvalidator-context.xml");
		SchemaService schemaService = (SchemaService) context.getBean("schemaService");
		schemaService.portIsService();
	}

}
