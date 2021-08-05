package com.vinodh.jsonvalidation.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.messages.JsonSchemaValidationBundle;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.vinodh.jsonvalidation.config.LoadingSchemaFiles;
import com.vinodh.jsonvalidation.config.LoadingValidationErrorMessage;
import com.vinodh.jsonvalidation.constants.JsonSchemaConstant;
import com.vinodh.jsonvalidation.error.model.JsonErrorModel;
import com.vinodh.jsonvalidation.model.JsonValidatorModel;

public class JsonSchemaValidationUtility {

	private static Logger log = LoggerFactory.getLogger(JsonSchemaValidationUtility.class);
	
	private static List<String> assignedTechList = new ArrayList<String>(Arrays.asList("GPON","BPON","ADSL","BOND2+","VDSL","VBOND2+","AVDSL","AVBOND2+"));

	public static JsonValidatorModel addServiceValidation(String jsonString) throws IOException {
		return InputValidator(jsonString, JsonSchemaConstant.GENERAL_GROUP_SCHEMA);
	}

	public static JsonValidatorModel refreshServiceSchemaValidation(String jsonString) throws IOException {
		ProcessingReport processingReport=jsonValidation(jsonString, JsonSchemaConstant.REFRESHPORTSERVICE_SCHEMA_FILE);
		JsonValidatorModel jsonValidatorModel=new JsonValidatorModel();
		jsonValidatorModel.setSuccess(true);
		JSONObject jsonObject=new JSONObject(jsonString);
		try {
			String ontId=jsonObject.getJSONObject("pon").getString("ontId");
			if(ontId!=null && !ontId.isEmpty()) {
				System.out.println("failure");
				String ontPort=jsonObject.getJSONObject("pon").getString("ontPort");
				if(ontPort==null||ontPort.isEmpty()) {
					jsonValidatorModel.setNullKeys("pon.ontPort");
					jsonValidatorModel.setSuccess(false);
				}
			}
		}catch (Exception e) {
			log.error(" Exception occured in commonServiceSchemaValidation ",e);
		}
		if (!processingReport.isSuccess()) {
			jsonValidatorModel.setProcessingReport(processingReport);
			jsonValidatorModel.setSuccess(false);
		}
		return jsonValidatorModel;
	}

	public static JsonValidatorModel commonServiceSchemaValidation(String jsonString) throws IOException {
		ProcessingReport processingReport=jsonValidation(jsonString, JsonSchemaConstant.COMMON_SERVICE_SCHEMA_VALIDATION_FILE);
		JsonValidatorModel jsonValidatorModel=new JsonValidatorModel();
		jsonValidatorModel.setSuccess(true);
		JSONObject jsonObject=new JSONObject(jsonString);
		System.out.println(processingReport);
		try {
			String ontId=jsonObject.getJSONObject("pon").getString("ontId");
			if(ontId!=null) {
				String ontPort=jsonObject.getJSONObject("pon").getString("ontPort");
				if(ontPort==null||ontPort.isEmpty()) {
					jsonValidatorModel.setNullKeys("pon.ontPort");
					jsonValidatorModel.setSuccess(false);
				}
			}
		}catch (Exception e) {
			log.error(" Exception occured in commonServiceSchemaValidation ",e);
		}
		if (!processingReport.isSuccess()) {
			jsonValidatorModel.setProcessingReport(processingReport);
			jsonValidatorModel.setSuccess(false);
		}
		return jsonValidatorModel;
	}

	public static ProcessingReport scpSchemaValidation(String jsonString) throws IOException {
		ProcessingReport processingReport=jsonValidation(jsonString, JsonSchemaConstant.SCP_SCHEMA_FILE);
		return processingReport;
	}

