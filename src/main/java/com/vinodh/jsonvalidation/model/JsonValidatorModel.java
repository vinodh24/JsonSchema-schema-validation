package com.vinodh.jsonvalidation.model;

import java.util.ArrayList;
import java.util.List;

import com.github.fge.jsonschema.core.report.ProcessingReport;

public class JsonValidatorModel {
	
	private  boolean isSuccess;
	private  String response;
	private  ProcessingReport processingReport;
	private  List<ProcessingReport> processingReportList;
	private  List<String> missingKeys=new ArrayList<>();
	private  List<String> nullKeys=new ArrayList<>();
	private  List<String> formateErrorKeys=new ArrayList<>();
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public List<ProcessingReport> getProcessingReportList() {
		return processingReportList;
	}
	public void setProcessingReportList(List<ProcessingReport> processingReportList) {
		this.processingReportList = processingReportList;
	}
	public List<String> getMissingKeys() {
		return missingKeys;
	}
	public void setMissingKeys(List<String> missingKeys) {
		this.missingKeys = missingKeys;
	}
	public void setMissingKeys(String missingKeys) {
		this.missingKeys.add(missingKeys);
	}
	public List<String> getNullKeys() {
		return nullKeys;
	}
	public void setNullKeys(List<String> nullKeys) {
		this.nullKeys = nullKeys;
	}
	public void setNullKeys(String nullKeys) {
		this.nullKeys.add(nullKeys);
	}
	public List<String> getFormateErrorKeys() {
		return formateErrorKeys;
	}
	public void setFormateErrorKeys(List<String> formateErrorKeys) {
		this.formateErrorKeys = formateErrorKeys;
	}
	public void setFormateErrorKeys(String formateErrorKeys) {
		this.formateErrorKeys.add(formateErrorKeys);
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	public ProcessingReport getProcessingReport() {
		return processingReport;
	}
	public void setProcessingReport(ProcessingReport processingReport) {
		this.processingReport = processingReport;
	}
	@Override
	public String toString() {
		return "JsonValidatorModel [isSuccess=" + isSuccess + ", response=" + response + ", processingReport="
				+ processingReport + ", processingReportList=" + processingReportList + ", missingKeys=" + missingKeys
				+ ", nullKeys=" + nullKeys + ", formateErrorKeys=" + formateErrorKeys + "]";
	}

}
