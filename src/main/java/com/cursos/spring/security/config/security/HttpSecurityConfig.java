package com.cursos.spring.security.config.security;

import com.cursos.spring.security.config.security.filter.JwtAuthenticationFilter;
import com.cursos.spring.security.util.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {

        return http
                .csrf( AbstractHttpConfigurer::disable )
                .sessionManagement( session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
                .authenticationProvider( authenticationProvider )
                .addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class )
                .authorizeHttpRequests( authConfig -> {

                    authConfig.requestMatchers( HttpMethod.POST, "/auth/authenticate" ).permitAll();
                    authConfig.requestMatchers( HttpMethod.GET, "/auth/public-access" ).permitAll();
                    authConfig.requestMatchers( "/h2-console/**" ).permitAll();
                    authConfig.requestMatchers( "/error" ).permitAll();

                    authConfig.requestMatchers( HttpMethod.GET, "/products" ).hasAuthority( Permission.READ_ALL_PRODUCTS.name() );
                    authConfig.requestMatchers( HttpMethod.POST, "/products" ).hasAuthority( Permission.SAVE_ONE_PRODUCT.name() );

                    authConfig.anyRequest().denyAll();

                } )
                .headers( h -> h.frameOptions( HeadersConfigurer.FrameOptionsConfig::sameOrigin ) )
                .build();

    }

}