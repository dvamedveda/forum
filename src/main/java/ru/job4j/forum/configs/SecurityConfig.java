package ru.job4j.forum.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

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
     * Источник данных.
     */
    private DataSource dataSource;

    public SecurityConfig(@Qualifier("mainDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Конфигурация аунтентификации в приложении.
     *
     * @param auth объект для создания менеджера аутентификации.
     * @throws Exception исключения при создании менеджера аутентификации.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(this.passwordEncoder)
                .usersByUsernameQuery(
                        "select login, password, enabled "
                                + "from forum_users "
                                + "where login = ?")
                .authoritiesByUsernameQuery(
                        "select u.login, a.authority "
                                + "from forum_users u join authorities a on u.authority_id = a.id "
                                + "where u.login = ?"
                );
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
