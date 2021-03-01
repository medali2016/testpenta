package com.ett.penta.model.dto;


public class JwtResponseLogin {
	private String jwt;
    private UserPrincipal user;
	public JwtResponseLogin(String jwttoken, UserPrincipal user) {
		this.jwt = jwttoken;
		this.user = user;
	}

	public JwtResponseLogin(UserPrincipal user) {
		this.user = user;
	}
	
	public UserPrincipal getUser() {
		return user;
	}

	public void setUser(UserPrincipal user) {
		this.user = user;
	}

	public String getJwttoken() {
		return jwt;
	}

	public JwtResponseLogin(String jwttoken) {
		this.jwt = jwttoken;
	}

	public String getToken() {
		return this.jwt;
	}
}
