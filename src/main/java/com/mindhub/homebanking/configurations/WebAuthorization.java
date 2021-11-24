package com.mindhub.homebanking.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity

@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected  void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console/**").hasAuthority("ADMIN")
                .antMatchers("/web/manager.html").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/transactions").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/loans").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers("/api/clients/current/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/api/loans").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/api/**").hasAnyAuthority("ADMIN")
                .antMatchers("/web/index.html").permitAll()
                .antMatchers("/web/assets/**").permitAll()
                .antMatchers("/web/css/styles.css").permitAll()
                .antMatchers("/web/script/index.js").permitAll()
                .antMatchers("/web/favicon.ico").permitAll()
                .antMatchers("/**").hasAnyAuthority("USER", "ADMIN");

        http.formLogin().usernameParameter("email").passwordParameter("password").loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> {
            if( req.getRequestURI().contains("/web")) {
                res.sendRedirect("/web/index.html");
            }
            if( req.getRequestURI().contains("/api")){
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
            if( req.getRequestURI().contains("/rest")){
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        });

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin().failureHandler((req, res, exc) -> {
            if (exc.getMessage().equals("Maximum sessions of 1 for this principal exceeded")){
                res.sendError(HttpServletResponse.SC_CONFLICT);
            } else {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        });

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}
