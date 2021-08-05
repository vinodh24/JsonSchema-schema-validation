package com.vinodh.jsonvalidation.error.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonIgnoreProperties
@JsonInclude(Include.NON_NULL)
public class JsonErrorModel {
	
	private String level;
	private Schema schema;
	private Instance instance;
	private String domain;
	private String keyword;
	private String message;
	private String found;
	private List<String> missing;
	private List<String> expected;
	
	public List<String> getMissing() {
		return missing;
	}
	public void setMissing(List<String> missing) {
		this.missing = missing;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Schema getSchema() {
		return schema;
	}
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	public Instance getInstance() {
		return instance;
	}
	public void setInstance(Instance instance) {
		this.instance = instance;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFound() {
		return found;
	}
	public void setFound(String found) {
		this.found = found;
	}
	public List<String> getExpected() {
		return expected;
	}
	public void setExpected(List<String> expected) {
		this.expected = expected;
	}
	
	@Override
	public String toString() {
		return "JsonErrorModel [level=" + level + ", schema=" + schema + ", instance=" + instance + ", domain=" + domain
				+ ", keyword=" + keyword + ", message=" + message + ", found=" + found + ", missing=" + missing
				+ ", expected=" + expected + "]";
	}
	
}
