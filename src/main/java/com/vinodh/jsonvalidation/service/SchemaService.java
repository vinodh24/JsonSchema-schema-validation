package com.vinodh.jsonvalidation.service;

import java.io.IOException;

import com.vinodh.jsonvalidation.model.JsonValidatorModel;
import com.vinodh.jsonvalidation.util.ConvertionUtility;
import com.vinodh.jsonvalidation.util.JsonSchemaValidationUtility;

public class SchemaService {

	public void addServiceSchemaValidation() {
		try {
			String	addServiceJsonRequest = ConvertionUtility.inputPutFileToString("addService_adtran_request");
			JsonValidatorModel jsonValidatorModel=JsonSchemaValidationUtility.addServiceValidation(addServiceJsonRequest);
			if (jsonValidatorModel.isSuccess() ) {
				//jsonValidatorModel=JsonSchemaValidationUtility.additionalParameterValidationBasedOnVendorAndModel("dzhone", "Mx1u194", "gpon", addServiceJsonRequest);
				//jsonValidatorModel=JsonSchemaValidationUtility.additionalParameterValidationBasedOnVendorAndModel("adtran", "TA5000", "gpon", addServiceJsonRequest);
				if (jsonValidatorModel.isSuccess() ) {
					System.out.println("success");
				}else {
					String errorMessage=JsonSchemaValidationUtility.getrequiredErrorKeysBasedOnProcessingMessage(jsonValidatorModel,"");
					System.out.println("errorMessage   ::"+errorMessage);
				}
			}else {
				System.out.println("failure");
				String errorMessage=JsonSchemaValidationUtility.getrequiredErrorKeysBasedOnProcessingMessage(jsonValidatorModel,"");
				System.out.println("errorMessage   ::"+errorMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void refreshPortService() {
		System.out.println("refreshPortService");
		try {
			String	refreshPortServiceRequest = ConvertionUtility.inputPutFileToString("refreshPortService_request");
			JsonValidatorModel jsonValidatorModel=JsonSchemaValidationUtility.refreshServiceSchemaValidation(refreshPortServiceRequest);
			if (jsonValidatorModel.isSuccess() ) {
				System.out.println("success");
			}else {
				System.out.println("failure");
				String errorMessage=JsonSchemaValidationUtility.getrequiredErrorKeysBasedOnProcessingMessage(jsonValidatorModel,"");
				System.out.println("errorMessage   ::"+errorMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	public void portIsService() {
		System.out.println("refreshPortService");
		try {
			String	refreshPortServiceRequest = ConvertionUtility.inputPutFileToString("refreshPortService_request");
			JsonValidatorModel jsonValidatorModel=JsonSchemaValidationUtility.commonServiceSchemaValidation(refreshPortServiceRequest);
			if (jsonValidatorModel.isSuccess() ) {
				System.out.println("success");
			}else {
				System.out.println("failure");
				String errorMessage=JsonSchemaValidationUtility.getrequiredErrorKeysBasedOnProcessingMessage(jsonValidatorModel,"");
				System.out.println("errorMessage   ::"+errorMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	public void deleteService() {
		System.out.println("refreshPortService");
		try {
			String	refreshPortServiceRequest = ConvertionUtility.inputPutFileToString("refreshPortService_request");
			JsonValidatorModel jsonValidatorModel=JsonSchemaValidationUtility.commonServiceSchemaValidation(refreshPortServiceRequest);
			if (jsonValidatorModel.isSuccess() ) {
				System.out.println("success");
			}else {
				System.out.println("failure");
				String errorMessage=JsonSchemaValidationUtility.getrequiredErrorKeysBasedOnProcessingMessage(jsonValidatorModel,"");
				System.out.println("errorMessage   ::"+errorMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