	public static JsonValidatorModel ripAndRebuildSchemaValidation(String jsonString) throws IOException {
		ProcessingReport processingReport=jsonValidation(jsonString, JsonSchemaConstant.RIPANDREBUILD_SCHEMA_FILE);
		JsonValidatorModel jsonValidatorModel=new JsonValidatorModel();
		jsonValidatorModel.setSuccess(true);
		JSONObject jsonObject=new JSONObject(jsonString);
		try {
			String ontId=jsonObject.getJSONObject("pon").getString("ontId");
			if(ontId!=null) {
				String ontPort=jsonObject.getJSONObject("pon").getString("ontPort");
				if(ontPort==null||ontPort.isEmpty()) {
					jsonValidatorModel.setNullKeys("pon.ontPort");
					jsonValidatorModel.setSuccess(false);
				}
			}
		}catch (Exception e) {
			log.error(" Exception occured in ripAndRebuildSchemaValidation ",e);
		}
		if (!processingReport.isSuccess()) {
			jsonValidatorModel.setProcessingReport(processingReport);
			jsonValidatorModel.setSuccess(false);
		}
		return jsonValidatorModel;
	}

	public static JsonValidatorModel refreshPonSchemaValidation(String jsonString) throws IOException {
		JsonValidatorModel jsonValidatorModel=new JsonValidatorModel();
		jsonValidatorModel.setSuccess(true);
		ProcessingReport processingReport=jsonValidation(jsonString, JsonSchemaConstant.REFRESHPON_SCHEMA_FILE);
		if(!processingReport.isSuccess()) {
			jsonValidatorModel.setProcessingReport(processingReport);
			jsonValidatorModel.setSuccess(false);
		}
		return jsonValidatorModel;
	}

	public static JsonValidatorModel refreshOntSchemaValidation(String jsonString) throws IOException {
		JsonValidatorModel jsonValidatorModel=new JsonValidatorModel();
		jsonValidatorModel.setSuccess(true);
		ProcessingReport processingReport=jsonValidation(jsonString, JsonSchemaConstant.REFRESHONT_SCHEMA_FILE);
		if(!processingReport.isSuccess()) {
			jsonValidatorModel.setProcessingReport(processingReport);
			jsonValidatorModel.setSuccess(false);
		}
		return jsonValidatorModel;
	}

	public static ProcessingReport modifyServiceSchemaValidation(String jsonString) throws IOException {
		ProcessingReport processingReport=jsonValidation(jsonString, JsonSchemaConstant.MODIFYSERVICE_SCHEMA_FILE);
		return processingReport;
	}

	/*public static JsonValidatorModel InputValidator(String jsonString, String fileName) throws IOException {
		JSONObject jsonObject=new JSONObject(jsonString);
		JsonValidatorModel jsonValidatorModel=new JsonValidatorModel();
		ProcessingReport processingReport = jsonValidation(jsonString, fileName);
		if (processingReport.isSuccess()) {
			List<ProcessingReport> processingReportList =basedOnVendorValidation(jsonString,jsonObject,jsonValidatorModel);
			if(jsonValidatorModel.isSuccess()) {
				//need to add logic
			}else {
				jsonValidatorModel.setProcessingReportList(processingReportList);
			}
		}else {
			if(jsonObject.has("telephoneNumber")) {
				String telephoneNumber=jsonObject.getString("telephoneNumber");
				if(telephoneNumber==null || telephoneNumber.isEmpty()) {
					jsonValidatorModel.setNullKeys("telephoneNumber");
				}
			}
			
			List<ProcessingReport> processingReportList =basedOnVendorValidation(jsonString,jsonObject,jsonValidatorModel);
			processingReportList.add(processingReport);
			jsonValidatorModel.setSuccess(false);
			jsonValidatorModel.setProcessingReportList(processingReportList);
		}
		jsonObject=null;
		return jsonValidatorModel;	
	}*/
	
