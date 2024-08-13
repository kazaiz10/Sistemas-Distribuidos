package com.example.jardinagem;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/registerCurso").hasRole("Admin")
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/index.html").permitAll()
                                .requestMatchers("/users").hasRole("Admin")
                                .requestMatchers("/admin").hasRole("Admin")
                                .requestMatchers("/professor/**").hasRole("Professor")
                                .requestMatchers("/aluno/**").hasRole("Aluno")
                                .requestMatchers("/remove/**").hasRole("Admin")
                                .requestMatchers("/update/**").hasRole("Admin")
                                .requestMatchers("/visualizar").hasAnyRole("Admin", "Professor", "Aluno")
                                .anyRequest().denyAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(new AuthenticationSuccessHandler() {
                                    @Override
                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                        List<String> RoleList = authentication.getAuthorities().stream().map(r -> r.getAuthority()).toList();
                                        String MainRile = RoleList.get(0);
                                        switch (MainRile) {
                                            case "ROLE_Admin":
                                                response.sendRedirect("/admin");
                                                break;
                                            case "ROLE_Professor":
                                                response.sendRedirect("/professor");
                                                break;
                                            case "ROLE_Aluno":
                                                response.sendRedirect("/aluno");
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                })
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}