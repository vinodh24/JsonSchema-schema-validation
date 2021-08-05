package com.vinodh.jsonvalidation.util;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapToPojo {
	
	public static <T> T convertMapToPojo(Map<String, String> object, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		T ob=mapper.convertValue(object, clazz);
		return ob;		
	}
	
	public static <T> Map<String, String> convertObjectToSingleMap(T object) {	
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, String> data = mapper.convertValue(object, new TypeReference<Map<String, String>>(){});	
		return data;		
	}
	
}
