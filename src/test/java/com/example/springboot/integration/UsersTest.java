package com.example.springboot.integration;

import com.example.springboot.User.dto.UserDTO;
import com.example.springboot.Auth.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UsersTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String jwt;
    private Gson gson = new Gson();

    @Before
    public void getJWT() throws Exception {
        // credentials for the user
        Login credentials = new Login();
        credentials.setEmail("admin@admin.com");
        credentials.setPassword("admin");
        // convert to json
        String json = gson.toJson(credentials);
        // make the request to get the token in the header
        MvcResult result = mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)) // pass the content as a String
                .andExpect(status().isOk())
                .andReturn();
        // get the token
        jwt = Objects.requireNonNull(result.getResponse()
                        .getHeader("Authorization"))
                .replace("Bearer ", "");
    }

    @Test
    public void testLoginAdmin() throws Exception {
        // credentials for the user
        Login anObject = new Login();
        anObject.setEmail("admin@admin.com");
        anObject.setPassword("admin");
        // convert to json
        Gson gson = new Gson();
        String json = gson.toJson(anObject);
        // make the request to get the token in the header
        MvcResult result = mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)) // pass the content as a String
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createUserAdmin() throws Exception {
        UserDTO newUser = new UserDTO();
        newUser.setEmail("angelgarces1248eee@gmail.com");
        newUser.setPassword("angel123");
        newUser.setFirstName("Angel");
        newUser.setLastName("Garces");
        newUser.setPhoneNumber("04141336220");
        ArrayList<Long> roleIds = new ArrayList<>();
        roleIds.add(1L);
        newUser.setRoles(roleIds);

        // ========= create user in the database =========

        // convert user to json
        String json = gson.toJson(newUser);
        // request
        MvcResult result = mockMvc.perform(
                        post("/api/users/create-user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + jwt)
                                .content(json) // pass the content as a String
                )
                .andExpect(status().isOk())
                .andReturn();
        // get the created user
        String responseBody = result.getResponse().getContentAsString(); // get response body as string
        UserDTO createdUser = objectMapper.readValue(responseBody, UserDTO.class); // convert response body to UserDTO object

        // ========= search for the user in the database =========

        // perform GET request to /api/users/{id}
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/users/{id}", createdUser.getId())
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // assert that returned user has the expected ID value
        String responseBodyGet = mvcResult.getResponse().getContentAsString();
        UserDTO actualUser = objectMapper.readValue(responseBodyGet, UserDTO.class);
        assertEquals(createdUser.getId(), actualUser.getId());

        // ========= delete the user in the database =========
        mockMvc.perform(
                        delete("/api/users/{id}", createdUser.getId())
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createCustomerAndLogin() throws Exception {
        UserDTO newUser = new UserDTO();
        newUser.setEmail("pepe123@gmail.com");
        newUser.setPassword("pepe123");
        newUser.setFirstName("Pepe");
        newUser.setLastName("Perez");
        newUser.setPhoneNumber("04141336220");
        ArrayList<Long> roleIds = new ArrayList<>();
        roleIds.add(1L);
        newUser.setRoles(roleIds);

        // ========= create customer in the database =========

        // convert user to json
        String json = gson.toJson(newUser);
        // request
        MvcResult result = mockMvc.perform(
                        post("/api/users/create-customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json) // pass the content as a String
                )
                .andExpect(status().isOk())
                .andReturn();
        // get the created user
        String responseBody = result.getResponse().getContentAsString(); // get response body as string
        UserDTO createdUser = objectMapper.readValue(responseBody, UserDTO.class); // convert response body to UserDTO object

        // ========= test login of customers =========

        // credentials for the user
        Login credentials = new Login();
        credentials.setEmail(newUser.getEmail());
        credentials.setPassword(newUser.getPassword());
        System.out.println(newUser.getEmail() + " "+newUser.getPassword());
        // convert to json
        json = gson.toJson(credentials);
        // make the request to get the token in the header
        mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)) // pass the content as a String
            .andExpect(status().isOk());

        // ========= delete the user in the database =========
        mockMvc.perform(
                        delete("/api/users/{id}", createdUser.getId())
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}
