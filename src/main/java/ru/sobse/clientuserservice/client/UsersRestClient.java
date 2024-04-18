package ru.sobse.clientuserservice.client;

import ru.sobse.clientuserservice.DTO.UserDto;

import java.util.List;

public interface UsersRestClient {
    public List<UserDto> getUsers();
}
