package com.music.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.music.business.account.IAccountDAO;
import com.music.entity.Account;
import com.music.entity.Song;

@Controller
public class AccountController {
	@Autowired
	private IAccountDAO accountDAO;
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String defaultHome() {
		Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorities = auth.getAuthorities();
        String myRole = authorities.toArray()[0].toString();
//        System.out.println(myRole);
		if(myRole.equals("ROLE_ADMIN")) {
			return "redirect:/admin/song";
		} 
		else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String loginPage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			model.addAttribute("account",new Account());
			return "web/login";
		}
		else {
			return "redirect:/";	
		}
		
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/";
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	private String register(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			model.addAttribute("account",new Account());
			return "web/register";
		}
		else {
			return "redirect:/defaut";	
		}
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	private String register(Account account, @RequestParam("re-password") String re_pass) {

		if (accountDAO.findByUsername(account.getUsername()) != null) {
			return "redirect:/register?usererror=true";
		} 
		else {
			if (account.getPassword().equals(re_pass) == false) {
				return "redirect:/register?passerror=true";
			} 
			else {
				if (accountDAO.findByEmail(account.getEmail()) != null) {
					return "redirect:/register?emailerror=true";
				} 
				else {
					account.setVip(false);
					account.setRole("ROLE_USER");
					account.setStatus(true);
					accountDAO.save(account);
					String text="Tài khoản của bạn là: "+account.getUsername()
								+"\n"+"Mật khẩu của bạn là: "+account.getPassword()
								+"\n"+"Vui lòng bảo mật tài khoản của bạn.";
					accountDAO.sendEmail(account.getEmail(), "Đăng ký thành công 9sound.com", text);
					return "redirect:/login?success=true";
				}
			}
		}

	}
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	private String forgetPass() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			
			return "web/forgot";
		}
		else {
			
			return "redirect:/defaut";	
		}
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	private String forgetPass( @RequestParam("username") String username) {

		if (accountDAO.findByUsername(username) == null) {
			return "redirect:/forgot?error=true";
		} 
		else {
			Account account=accountDAO.findByUsername(username);
			String text="Tài khoản của bạn là: "+account.getUsername()
						+"\n"+"Mật khẩu của bạn là: "+account.getPassword()
						+"\n"+"Vui lòng bảo mật tài khoản của bạn.";
			accountDAO.sendEmail(account.getEmail(), "Lấy lại mật khẩu 9sound.com", text);
			return "redirect:/forgot?success=true";
		}

	}
	@RequestMapping(value="/changepass",method = RequestMethod.GET)
	private String changePass() {
		return "web/changepass";
	}
	@RequestMapping(value="/changepass",method = RequestMethod.POST)
	private String changePass(@RequestParam("username") String username,
			@RequestParam("oldpass") String pass,
			@RequestParam("newpass") String newpass,
			@RequestParam("renewpass") String renewpass) {
		
		Account account=accountDAO.findByUsernameAndPassword(username, pass);
		if(account==null) {
			return "redirect:/changepass?error=true";
		}
		else {
			if(newpass.equals(renewpass)==false) {
				return "redirect:/changepass?erpass=true";
			}
			else {
				account.setPassword(newpass);
				accountDAO.save(account);
				return "redirect:/changepass?success=true";
			}
		}

	}
	
}