	public static JsonValidatorModel InputValidator(String jsonString, String fileName) throws IOException {
		JSONObject jsonObject=new JSONObject(jsonString);
		JsonValidatorModel jsonValidatorModel=new JsonValidatorModel();
		ProcessingReport processingReport = jsonValidation(jsonString, fileName);
		if (processingReport.isSuccess()) {
			List<ProcessingReport> processingReportList =basedOnVendorValidation(jsonString,jsonObject,jsonValidatorModel);
			if(jsonValidatorModel.isSuccess()) {
				String assignedTech=jsonObject.getString("assignedTech");
				if(!assignedTechList.contains(assignedTech.toUpperCase())) {
					throw new IllegalArgumentException(
							"NOCVue Unity does not support assignedTech "+assignedTech+"");
				}
				if (assignedTech.equalsIgnoreCase("gpon") || assignedTech.equalsIgnoreCase("bpon")) {
					boolean isData = false;
					if (jsonObject.has("data")) {
						if (jsonObject.getJSONObject("data").has("isData")) {
							isData = (boolean) jsonObject.getJSONObject("data").get("isData");
						}
					}

					if (!isData) {
						boolean isVideo = false;
						if (jsonObject.has("video") && jsonObject.getJSONObject("video").has("isVideo")) {
							isVideo = (boolean) jsonObject.getJSONObject("video").get("isVideo");
							if (isVideo) {
								boolean isVoice = false;
								if (jsonObject.has("voice") && jsonObject.getJSONObject("voice").has("isVoice")) {
									isVoice = (boolean) jsonObject.getJSONObject("voice").get("isVoice");
								}

								if (isVideo && isVoice) {
									throw new IllegalArgumentException(
											"NOCVue Unity does not support video with voice service on GPON/BPON assignedTech");
								} else {
									throw new IllegalArgumentException(
											"NOCVue Unity does not support video only service on GPON/BPON assignedTech");
								}
							}
						}
					}	
				} else {
					boolean isData = false;
					if (jsonObject.has("data")) {
						if (jsonObject.getJSONObject("data").has("isData")) {
							isData = (boolean) jsonObject.getJSONObject("data").get("isData");
						}
					}

					if (!isData) {
						boolean isVideo = false;
						boolean isVoice = false;
						if (jsonObject.has("video") && jsonObject.getJSONObject("video").has("isVideo")) {
							isVideo = (boolean) jsonObject.getJSONObject("video").get("isVideo");
							if (isVideo) {
								if (jsonObject.has("voice") && jsonObject.getJSONObject("voice").has("isVoice")) {
									isVoice = (boolean) jsonObject.getJSONObject("voice").get("isVoice");
								}
								if (isVideo && isVoice) {
									throw new IllegalArgumentException(
											"NOCVue Unity does not support video with voice service on "+assignedTech+" assignedTech");
								} else {
									throw new IllegalArgumentException(
											"NOCVue Unity does not support video only service on "+assignedTech+" assignedTech");
								}
							}
						}
						if (jsonObject.has("voice") && jsonObject.getJSONObject("voice").has("isVoice")) {
							isVoice = (boolean) jsonObject.getJSONObject("voice").get("isVoice");
							if (isVoice) {
								throw new IllegalArgumentException(
										"NOCVue Unity does not support voice service on "+assignedTech+" assignedTech");
							}
						}
					} else {
						boolean isVoice = false;
						if (jsonObject.has("voice") && jsonObject.getJSONObject("voice").has("isVoice")) {
							isVoice = (boolean) jsonObject.getJSONObject("voice").get("isVoice");
							if (isVoice) {
								throw new IllegalArgumentException(
										"NOCVue Unity does not support voice service on "+assignedTech+" assignedTech");
							}
						}
					}
				}


			}else {
				if(jsonObject.has("telephoneNumber")) {
					String telephoneNumber=jsonObject.getString("telephoneNumber");
					if(telephoneNumber==null || telephoneNumber.isEmpty()) {
						jsonValidatorModel.setNullKeys("telephoneNumber");
					}
				}
				jsonValidatorModel.setProcessingReportList(processingReportList);
			}
		}else {
			if(jsonObject.has("telephoneNumber")) {
				String telephoneNumber=jsonObject.getString("telephoneNumber");
				if(telephoneNumber==null || telephoneNumber.isEmpty()) {
					jsonValidatorModel.setNullKeys("telephoneNumber");
				}
			}
			List<ProcessingReport> processingReportList =basedOnVendorValidation(jsonString,jsonObject,jsonValidatorModel);
			processingReportList.add(processingReport);
			jsonValidatorModel.setSuccess(false);
			jsonValidatorModel.setProcessingReportList(processingReportList);
			System.out.println("jsonValidatorModel   :::  "+jsonValidatorModel);
		}
		jsonObject=null;
		return jsonValidatorModel;	
	}

