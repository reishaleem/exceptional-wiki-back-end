package com.exceptionaloutlining.app.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String id;
	private String username;
	private String name;
	private List<String> roles;

	public JwtResponse(String accessToken, String id, String username, String name, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.name = name;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}
}