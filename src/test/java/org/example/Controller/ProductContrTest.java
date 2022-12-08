package org.example.Controller;

import org.example.entity.Product;
import org.example.service.ProductServ;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.nio.file.Files.delete;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ProductContrTest {

    @MockBean
    private ProductServ productServ;

    @Autowired
    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//    }

    @Test
    @DisplayName("GET / product/1 - Found")
    void getProduct() throws Exception {
        //setup out mocked service
        Product mockProduct = new Product(1, "Product Name", 10, 1);
        doReturn(Optional.of(mockProduct)).when(productServ).findById(1);

        //Execute the GET request
        mockMvc.perform(get("/product/{id}", 1))

                .andExpect(status().ok()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                //validate headers
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\"")).andExpect(header().string(HttpHeaders.LOCATION, "/product/1"))

                //validate headers

                .andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Product Name"))).andExpect(jsonPath("$.quantity", is(10))).andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("POST /Product/1 - Not Found")
    void getProducts() throws Exception {

        // Setup our mocked service
        doReturn(Optional.empty()).when(productServ).findById(1);
        // Execute the GET request
        mockMvc.perform(get("/product/{id}", 1))

                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /product- Success")
    void createProduct() {

        // Setup mocked service
        Product postProduct = new Product("Product Name", 10);
        Product mockProduct = new Product(1, "Product Name", 10, 1);
        doReturn(mockProduct).when(productServ).save(any());

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(asJsonString(postProduct)))

                // Validate the response code and content type
                .andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\"")).andExpect(header().string(HttpHeaders.LOCATION, "/product/1"))
                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Product Name"))).andExpect(jsonPath("$.quantity", is(10))).andExpect(jsonPath("$.version", is(1)));

    }

    @Test
    @DisplayName("PUT /Product/1 - Success")
    void updateProduct() {

        // Setup mocked service
        Product putProduct = new Product("Product Name", 10);
        Product mockProduct = new Product(1, "Product Name", 10, 1);
        doReturn(Optional.of(mockProduct)).when(productServ).findById(1);
        doReturn(true).when(productServ).update(any());

        mockMvc.perform(put("/product/{id}", 1).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.IF_MATCH, 1).content(asJsonString(putProduct)))

                // Validate the response code and content type
                .andExpect(status().is0k()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.ETAG, "\"2\"")).andExpect(header().string(HttpHeaders.LOCATION, "/product/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Product Name"))).andExpect(jsonPath("$.quantity", is(10))).andExpect(jsonPath("$.version", is(2)));

    }

    @Test
    @DisplayName("PUT /product/1 - Version Mismatch")
    void testProductPutVersionMismatch() throws Exception {
        // Setup mocked service
        Product putProduct = new Product("Product Name", 10);
        Product mockProduct = new Product(1, "Product Name", 10, 2);
        doReturn(Optional.of(mockProduct)).when(productServ).findById(1);
        doReturn(true).when(productServ).update(any());

        mockMvc.perform(put("/product/{id}", 1).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.IF_MATCH, 1).content(asJsonString(putProduct)))


                // Validate the response code and content type
                .andExpect(status().isConflict());

    }

    @Test
    @DisplayName("DELETE /product/1 Success")
    void testProductDeleteSuccess() throws Exception {
        //Setup mocked product
        Product mockProduct = new Product(1, "Product Name", 10, 1);
        // Setup the mocked service
        doReturn(Optional.of(mockProduct)).when(productServ).findById(1);
        doReturn(true).when(productServ).delete(1);
        // Execute our DELETE request
        mockMvc.perform(delete("/product/{id}", 1))
                .andExpect(status().is0k());
    }

    @Test
    @DisplayName("DELETE /product/1 - Not Found")
    void testProductDeleteNotFound() throws Exception {
        // Setup the mocked service
        doReturn(Optional.empty()).when(productServ).findById(1);
        // Execute our DELETE request
        mockMvc.perform(delete("/product/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /product/1 - Failure")
    void testProductDeleteFailure() throws Exception {
        // Setup mocked product
        Product mockProduct = new Product(1, "Product Name", 10, 1);
    }
}