package ru.sobse.clientuserservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sobse.clientuserservice.DTO.UserDto;
import ru.sobse.clientuserservice.client.UsersRestClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final UsersRestClient restClient;

    @GetMapping("admin/get-users")
    public List<UserDto> getUsers() {
        return this.restClient.getUsers();
    }
}
