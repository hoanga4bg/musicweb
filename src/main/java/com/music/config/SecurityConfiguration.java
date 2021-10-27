package com.music.config;







import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

import com.music.business.account.AccountDAO;
import com.music.business.account.IAccountDAO;
import com.music.config.OAuth2.MyOAuth2User;
import com.music.config.OAuth2.MyOAuth2UserService;
import com.music.config.OAuth2.OAuth2LoginSuccessHandle;

import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;




@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

//	@Autowired
//	DataSource dataSource;
	
	@Autowired
	UserDetailsService userDetailsService=new MyUserDetailsService();
	@Autowired
	private OAuth2LoginSuccessHandle oAuth2LoginSuccessHandle;
	@Autowired
    private MyOAuth2UserService oauth2UserService;
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
					.antMatchers("/myaccount","/payment").hasRole("USER")
					.antMatchers("/admin/*").hasRole("ADMIN")
					.antMatchers("/song","/playlist").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
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
						.userService(oauth2UserService)
				.and()
					.successHandler(oAuth2LoginSuccessHandle)
				.and()

					.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/");
		
		
		
	}
	

}
