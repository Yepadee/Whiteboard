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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

	@Autowired
	private UserDetailsService userDetialsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetialsService);//.passwordEncoder(passwordencoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
        .antMatchers(
                "/",
                "/js/**",
                "/css/**",
                "/img/**",
                "/webjars/**").permitAll()
        .antMatchers("/unitDirector").hasRole(UserRoleGetter.UNIT_DIRECTOR_ROLE)
        .anyRequest().authenticated()
    .and()
    .formLogin()
        .loginPage("/login")
        .permitAll()
    .and()
    .logout()
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login?logout")
        .permitAll()
    .and()
    .exceptionHandling()
        ;//.accessDeniedHandler(accessDeniedHandler);
	}
	
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
}