package com.projects.videolibrary.config;

import com.projects.videolibrary.auth.Authority;
import com.projects.videolibrary.auth.LoginAuthenticationProvider;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  String logInPage = "/log-in";
  String adminPage = "/admin-dashboard";


  private final DataSource dataSource;
  private final PasswordEncoder passwordEncoder;

  public SecurityConfiguration(DataSource dataSource,
      PasswordEncoder passwordEncoder) {
    this.dataSource = dataSource;
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  void configureGlobal(
      AuthenticationManagerBuilder auth,
      LoginAuthenticationProvider authenticationProvider
  ) throws Exception {

    auth.authenticationProvider(authenticationProvider)
        .jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder)
        .usersByUsernameQuery(
            "SELECT username, email, password, enabled FROM users WHERE username = ?")
        .authoritiesByUsernameQuery(
            "SELECT username, authority_id FROM authorities WHERE username = ?");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests()
        .mvcMatchers(logInPage, "/**")
        .permitAll()
        .mvcMatchers(adminPage)
        .hasAuthority(Authority.ADMIN.getNumVal().toString())
        .and()
        .formLogin()
        .loginPage(logInPage)
        .usernameParameter("user")
        .passwordParameter("password")
        .defaultSuccessUrl(adminPage, true)
        .failureUrl(logInPage + "?error")
        .permitAll()
        .and()
        .logout()
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .logoutUrl("/log-out")
        .logoutSuccessUrl("/log-in")
        .permitAll();

    return http.build();
  }

//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                var securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                var collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//
//        };
//        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
//        return tomcat;
//    }
//
//    private Connector getHttpConnector() {
//        var connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(8082);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
}