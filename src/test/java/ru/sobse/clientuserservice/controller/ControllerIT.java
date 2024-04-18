package ru.sobse.clientuserservice.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockTest(httpPort = 54321)
public class ControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getUsersReturnsUsersList() throws Exception{
        //arrange
        var requestBuilder = MockMvcRequestBuilders.get("/admin/get-users")
                .with(user("admin").roles("ADMIN"));

        WireMock.stubFor(WireMock.get("/admin/get-users")
                .willReturn(WireMock.okJson("""
                        [
                            {
                                "name": "аdmin"
                            },
                            {
                                "name": "user"
                            }
                        ]
                        """)));

        //act
        this.mockMvc.perform(requestBuilder)
                //assert
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                        [
                            {
                                "name": "аdmin"
                            },
                            {
                                "name": "user"
                            }
                        ]
                        """)
                );
    }

    @Test
    public void getUsersUserNotAuthorized() throws Exception{
        //arrange
        var requestBuilder = MockMvcRequestBuilders.get("/admin/get-users")
                .with(user("admin").roles("USER"));
        //act
        this.mockMvc.perform(requestBuilder)
                //assert
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
