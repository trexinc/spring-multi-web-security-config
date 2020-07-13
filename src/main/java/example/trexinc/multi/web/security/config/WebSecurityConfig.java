package example.trexinc.multi.web.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
class WebSecurityConfig {

    @Profile("local")
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    @EnableWebSecurity
    public static class SecurityDisabledConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // allow all requests
            http.authorizeRequests().anyRequest().permitAll();
        }
    }

    @Profile("password")
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    @EnableWebSecurity
    public static class SecurityPasswordConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    // require authenticated access to /admin
                    .antMatchers("/admin", "/admin/**").authenticated()
                    // allow anonymous access to all other requests
                    .anyRequest().permitAll()
                    // set logout URL
                    .and().logout().logoutSuccessUrl("/")
                    // enable http basic authorization
                    .and().formLogin().and().httpBasic();
        }
    }

    @Profile("okta")
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    @EnableWebSecurity
    public static class SecurityOktaConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    // require authenticated access to /admin
                    .antMatchers("/admin", "/admin/**").authenticated()
                    // allow anonymous access to all other requests
                    .anyRequest().permitAll()
                    // set logout URL
                    .and().logout().logoutSuccessUrl("/")
                    // enable OAuth2/OIDC
                    .and().oauth2Client()
                    .and().oauth2Login();
        }
    }
}
