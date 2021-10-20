package com.music.config;





import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.music.config.OAuth2.MyOAuth2UserService;
import com.music.config.OAuth2.OAuth2LoginSuccessHandle;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

//	@Autowired
//	DataSource dataSource;
	
	@Autowired
	UserDetailsService userDetailsService=new MyUserDetailsService();
	@Autowired
	private MyOAuth2UserService oAuth2UserService;
	@Autowired
	private OAuth2LoginSuccessHandle oAuth2LoginSuccessHandle;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/admin/*","/*","/song","/playlist").hasRole("ADMIN")
					.antMatchers("/myaccount","/payment","/playlist","/song").hasRole("USER")
					.antMatchers("/css/**",
							"/js/*",
							"/img/*",
							"/vendors/*",
							"/fonts/*",
							"/assets/*",
							"/dist/*",
							"/images/*",
							"/font-awesome/*",
							"/vendor/*",
							"/*",
							"/login",
							"/"
							).permitAll()
					.and()
					.exceptionHandling().accessDeniedPage("/logout")	
					.and()
				.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/j_spring_security_check")
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/default")
					.failureUrl("/login?error=true")
				.and()
				.oauth2Login()
					.loginPage("/login")
					.userInfoEndpoint()
					.userService(oAuth2UserService)
				.and()
				.successHandler(oAuth2LoginSuccessHandle)
				.and()
					.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/");
		
		
		
	}
	

	
}
