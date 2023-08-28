package nl.first8.library.controller.config;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class JWTSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authz -> authz.antMatchers(HttpMethod.GET, "/api/v1/members")
                        .hasRole("EMPLOYEE")
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return http.build();
    }

    // To be able to properly read the roles from Keycloak
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {

            Set<GrantedAuthority> authorities = new HashSet<>();
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");

            // To add ROLE_ to every keycloak role
            SimpleAuthorityMapper sam = new SimpleAuthorityMapper();
            sam.setPrefix("ROLE_");
            sam.setConvertToUpperCase(true);

            if (realmAccess != null) {
                Collection<String> roles = realmAccess.get("roles");
                authorities.addAll(roles.stream()
                        .map(SimpleGrantedAuthority::new).toList());
            }
            String clientId = jwt.getClaim("azp");
            Map<String, Collection<Object>> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess != null) {
                JSONObject client = (JSONObject) resourceAccess.get(clientId);
                if(client != null){
                    Collection<String> roles = (Collection<String>) client.get("roles");
                    authorities.addAll(roles.stream().map(SimpleGrantedAuthority::new).toList());
                }

            }

            return sam.mapAuthorities(authorities);
        };

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        jwtAuthenticationConverter.setPrincipalClaimName("preferred_username");

        return jwtAuthenticationConverter;
    }
}
