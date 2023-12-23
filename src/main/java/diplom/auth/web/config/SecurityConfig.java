package diplom.auth.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import diplom.auth.web.jwt.JWTAuthenticationFilter;
import diplom.auth.web.jwt.JWTLoginFilter;
import diplom.auth.business.services.DefaultProfileService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DefaultProfileService profileService;

    @Autowired
    public SecurityConfig(DefaultProfileService userDetailsManager) {
        this.profileService =userDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests()
                .antMatchers("/profile").permitAll() //Возврат страници авторизации
                .antMatchers( "/swagger-ui.html",
                        "/swagger-ui.html/**",
                        "/swagger-ui",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**").permitAll()
                .antMatchers( "/api/profile/login").permitAll()
                .antMatchers("/api/profile/create").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(profileService);
    }
}
