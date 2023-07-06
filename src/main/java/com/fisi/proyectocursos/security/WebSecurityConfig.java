package com.fisi.proyectocursos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fisi.proyectocursos.security.config.MyCustomAuthenticationProvider;
import com.fisi.proyectocursos.security.config.MySimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyCustomAuthenticationProvider authProvider;
	
//	@Qualifier("userDetailsServiceImpl")
//	@Autowired
//	private UserDetailsService userDetailsService;
//	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//	}
	
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
    
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
    	return new MySimpleUrlAuthenticationSuccessHandler();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/soy_centro").permitAll()
				.antMatchers("/css/**", "/js/**", "/images/**", "/libs/**", "/fonts/**", "/vendors/**").permitAll()
				.antMatchers("/registro/**", "/recuperar**", "/restablecer_contrasena**").permitAll()
				.antMatchers("/centro/**").hasRole("CENTER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/usuario/**").hasRole("USER")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.successHandler(myAuthenticationSuccessHandler())
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
    
    
    

}
