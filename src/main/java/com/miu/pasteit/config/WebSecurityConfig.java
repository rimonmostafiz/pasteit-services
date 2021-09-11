package com.miu.pasteit.config;

import com.miu.pasteit.security.JWTAuthenticationFilter;
import com.miu.pasteit.security.JWTAuthorizationFilter;
import com.miu.pasteit.security.SecurityUtils;
import com.miu.pasteit.service.user.SessionService;
import com.miu.pasteit.service.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import static com.miu.pasteit.utils.UrlHelper.*;

/**
 * @author Samson Hailu
 * @author Rimon Mostafiz
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SessionService sessionService;

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.cors().and().csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.formLogin()
                .loginProcessingUrl(SecurityUtils.LOGIN_PRECESSING_URL)
                .and()
                .logout(logout -> logout
                        .logoutUrl(SecurityUtils.LOGOUT_URL)
                        .addLogoutHandler(sessionService)
                );

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityUtils.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityUtils.GET_PASTE_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), sessionService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), sessionService));

        http.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(all(API_DOCS),
                all(V2_API_DOCS),
                all(CONFIGURATION),
                CONFIGURATION_UI,
                all(WEB_JARS),
                all(SWAGGER_RESOURCES),
                SWAGGER_UI_HTML,
                all(SWAGGER_UI)
        );
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

