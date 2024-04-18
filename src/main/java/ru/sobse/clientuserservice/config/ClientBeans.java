package ru.sobse.clientuserservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.sobse.clientuserservice.client.UsersRestClientImpl;
import ru.sobse.clientuserservice.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class ClientBeans {
    @Bean
    public UsersRestClientImpl productsRestClient(
            @Value("${user-service.services.users.uri:http://localhost:8081}") String userServiceUri,
            @Value("${user-service.services.users.registrationId:user-service-app}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        return new UsersRestClientImpl(RestClient.builder()
                .baseUrl(userServiceUri)
                .requestInterceptor(new OAuthClientHttpRequestInterceptor(
                        new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository)
                        , registrationId))
                .build());
    }
}
