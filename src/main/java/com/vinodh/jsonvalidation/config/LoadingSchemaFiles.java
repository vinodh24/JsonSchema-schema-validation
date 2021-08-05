package com.vinodh.jsonvalidation.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.vinodh.jsonvalidation.constants.JsonSchemaConstant;

public class LoadingSchemaFiles {

	Logger log = LoggerFactory.getLogger(LoadingSchemaFiles.class);

	private List<String> jsonSchemaValidationFiles;

	public final static Map<String,JsonNode> jsonSchemaList=new HashMap<String, JsonNode>();

	public void init() {
		loadingSchemaData();
	}

	public void loadingSchemaData() {
		log.info("loadingSchemaData All files list ::: "+jsonSchemaValidationFiles);
		for (String fileName : jsonSchemaValidationFiles) {
			try {
				jsonSchemaList.put(fileName, loadResource(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("jsonSchema data  ::: "+jsonSchemaList);
	}

	public static JsonNode loadResource(String name) throws IOException {
		return JsonLoader.fromResource(JsonSchemaConstant.FOLDER_LOCATION + name+JsonSchemaConstant.JSON_EXTENSION);
	}
	
	public static JsonNode getJsonSchemBasedOnFileName(String fileName) {
		for (Entry<String, JsonNode> entry : jsonSchemaList.entrySet()) {
			if(fileName.contains(entry.getKey())){
				return entry.getValue();
			}
		}
		return null;
		
	}

	public List<String> getJsonSchemaValidationFiles() {
		return jsonSchemaValidationFiles;
	}

	public void setJsonSchemaValidationFiles(List<String> jsonSchemaValidationFiles) {
		this.jsonSchemaValidationFiles = jsonSchemaValidationFiles;
	}

}
