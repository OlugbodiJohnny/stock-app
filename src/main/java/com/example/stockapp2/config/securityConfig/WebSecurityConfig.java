package com.example.stockapp2.config.securityConfig;

import com.example.stockapp2.security.jwt.AuthEntryPointJwt;
import com.example.stockapp2.security.jwt.AuthTokenFilter;
import com.example.stockapp2.security.service.UserDetailsServiceImpl;
import com.example.stockapp2.util.SecurityAuthorisationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

//    @Value("${paystack.ip_addresses}")
//    private String ipAddresses;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .exceptionHandling()
               .authenticationEntryPoint(unauthorizedHandler).and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
               .cors()
               .configurationSource(corsConfigurationSource())
               .and()
               .authorizeRequests()
               .antMatchers("/api/auth/login").permitAll()
//               .antMatchers("/swagger-ui/index.html/**").permitAll()
//               .hasAuthority(AuthoritiesConstants.DEV)
               .antMatchers(SecurityAuthorisationConstants.PUBLIC_URIS).permitAll()
//               .antMatchers("/api/subscription/confirm").access(ipAddresses)
//               .antMatchers("/api/subscription/confirm/").access(ipAddresses)
               .anyRequest()
               .authenticated();

       http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

       http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/actuator/**");
    }
}