	private static List<ProcessingReport> basedOnVendorValidation(String jsonString, JSONObject jsonObject, JsonValidatorModel jsonValidatorModel) throws IOException {
		List<ProcessingReport> processingReportList = new ArrayList<ProcessingReport>();
		jsonValidatorModel.setSuccess(true);
		String assignedTech=jsonObject.getString("assignedTech");
		if(assignedTech.equalsIgnoreCase("gpon")||assignedTech.equalsIgnoreCase("bpon")) {
			ProcessingReport processingReport = jsonValidation(jsonString, JsonSchemaConstant.PON_JSON_SCHEMA);
			if(!processingReport.isSuccess()) {
				jsonValidatorModel.setSuccess(false);
				processingReportList.add(processingReport);
			}
		}
		boolean isVoice=(boolean) jsonObject.getJSONObject("voice").get("isVoice");
		if (isVoice) {
			ProcessingReport processingReport = jsonValidation(jsonString, JsonSchemaConstant.VOICE_JSON_SCHEMA);
			if(!processingReport.isSuccess()) {
				jsonValidatorModel.setSuccess(false);
				processingReportList.add(processingReport);
			}
		}
		boolean isData=(boolean) jsonObject.getJSONObject("data").get("isData");
		if (isData) {
			ProcessingReport processingReport = jsonValidation(jsonString, JsonSchemaConstant.DATA_JSON_SCHEMA);
			if(!processingReport.isSuccess()) {
				jsonValidatorModel.setSuccess(false);
				processingReportList.add(processingReport);
			}
		}
		boolean isVideo=(boolean) jsonObject.getJSONObject("video").get("isVideo");
		if (isVideo) {
			ProcessingReport processingReport = jsonValidation(jsonString, JsonSchemaConstant.VIDEO_JSON_SCHEMA);
			if(!processingReport.isSuccess()) {
				jsonValidatorModel.setSuccess(false);
				processingReportList.add(processingReport);
			}
		}
		jsonValidatorModel.setProcessingReportList(processingReportList);
		return processingReportList;

	}

	public static String getrequiredErrorKeysBasedOnProcessingMessage(JsonValidatorModel jsonValidatorModel,String seqNo) throws JsonParseException, JsonMappingException, IOException {
		if(jsonValidatorModel.getProcessingReport()!=null) {
			for (ProcessingMessage processingMessage : jsonValidatorModel.getProcessingReport()) {
				JsonErrorModel jsonErrorModel =JsonMapperUtil.mapFromJson(processingMessage.asJson().toString(), JsonErrorModel.class);
				System.out.println(jsonErrorModel);
				jsonValidatorModel=getrequiredErrorKeysForVendor(jsonErrorModel,jsonValidatorModel);
			}
		}
		if(jsonValidatorModel.getProcessingReportList()!=null) {
			for (ProcessingReport processingReport : jsonValidatorModel.getProcessingReportList()) {
				for (ProcessingMessage processingMessage : processingReport) {
					JsonErrorModel jsonErrorModel =JsonMapperUtil.mapFromJson(processingMessage.asJson().toString(), JsonErrorModel.class);
					System.out.println(jsonErrorModel);
					jsonValidatorModel=getrequiredErrorKeysForVendor(jsonErrorModel,jsonValidatorModel);
				}
			}
		}

		if(!jsonValidatorModel.getMissingKeys().isEmpty()) {
			String missingKey = jsonValidatorModel.getMissingKeys().stream().map(a -> String.valueOf(a))
					.collect(Collectors.joining(","));
			String message="parameter(s) missing: "+missingKey;
			return getJsonErrorMessage(message,seqNo);
		}else if(!jsonValidatorModel.getNullKeys().isEmpty()) {
			String missingKey = jsonValidatorModel.getNullKeys().stream().map(a -> String.valueOf(a))
					.collect(Collectors.joining(","));
			String message="Invalid values for parameters "+missingKey+". Empty/Null is not supported";
			return getJsonErrorMessage(message,seqNo);
		}else if(jsonValidatorModel.getResponse()!=null && !jsonValidatorModel.getResponse().isEmpty()){
			return getJsonErrorMessage(jsonValidatorModel.getResponse(),seqNo);
		}else {
			String formateErrorKeys = jsonValidatorModel.getFormateErrorKeys().stream().map(a -> String.valueOf(a))
					.collect(Collectors.joining(","));
			return getJsonErrorMessage(formateErrorKeys,seqNo);
		}
	}

