package com.whatsappclone.response;

public class AuthResponse {

	private String jwt;
	private boolean isAuth;

	public AuthResponse(String jwt, boolean isAuth) {
		this.jwt = jwt;
		this.isAuth = isAuth;
	}

}
