package ru.job4j.forum.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Конфигурация SpringSecurity.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Кодировщик паролей, используемый при аутентификации.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Получение кодировщика паролей.
     *
     * @return объект кодировщика.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Конфигурация аунтентификации в приложении.
     *
     * @param auth объект для создания менеджера аутентификации.
     * @throws Exception исключения при создании менеджера аутентификации.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager()).passwordEncoder(this.passwordEncoder);
    }

    /**
     * Получение менеджера данных пользователей, хранящего информацию в памяти.
     *
     * @return объект менеджера данных пользователей.
     */
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails defaultUser = User.withUsername("user").password(passwordEncoder.encode("secret")).roles("USER").build();
        return new InMemoryUserDetailsManager(defaultUser);
    }

    /**
     * Конфигурация авторизации в приложении.
     *
     * @param http объект, хранящий настройки безопасности.
     * @throws Exception исключения при настройке объекта безопасности.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register", "/login", "/").permitAll()
                .antMatchers("/**").hasAnyRole("USER")
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true)
                .and()
                .csrf().disable();
    }
}
