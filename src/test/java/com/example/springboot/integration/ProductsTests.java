package com.example.springboot.integration;

import com.example.springboot.Product.dtos.ProductDTO;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductsTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String jwt;
    private Gson gson = new Gson();

    // get the JWT to all the request
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
    public void createProduct() throws Exception {
        // credentials for the user
        ProductDTO product = new ProductDTO();
        product.setName("pepsi");
        product.setPrice(20L);
        product.setTaxPercentage(10);
        product.setQuantity(0L);
        product.setDescription("A pepsi to have a better day (?)");
        product.setPhotoUrlSmall("asdsdfadf");
        product.setPhotoUrlMedium("sdfasdf");
        product.setPhotoUrlBig("sdfsdf");
        product.setAmount("12x3");
        product.setWeight(BigDecimal.valueOf(123.22));
        product.setHeight(BigDecimal.valueOf(123.33));
        product.setCategories(Collections.emptyList());
        // make the request to get the token in the header
        MvcResult result = mockMvc.perform(
                        post("/api/products/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + jwt)
                                .content(gson.toJson(product))) // pass the content as a String
                .andExpect(status().isCreated())
                .andReturn();
        // get the created product
        String responseBody = result.getResponse().getContentAsString(); // get response body as string
        ProductDTO createdProduct = objectMapper.readValue(responseBody, ProductDTO.class); // convert response body to ProductDTO object

        // ========= search for the user in the database =========

        // perform GET request to /api/users/{id}
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/products/{id}", createdProduct.getId())
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // assert that returned user has the expected ID value
        String responseBodyGet = mvcResult.getResponse().getContentAsString();
        ProductDTO actualUser = objectMapper.readValue(responseBodyGet, ProductDTO.class);
        assertEquals(createdProduct, actualUser);

        // ========= delete the product in the database =========
        mockMvc.perform(
                        delete("/api/products/{id}", createdProduct.getId())
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
