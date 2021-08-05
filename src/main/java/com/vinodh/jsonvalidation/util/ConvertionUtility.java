package com.vinodh.jsonvalidation.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

import com.vinodh.jsonvalidation.constants.JsonSchemaConstant;

public class ConvertionUtility {

	public static String inputPutFileToString(String fileName)throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource(JsonSchemaConstant.FOLDER_LOCATION+fileName+JsonSchemaConstant.JSON_EXTENSION).getFile()));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		line = null;
		return sb.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, String> getSchemErrorProperties(){
		Properties getProperties = new Properties();
		FileReader inputStream = null;
		HashMap<String, String> propertyMap = new HashMap<String, String>();
		try {
			inputStream =  new FileReader(new ClassPathResource(
					JsonSchemaConstant.FOLDER_LOCATION+JsonSchemaConstant.VALIDATION_ERROR_MESSAGE_COFIGURATION).getFile());
			getProperties.load(inputStream);
			propertyMap.putAll((Map) getProperties);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertyMap;
	}
}
