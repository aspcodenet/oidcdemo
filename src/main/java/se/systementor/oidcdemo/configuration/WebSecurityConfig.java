package se.systementor.oidcdemo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public WebSecurityConfig() {

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(c ->
                c.requestMatchers("/", "/oauth_login", "/js/**", "public/**", "/css/**", "/images/**", "/login/**", "/oauth2/**", "/error")
                        .permitAll()
                        .anyRequest().authenticated()
        ).oauth2Login(c -> c.loginPage("/oauth_login"));
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        return http.build();
    }

//    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
//        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
//                new OidcClientInitiatedLogoutSuccessHandler(
//                        this.clientRegistrationRepository);
//
//
//        var baseUrl = env.getProperty("schedule-app.base-url");
//
//        oidcLogoutSuccessHandler.setPostLogoutRedirectUri(baseUrl );
//
//        return oidcLogoutSuccessHandler;
//    }
}