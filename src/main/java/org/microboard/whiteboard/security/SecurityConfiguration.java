package org.microboard.whiteboard.security;

import org.microboard.whiteboard.model.user.visitors.UserRoleGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

	@Autowired
	private UserDetailsService userDetialsService;
	
	@SuppressWarnings("deprecation")
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetialsService)
		.passwordEncoder(passwordencoder());
		/*
		.passwordEncoder(NoOpPasswordEncoder.getInstance())
		.withUser("james")
		.password("test")
		.authorities(UserRoleGetter.UNIT_DIRECTOR_ROLE);
		*///
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/unitDirector").hasRole(UserRoleGetter.UNIT_DIRECTOR_ROLE)
        .anyRequest().authenticated()
	    .and()
	    .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password")
	    .permitAll()
	    .and()
	    .logout().permitAll()
	    .and().exceptionHandling().accessDeniedPage("/403");
		http.csrf().disable();
	}
	
	
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
}