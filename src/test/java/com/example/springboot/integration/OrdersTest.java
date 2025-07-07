package com.example.springboot.integration;


import com.example.springboot.Order.dtos.OrderDTO;
import com.example.springboot.Order.dtos.OrderItemDTO;
import com.example.springboot.Order.dtos.PaymentMethodDTO;
import com.example.springboot.Product.dtos.ProductDTO;
import com.example.springboot.User.dto.AddressDTO;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrdersTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String jwt;
    private Gson gson = new Gson();

    // products to be bought
    List<ProductDTO> productsInMarket = new ArrayList<>(Collections.emptyList());

    @Before
    public void getJWT() throws Exception {
        // ========= GET JWT of the admin =========

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

        // ========= Create products to bought =========

        // credentials for the user
        ProductDTO product = new ProductDTO();
        product.setName("pepsi");
        product.setPrice(20L);
        product.setTaxPercentage(10);
        product.setQuantity(10L);
        product.setDescription("A pepsi to have a better day (?)");
        product.setPhotoUrlSmall("asdsdfadf");
        product.setPhotoUrlMedium("sdfasdf");
        product.setPhotoUrlBig("sdfsdf");
        product.setAmount("12x3");
        product.setWeight(BigDecimal.valueOf(123.22));
        product.setHeight(BigDecimal.valueOf(123.33));
        product.setCategories(Collections.emptyList());
        // make the request to get the token in the header
        for (int i = 0; i < 5; i++) {
            result = mockMvc.perform(
                            post("/api/products/create")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header("Authorization", "Bearer " + jwt)
                                    .content(gson.toJson(product))) // pass the content as a String
                    .andExpect(status().isCreated())
                    .andReturn();
            // get the created product
            String responseBody = result.getResponse().getContentAsString(); // get response body as string
            ProductDTO createdProduct = objectMapper.readValue(responseBody, ProductDTO.class); // convert response body to ProductDTO object
            productsInMarket.add(createdProduct);
        }
    }

    @Test
    public void createUserAndOrder() throws Exception {
        UserDTO newUser = new UserDTO();
        newUser.setEmail("joseJose3@gmail.com");
        newUser.setPassword("jose123");
        newUser.setFirstName("Jose");
        newUser.setLastName("Jose");
        newUser.setPhoneNumber("04141336220");
        ArrayList<Long> roleIds = new ArrayList<>();
        roleIds.add(1L);
        newUser.setRoles(roleIds);

        // ========= create user in the database =========

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

        // ========= get the jwt of the user =========

        // credentials for the user
        Login credentials = new Login();
        credentials.setEmail(newUser.getEmail());
        credentials.setPassword(newUser.getPassword());
        // convert to json
        // make the request to get the token in the header
        result = mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(credentials))) // pass the content as a String
                .andExpect(status().isOk())
                .andReturn();
        // get the token
        String jwtCustomer = Objects.requireNonNull(result.getResponse()
                        .getHeader("Authorization"))
                .replace("Bearer ", "");

        // ========= create address =========
        AddressDTO newAddress = new AddressDTO();
        newAddress.setName("casa");
        newAddress.setCity("Caracas");
        newAddress.setCountry("Venezuela");
        newAddress.setLatitude(213.0);
        newAddress.setLongitude(21231.2);

        result = mockMvc.perform(
                        post("/api/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + jwtCustomer)
                                .content(gson.toJson(newAddress))) // pass the content as a String
                .andExpect(status().isOk())
                .andReturn();
        responseBody = result.getResponse().getContentAsString(); // get response body as string
        AddressDTO createdAddress = objectMapper.readValue(responseBody, AddressDTO.class);

        // ========= create order =========

        OrderDTO order = new OrderDTO();
        order.setAddressId(createdAddress.getId());
        order.setUserId(createdUser.getId());
        order.setStatusCode(0);

        List<OrderItemDTO> orderItemDTOS = new ArrayList<>(Collections.emptyList());
        for (ProductDTO product : productsInMarket) {
            OrderItemDTO item = new OrderItemDTO();
            item.setQuantity(1);
            item.setProductId(product.getId());
            item.setPurchasePrice(BigDecimal.valueOf(product.getPrice()));
            orderItemDTOS.add(item);
        }
        order.setOrderItems(orderItemDTOS);

        List<PaymentMethodDTO> paymentMethodDTOS = new ArrayList<>(Collections.emptyList());
        PaymentMethodDTO payment = new PaymentMethodDTO();
        payment.setAmount(100L);
        payment.setType("cash");
        payment.setCurrency("BS");
        payment.setPaymentUserId("27123123");
        payment.setIsConfirmed(false);
        paymentMethodDTOS.add(payment);
        order.setPaymentMethods(paymentMethodDTOS);

        mockMvc.perform(
                        post("/api/orders/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + jwtCustomer)
                                .content(gson.toJson(order))) // pass the content as a String
                .andExpect(status().isOk())
                .andReturn();

        // ========= delete the user in the database =========

        mockMvc.perform(
                        delete("/api/users/{id}", createdUser.getId())
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}
