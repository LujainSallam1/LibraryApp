package nl.first8.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Arrays;

@Configuration
public class SpringFoxConfig {

    private static final String CLIENT_ID = "backend";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("nl.first8.library.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    // Set security configuration, including default client ID (and secret later?)
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //Scheme to set where to get the tokens from (Keycloak token endpoint), and where to authorize the client (Keycloak authorization endpoint).
    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint("http://localhost:8080/realms/library/protocol/openid-connect/token", "oauthtoken")) //TODO: extract base url as AUTH_SERVER, also below
                .tokenRequestEndpoint(new TokenRequestEndpoint("http://localhost:8080/realms/library/protocol/openid-connect/auth", CLIENT_ID, null))
                .build();

        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
                .grantTypes(Arrays.asList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
        return oauth;
    }

    // authorization scopes, just using one. Not sure how it matters
    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("openid", "OpenID Connect scope, includes access to all endpoints.") };
        return scopes;
    }

    // References the previously defined SecurityScheme "spring_oauth".
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Arrays.asList(new SecurityReference("spring_oauth", scopes()))
                ).forPaths(PathSelectors.any())
                .build();
    }

}