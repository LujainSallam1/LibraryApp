//package nl.first8.library.controller.config;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//public class webConfig extends WebSecurityConfigurerAdapter {
//
//    private static final String[] SWAGGER_WHITELIST = {
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            "/swagger-ui.html",
//    };
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .authorizeRequests()
//                .antMatchers(SWAGGER_WHITELIST).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//    }
//}
//
