package ru.sobse.clientuserservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import ru.sobse.clientuserservice.DTO.UserDto;

import java.util.List;

@RequiredArgsConstructor
public class UsersRestClientImpl implements UsersRestClient{
    private final RestClient restClient;

    @Override
    public List<UserDto> getUsers() {
        return restClient
                .get()
                .uri("/admin/get-users")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
