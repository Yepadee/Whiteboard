package org.microboard.whiteboard.security;

import org.microboard.whiteboard.model.user.visitors.UserRoleGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
@Profile("prod")
class SecurityConfigurationProd extends WebSecurityConfigurerAdapter  {

	@Autowired
	private UserDetailsService userDetialsService;
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetialsService)
		.passwordEncoder(passwordencoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/css/**","/icons/**","/img/**","/js/**","/layer/**").permitAll()
		.antMatchers("/").permitAll()
        .antMatchers("/unit_director/**").hasAuthority(UserRoleGetter.ROLE_UNIT_DIRECTOR)
        .anyRequest().fullyAuthenticated()
	    .and()
	    .formLogin().loginPage("/login").failureUrl("/login?error").usernameParameter("username").passwordParameter("password")
	    .permitAll()
	    .and()
	    .logout().permitAll()
	    .and()
	    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
	    .and()
	    .exceptionHandling().accessDeniedPage("/access_denied")
	    .and().csrf();
		
		http.headers().frameOptions().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/javax.faces.resource/**");
	}
	
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
}

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
@Profile("dev")
class SecurityConfigurationDev extends SecurityConfigurationProd  {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/css/**","/icons/**","/img/**","/js/**","/layer/**", "/h2-console/**").permitAll()
		.antMatchers("/").permitAll()
        .antMatchers("/unit_director/**").hasAuthority(UserRoleGetter.ROLE_UNIT_DIRECTOR)
        .anyRequest().fullyAuthenticated()
	    .and()
	    .formLogin().loginPage("/login_dev").failureUrl("/login_dev?error").usernameParameter("username").passwordParameter("password")
	    .permitAll()
	    .and()
	    .logout().permitAll()
	    .and()
	    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login_dev?logout")
	    .and()
	    .exceptionHandling().accessDeniedPage("/access_denied")
	    .and().csrf().ignoringAntMatchers("/h2-console/**");
		
		http.headers().frameOptions().disable();
	}
}