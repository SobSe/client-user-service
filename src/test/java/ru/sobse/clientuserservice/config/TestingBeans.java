package ru.sobse.clientuserservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.sobse.clientuserservice.client.UsersRestClient;
import ru.sobse.clientuserservice.client.UsersRestClientImpl;

import static org.mockito.Mockito.mock;
@Configuration
public class TestingBeans {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return mock(ClientRegistrationRepository.class);
    }

    @Bean
    public OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository() {
        return mock(OAuth2AuthorizedClientRepository.class);
    }

    @Bean
    @Primary
    public UsersRestClientImpl testUsersRestClientImpl(
            @Value("${user-service.services.users.uri:http://localhost:54321}") String userServiceUri) {
        return new UsersRestClientImpl(RestClient.builder()
                .baseUrl(userServiceUri)
                .build());
    }

}
