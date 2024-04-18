package ru.sobse.clientuserservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sobse.clientuserservice.DTO.UserDto;
import ru.sobse.clientuserservice.client.UsersRestClient;

import java.util.Arrays;
import java.util.List;

public class ControllerTest {
    public UsersRestClient restClient;

    @BeforeEach
    public void beforeEach() {
        this.restClient = Mockito.mock(UsersRestClient.class);
    }

    @Test
    public void getUsersTest() {
        //arrange
        List<UserDto> listOfUsers = Arrays.asList(
                new UserDto("admin"),
                new UserDto("user")
        );
        Controller controller = new Controller(this.restClient);
        Mockito.when(restClient.getUsers()).thenReturn(listOfUsers);
        //act
        List<UserDto> actualListOfUsers = controller.getUsers();
        //assert
        Assertions.assertEquals(listOfUsers, actualListOfUsers);
    }
}