	public static JsonValidatorModel getrequiredErrorKeysForVendor1(JsonErrorModel jsonErrorModel,JsonValidatorModel jsonValidatorModel) {
		if(jsonErrorModel.getKeyword().contains("required")) {
			if (jsonErrorModel.getInstance().getPointer().isEmpty()) {
				jsonValidatorModel.setMissingKeys(jsonErrorModel.getMissing());
			}else {
				String missingKeyGroupName=jsonErrorModel.getInstance().getPointer().substring(jsonErrorModel.getInstance().getPointer().lastIndexOf("/") + 1);
				for (String missingKey : jsonErrorModel.getMissing()) {
					String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
					if(nullkeys.length==3) {
						jsonValidatorModel.setMissingKeys(nullkeys[1]+"."+missingKeyGroupName+"."+missingKey);
					}else {
						jsonValidatorModel.setMissingKeys(missingKeyGroupName+"."+missingKey);
					}
				}
			}
		}else if(jsonErrorModel.getKeyword().contains("type")){
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			if(nullkeys.length==4) {
				jsonValidatorModel.setNullKeys(nullkeys[1]+"."+nullkeys[2]+"."+nullkeys[3]);
			}else if(nullkeys.length==3) {
				String groupKeyName=nullkeys[1];
				String missingKeyName=nullkeys[2];
				jsonValidatorModel.setNullKeys(groupKeyName+"."+missingKeyName);
			}else if(nullkeys.length==2) {
				String missingKeyName=nullkeys[1];
				jsonValidatorModel.setNullKeys(missingKeyName);
			}
		}else if(jsonErrorModel.getKeyword().contains("minLength")){
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			System.out.println(Arrays.toString(nullkeys)+"  "+nullkeys.length);
			if(nullkeys.length==4) {
				jsonValidatorModel.setNullKeys(nullkeys[1]+"."+nullkeys[2]+"."+nullkeys[3]);
			}else if(nullkeys.length==3) {
				String groupKeyName=nullkeys[1];
				String missingKeyName=nullkeys[2];
				jsonValidatorModel.setNullKeys(groupKeyName+"."+missingKeyName);
			}else if(nullkeys.length==2) {
				String missingKeyName=nullkeys[1];
				jsonValidatorModel.setNullKeys(missingKeyName);
			}
		}else if(jsonErrorModel.getKeyword().contains("minItems")){
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			if(nullkeys.length==4) {
				jsonValidatorModel.setNullKeys(nullkeys[1]+"."+nullkeys[2]+"."+nullkeys[3]);
			}else if(nullkeys.length==3) {
				String groupKeyName=nullkeys[1];
				String missingKeyName=nullkeys[2];
				jsonValidatorModel.setFormateErrorKeys(groupKeyName+"."+missingKeyName);
			}else if(nullkeys.length==2) {
				String missingKeyName=nullkeys[1];
				jsonValidatorModel.setFormateErrorKeys(missingKeyName);
				if(missingKeyName.contains("scp"))
					jsonValidatorModel.setResponse("scp array is to short");
			}
		}else if(jsonErrorModel.getKeyword().contains("maxItems")){
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			if(nullkeys.length==3) {
				String groupKeyName=nullkeys[1];
				String missingKeyName=nullkeys[2];
				jsonValidatorModel.setFormateErrorKeys(groupKeyName+"."+missingKeyName);
			}else if(nullkeys.length==2) {
				String missingKeyName=nullkeys[1];
				jsonValidatorModel.setFormateErrorKeys(missingKeyName);
				if(missingKeyName.contains("scp"))
					jsonValidatorModel.setResponse("NOCVue Unity curently does not support scp array more than 2");
			}
		}else if(jsonErrorModel.getKeyword().contains("pattern")) {
			System.out.println( "pattern--------------------");
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			System.out.println(nullkeys[1]);
			if(isContainExactWord(nullkeys[1], "telephoneNumber")) {
				System.out.println("inside telephoneNumber");
				jsonValidatorModel.setResponse("Invalid telephoneNumber");
			}
		}else if(jsonErrorModel.getKeyword().contains("anyOf")) {
			System.out.println( "pattern11111111111111111111111111");
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			System.out.println(nullkeys[1]);
			if(isContainExactWord(nullkeys[1], "deviceIP")) {
				throw new IllegalArgumentException("invalid deviceIP");
			}
			if(isContainExactWord(nullkeys[1], "telephoneNumber")) {
				System.out.println("inside telephoneNumber");
				jsonValidatorModel.setResponse("Invalid telephoneNumber");
			}
		}
		else {
			if(jsonErrorModel.getMissing()!=null) {
				jsonValidatorModel.setFormateErrorKeys(jsonErrorModel.getMissing());
			}else {
				jsonValidatorModel.setFormateErrorKeys(jsonErrorModel.getInstance().getPointer().replaceAll("[^a-zA-Z0-9]", "")); 
			}
		}
		return jsonValidatorModel;
	}
	
