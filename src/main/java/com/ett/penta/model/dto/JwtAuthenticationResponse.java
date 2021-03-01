package com.ett.penta.model.dto;


public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserPrincipal user;
    /*public User convertToUser(UserPrincipal userPrincipal) {
    	User user = new User (userPrincipal.getId(),userPrincipal.getFirstName()
    			,userPrincipal.getUsername(),userPrincipal.getEmail(),userPrincipal.getAuthorities());
    	return user;
    }*/
    public JwtAuthenticationResponse(String accessToken,UserPrincipal user) {
        this.accessToken = accessToken;
        //this.user= convertToUser(user);
        this.user = user;
    }

    public UserPrincipal getUserPrincipal() {
		return user;
	}

	public void setUserPrincipal(UserPrincipal userPrincipal) {
		this.user = userPrincipal;
	}

	public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
