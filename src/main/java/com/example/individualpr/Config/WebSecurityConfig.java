package com.example.individualpr.Config;

import org.jasypt.digest.StandardStringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.headers().cacheControl().disable();
        http
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error");
        http
                .authorizeRequests()
                .antMatchers("/orderSotr/add").not().authenticated()
                .antMatchers("/registration").permitAll()
                .and()
                .formLogin().usernameParameter("login").loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout();
        http.logout()
                .logoutUrl("/logit") // указываем url, по которому будет обрабатываться запрос выхода из приложения
                .logoutSuccessUrl("/login?logout") // указываем url, на который будет перенаправляться пользователь после успешного выхода
                .invalidateHttpSession(true) // удаляем HttpSession
                .deleteCookies("JSESSIONID"); // удаляем Cookie
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {// берет из базы данных
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select login, password, active from user where login=?")
                .authoritiesByUsernameQuery("select u.login, ur.roles from user u inner join user_role ur on u.id = ur.user_id" +
                        " where u.login=?");
    }
}