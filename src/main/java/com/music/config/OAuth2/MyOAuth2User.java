package com.music.config.OAuth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class MyOAuth2User implements OAuth2User {
	
	
	private OAuth2User oauth2User;
	
	public MyOAuth2User(OAuth2User oauth2User) {

		this.oauth2User = oauth2User;
	}

	@Override
	public Map<String, Object> getAttributes() {
		
		return oauth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return oauth2User.getAuthorities();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return oauth2User.getAttribute("name");
	}
	
	public String getUsername() {
		return oauth2User.getAttribute("email");
	}
	
	public boolean getVip() {
		return false;
	}
	
}