	public static JsonValidatorModel getrequiredErrorKeysForVendor(JsonErrorModel jsonErrorModel,JsonValidatorModel jsonValidatorModel) {
		if(jsonErrorModel.getKeyword().contains("required")) {
			if (jsonErrorModel.getInstance().getPointer().isEmpty()) {
				jsonValidatorModel.setMissingKeys(jsonErrorModel.getMissing());
			}else {
				String missingKeyGroupName=jsonErrorModel.getInstance().getPointer().substring(jsonErrorModel.getInstance().getPointer().lastIndexOf("/") + 1);
				for (String missingKey : jsonErrorModel.getMissing()) {
					String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
					if(nullkeys.length==3) {
						jsonValidatorModel.setMissingKeys(nullkeys[1]+"."+missingKeyGroupName+"."+missingKey);
					}else {
						jsonValidatorModel.setMissingKeys(missingKeyGroupName+"."+missingKey);
					}
				}
			}
		}else if(jsonErrorModel.getKeyword().contains("type")){
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			if(nullkeys.length==4) {
				jsonValidatorModel.setNullKeys(nullkeys[1]+"."+nullkeys[2]+"."+nullkeys[3]);
			}else if(nullkeys.length==3) {
				String groupKeyName=nullkeys[1];
				String missingKeyName=nullkeys[2];
				jsonValidatorModel.setNullKeys(groupKeyName+"."+missingKeyName);
			}else if(nullkeys.length==2) {
				String missingKeyName=nullkeys[1];
				jsonValidatorModel.setNullKeys(missingKeyName);
			}
		}else if(jsonErrorModel.getKeyword().contains("minLength")){
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			if(nullkeys.length==4) {
				jsonValidatorModel.setNullKeys(nullkeys[1]+"."+nullkeys[2]+"."+nullkeys[3]);
			}else if(nullkeys.length==3) {
				String groupKeyName=nullkeys[1];
				String missingKeyName=nullkeys[2];
				jsonValidatorModel.setNullKeys(groupKeyName+"."+missingKeyName);
			}else if(nullkeys.length==2) {
				String missingKeyName=nullkeys[1];
				jsonValidatorModel.setNullKeys(missingKeyName);
			}
		}else if(jsonErrorModel.getKeyword().contains("maxItems")){
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			if(nullkeys.length==3) {
				String groupKeyName=nullkeys[1];
				String missingKeyName=nullkeys[2];
				jsonValidatorModel.setFormateErrorKeys(groupKeyName+"."+missingKeyName);
			}else if(nullkeys.length==2) {
				String missingKeyName=nullkeys[1];
				jsonValidatorModel.setFormateErrorKeys(missingKeyName);
				if(missingKeyName.contains("scp"))
					jsonValidatorModel.setResponse("NOCVue Unity curently does not support scp array more than 2");
			}
		}else if(jsonErrorModel.getKeyword().contains("minItems")){
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			if(nullkeys.length==3) {
				String groupKeyName=nullkeys[1];
				String missingKeyName=nullkeys[2];
				jsonValidatorModel.setFormateErrorKeys(groupKeyName+"."+missingKeyName);
			}else if(nullkeys.length==2) {
				String missingKeyName=nullkeys[1];
				jsonValidatorModel.setFormateErrorKeys(missingKeyName);
				if(missingKeyName.contains("scp"))
					jsonValidatorModel.setResponse("scp array is to short");
			}
		}else if(jsonErrorModel.getKeyword().contains("pattern")) {
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			if(isContainExactWord(nullkeys[1], "telephoneNumber")) {
				jsonValidatorModel.setResponse("Invalid telephoneNumber");
			}
		}else if(jsonErrorModel.getKeyword().contains("anyOf")) {
			String[] nullkeys=jsonErrorModel.getInstance().getPointer().split("/");
			if(isContainExactWord(nullkeys[1], "deviceIP")) {
				throw new IllegalArgumentException("Invalid deviceIP");
			}
		}else {
			if(jsonErrorModel.getMissing()!=null) {
				jsonValidatorModel.setFormateErrorKeys(jsonErrorModel.getMissing());
			}else {
				jsonValidatorModel.setFormateErrorKeys(jsonErrorModel.getInstance().getPointer().replaceAll("[^a-zA-Z0-9]", "")); 
			}
		}
		return jsonValidatorModel;
	}


