package com.contacttura.contacttura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.contacttura.contacttura.service.CustomUserDetailService;

//import com.contacttura.contacttura.service.CustomUserDetailService;

//import com.contactura.contactura.service.CustomUserDetailService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
//		super.configure(http);
		http.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic()
			.and()
			.csrf().disable();
	}
	
	//Este método foi criado para a autenticação de usuarios em memória
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
			.withUser("lucas").password("{noop}root").roles("USER")
			.and()
			.withUser("ADMIN").password("{noop}root").roles("USER", "ADMIN");
	}
	
	
	//@Override
	//protected void configure(AuthenticationManagerBuilder auth) throws Exception{
	//	auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
	//}

	
	}
