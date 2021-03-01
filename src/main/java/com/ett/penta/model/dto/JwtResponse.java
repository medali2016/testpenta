package com.ett.penta.model.dto;


public class JwtResponse {
	private static final long serialVersionUID = -8091879091924046844L;
	private String jwttoken;
    private UserPrincipal user;
	public JwtResponse(String jwttoken, UserPrincipal user) {
		this.jwttoken = jwttoken;
		this.user = user;
	}

	public JwtResponse(UserPrincipal user) {
		this.user = user;
	}
	
	public UserPrincipal getUser() {
		return user;
	}

	public void setUser(UserPrincipal user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