	public static String getJsonErrorMessage(String message, String seqNo){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("message", message);
		jsonObject.put("transaction_id", seqNo);
		return jsonObject.toString();	
	}


	public static String getJsonErrorMessage(String message,String key, String seqNo){
		JSONObject jsonObject=new JSONObject();
		if(message.contains("required parameter  value is too short (length: 0")) {
			jsonObject.put("message", key+" is empty");
		}else {
			jsonObject.put("message", message+" "+key);
		}
		jsonObject.put(JsonSchemaConstant.TRANSACTION_ID, seqNo);
		return jsonObject.toString();	
	}

	public static ProcessingReport jsonValidation(String jsonString, String fileName) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode JsonNodeServiceFile = null;
		ProcessingReport result = null;
		final MessageBundle bundle= MessageBundles.getBundle(JsonSchemaValidationBundle.class)
				.thaw().appendSource(LoadingValidationErrorMessage.source).freeze();

		final ValidationConfiguration cfg = ValidationConfiguration.newBuilder()
				.setValidationMessages(bundle).freeze();

		JsonNode serviceJsonNode = mapper.readTree(jsonString);
		JsonNodeServiceFile=LoadingSchemaFiles.getJsonSchemBasedOnFileName(fileName);
		final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder().setValidationConfiguration(cfg).freeze();
		JsonSchema fileSchema;

