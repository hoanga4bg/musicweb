package com.music.config.OAuth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.music.business.account.IAccountDAO;
import com.music.entity.Account;

@Service
public class OAuth2LoginSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler{
	@Autowired
	private IAccountDAO accountDAO;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		MyOAuth2User oauth2User=(MyOAuth2User) authentication.getPrincipal();
		Account account=accountDAO.findByUsername(oauth2User.getUsername());
		if(account!=null) {
			System.out.println("Tài khoản đã tồn tại");
		}
		else {
			Account saveAccount =new Account();
			saveAccount.setEmail("");
			saveAccount.setUsername(oauth2User.getUsername());
			saveAccount.setPassword(RandomStringUtils.randomAlphabetic(8));
			saveAccount.setVip(false);
			saveAccount.setStatus(true);
			saveAccount.setRole("ROLE_USER");
			accountDAO.save(saveAccount);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
