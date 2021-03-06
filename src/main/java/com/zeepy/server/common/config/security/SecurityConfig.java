package com.zeepy.server.common.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(customAuthenticationEntryPoint)
			.accessDeniedHandler(customAccessDeniedHandler)

			.and()

			.headers().frameOptions().disable()

			.and()

			.authorizeRequests()
			.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
			.antMatchers("/h2-console/*").permitAll()
			.antMatchers("/api/user/registration").permitAll()
			.antMatchers("/api/auth/**").permitAll()
			// .antMatchers(HttpMethod.GET, "/api/buildings/address", "/api/buildings/addresses").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/api/buildings", "/api/buildings/{id}", "/api/deals").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/buildings/{id}", "/api/deals/{id}").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/buildings/{id}", "/api/deals/{id}").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST,"/api/user/redundancy/*").permitAll()
			.anyRequest().authenticated()

			.and()

			.addFilterBefore(new JwtAuthenticationFilter(jwtAuthenticationProvider),
				UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
