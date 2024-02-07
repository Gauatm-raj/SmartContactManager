package com.smartcontact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Myconfig {

    @Bean
    public UserDetailsService getUserDetailService(){
        return new UserDetailServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.authorizeHttpRequests((authorizeHttpRequests)-> authorizeHttpRequests
                       .requestMatchers("/user/**").hasRole("Public")
                       .requestMatchers("/**").permitAll()

               ).formLogin(
                       (formlogin)->
               formlogin
                       .usernameParameter("username")
                       .passwordParameter("password")
                       .loginPage("/login")
                       .loginProcessingUrl("/dologin")
                       .defaultSuccessUrl("/user/index")

                         ).logout((logout) ->
               logout
//                       .deleteCookies("remove")
//                       .invalidateHttpSession(false)
//                       .logoutUrl("/custom-logout")
                       .logoutSuccessUrl("/login")
       );
        return http.build();

   }



}
