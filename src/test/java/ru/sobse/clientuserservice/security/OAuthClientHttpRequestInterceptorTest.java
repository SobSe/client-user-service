package ru.sobse.clientuserservice.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.mock;

class OAuthClientHttpRequestInterceptorTest {

    @Test
    public void interceptTest() throws IOException {
        //arrange
        OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager = mock(OAuth2AuthorizedClientManager.class);
        OAuthClientHttpRequestInterceptor interceptor = new OAuthClientHttpRequestInterceptor(
                oAuth2AuthorizedClientManager,
                "test");

        var request = new MockClientHttpRequest();
        var body = new byte[0];
        var execution = mock(ClientHttpRequestExecution.class);
        var response = new MockClientHttpResponse();
        var authentication = new TestingAuthenticationToken("admin", "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var authorizedClient = new OAuth2AuthorizedClient(mock(), "admin",
                new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", Instant.now(), Instant.MAX));

        Mockito.when(oAuth2AuthorizedClientManager
                        .authorize(Mockito.argThat(
                                authRequest -> authRequest.getPrincipal().equals(authentication) &&
                                    authRequest.getClientRegistrationId().equals("test"))
                        )
                )
                .thenReturn(authorizedClient);

        Mockito.when(execution.execute(request, body)).thenReturn(response);
        //act
        var actualResponse = interceptor.intercept(request, body, execution);
        //assert
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals("Bearer token", request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }
}