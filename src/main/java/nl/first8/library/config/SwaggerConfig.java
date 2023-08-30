package nl.first8.library.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Configuration
@EnableSwagger2
@SpringBootApplication
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("nl.first8.library.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My API")
                .description("My API Documentation")
                .version("1.0")
                .build();
    }

    private SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("OAuth2")
                .grantTypes(grantTypes())
                .scopes(Collections.singletonList(openidScope()))

                .build();
    }

    private List<GrantType> grantTypes() {
        GrantType authorizationCode = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpointBuilder()
                        .url("http://localhost:8080/realms/libraryapp/protocol/openid-connect/token")
                        .build())
                .tokenRequestEndpoint(new TokenRequestEndpointBuilder()
                        .url("http://localhost:8080/realms/libraryapp/protocol/openid-connect/auth").
                        clientIdName("frontend")
                        .clientSecretName("uE3FLCoOpZyKcjFdNiga2VuiWX2Pfv7I")
                        .build())
                .build();

        return Collections.singletonList(authorizationCode);
    }

    private AuthorizationScope openidScope() {
        return new AuthorizationScope("openid", "OpenID Connect Scope");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference("OAuth2", new AuthorizationScope[]{openidScope()})))
                .forPaths(PathSelectors.any())
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