		try {
			fileSchema = factory.getJsonSchema(JsonNodeServiceFile);
			result = fileSchema.validate(serviceJsonNode,true);
		} catch (ProcessingException e) {
			log.info("json schema processing exception :  "+e.getMessage());
		}
		return result;
	}

	public static JsonNode loadResource(String name) throws IOException {
		return JsonLoader.fromResource(JsonSchemaConstant.FOLDER_LOCATION + name);
	}

	public static String getMissingKeys(ProcessingReport processingReport, String seqNo){
		String key="";
		StringBuilder jsonValidationErrors = new StringBuilder();
		for (ProcessingMessage processingMessage : processingReport) {
			jsonValidationErrors.append(processingMessage.getMessage());
			if(processingMessage.asJson().get("missing")!=null) {
				key=processingMessage.asJson().get("missing").toString().replaceAll("[^a-zA-Z0-9]", " ");
				key=key.replaceAll("\\s+"," ").trim().replaceAll(" .+$", "");
			}else {
				key=processingMessage.asJson().get("instance").get("pointer").toString().replaceAll("[^a-zA-Z0-9]", " ");
				key=key.replaceAll("\\s+"," ").trim().replaceAll(" .+$", "");
			}
			log.info(getJsonErrorMessage(jsonValidationErrors.toString(),key,seqNo));
			return getJsonErrorMessage(jsonValidationErrors.toString(),key,seqNo);
		}
		return null;
	}

	public static JsonValidatorModel additionalParameterValidationBasedOnVendorAndModel(String vendor,String model,String assinedTech,String request) throws IOException {
		JSONObject jsonRequest=new JSONObject(request);
		List<ProcessingReport> processingReportList = new ArrayList<ProcessingReport>();
		JsonValidatorModel jsonValidatorModel=new JsonValidatorModel();
		jsonValidatorModel.setSuccess(true);
		ProcessingReport processingReport = null;
		if(vendor.equalsIgnoreCase("adtran") && isContainExactWord(assinedTech, "gpon,bpon") && isContainExactWord(model, "TA5000,TA5004,TA5006")) {
			if (jsonRequest.has("pon")) {
				processingReport=validationForAdtranTA500WithPonGroup(request);
				if (!processingReport.isSuccess()) {
					jsonValidatorModel.setSuccess(false);
					processingReportList.add(processingReport);
				}
			}
			boolean isVoice=false;
			if(jsonRequest.has("voice") && jsonRequest.getJSONObject("voice").has("isVoice")) {
				isVoice=jsonRequest.getJSONObject("voice").getBoolean("isVoice");
			}
			System.out.println(isVoice);
			if (isVoice) {
				processingReport=validationForAdtranTA500WithVoiceGroup(request);
				if (!processingReport.isSuccess()) {
					jsonValidatorModel.setSuccess(false);
					processingReportList.add(processingReport);
				}
			}
		}		
		if(vendor.equalsIgnoreCase("dzhone") && isContainExactWord(model, "mx1u194,mx1421")) {
			boolean isData=false;
			if(jsonRequest.has("data") && jsonRequest.getJSONObject("data").has("isData")) {
				isData=jsonRequest.getJSONObject("data").getBoolean("isData");
			}
			System.out.println(isData);
			if (isData) {
				processingReport=validationForDZhoneMXWithDataGroup(request);
				if (!processingReport.isSuccess()) {
					processingReportList.add(processingReport);
					jsonValidatorModel.setSuccess(false);
				}
			}
			boolean isVoice=false;
			if(jsonRequest.has("voice") && jsonRequest.getJSONObject("voice").has("isVoice")) {
				isVoice=jsonRequest.getJSONObject("voice").getBoolean("isVoice");
			}
			System.out.println(isData);
			if (isVoice) {
				processingReport=validationForDZhoneMXWithVoiceGroup(request);
				if (!processingReport.isSuccess()) {
					processingReportList.add(processingReport);
					jsonValidatorModel.setSuccess(false);
				}
			}
			boolean isVideo=false;
			if(jsonRequest.has("video") && jsonRequest.getJSONObject("video").has("isVideo")) {
				isVideo=jsonRequest.getJSONObject("video").getBoolean("isVideo");
			}
			if (isVideo) {
				processingReport=validationForDZhoneMXWithVideoGroup(request);
				if (!processingReport.isSuccess()) {
					processingReportList.add(processingReport);
					jsonValidatorModel.setSuccess(false);
				}
			}
		}
		jsonValidatorModel.setProcessingReportList(processingReportList);
		return jsonValidatorModel;
	}

	private static ProcessingReport validationForDZhoneMXWithVideoGroup(String request) throws IOException {
		return jsonValidation(request, JsonSchemaConstant.ADDITIONALPARAMETER_DZHONE_MX_VIDEO_SCHEMA);
	}

	private static ProcessingReport validationForDZhoneMXWithVoiceGroup(String request) throws IOException {
		return jsonValidation(request, JsonSchemaConstant.ADDITIONALPARAMETER_DZHONE_MX_VOICE_SCHEMA);
	}

	private static ProcessingReport validationForDZhoneMXWithDataGroup(String request) throws IOException {
		return jsonValidation(request, JsonSchemaConstant.ADDITIONALPARAMETER_DZHONE_MX_DATA_SCHEMA);
	}

	private static ProcessingReport validationForAdtranTA500WithVoiceGroup(String request) throws IOException {
		return jsonValidation(request, JsonSchemaConstant.ADDITIONALPARAMETER_ADTRAN_TA500_VOICE_SCHEMA);
	}

	private static ProcessingReport validationForAdtranTA500WithPonGroup(String request) throws IOException {
		return jsonValidation(request, JsonSchemaConstant.ADDITIONALPARAMETER_ADTRAN_TA500_PON_SCHEMA);
	}

	public static boolean isContainExactWord(String partWord,String fullString){
		String pattern = "\\b"+partWord.toLowerCase()+"\\b";
		Pattern p=Pattern.compile(pattern);
		Matcher m=p.matcher(fullString.toLowerCase());
		return m.find();
	}


}
