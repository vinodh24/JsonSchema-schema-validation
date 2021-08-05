package com.vinodh.jsonvalidation.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.github.fge.msgsimple.source.MapMessageSource;
import com.github.fge.msgsimple.source.MessageSource;
import com.vinodh.jsonvalidation.constants.JsonSchemaConstant;

public class LoadingValidationErrorMessage {
	
	Logger log = LoggerFactory.getLogger(LoadingValidationErrorMessage.class);

	public static MessageSource source;

	public void init() {
		loadingValidationErrorMessage();
	}

	public void loadingValidationErrorMessage() {
		source= MapMessageSource.newBuilder().putAll(getProperties()).build();
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  Map<String, String> getProperties(){
		Properties getProperties = new Properties();
		FileReader inputStream = null;
		Map<String, String> propertyMap = new HashMap<String, String>();
		try {
			inputStream =  new FileReader(new ClassPathResource(JsonSchemaConstant.FOLDER_LOCATION+JsonSchemaConstant.VALIDATION_ERROR_MESSAGE_COFIGURATION).getFile());
			getProperties.load(inputStream);
			propertyMap.putAll((Map) getProperties);

		} catch (Exception e) {
			log.info("json Error Message processing exception :  "+e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				inputStream.close();
				getProperties=null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return propertyMap;
	}

}
