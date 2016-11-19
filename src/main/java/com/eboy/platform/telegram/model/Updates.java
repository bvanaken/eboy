package com.eboy.platform.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Updates {
	private String ok;
	private Update[] result;

	public Updates() {
		// TODO Auto-generated constructor stub
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public Update[] getResult() {
		return result;
	}

	public void setResult(Update[] result) {
		this.result = result;
	}
}
