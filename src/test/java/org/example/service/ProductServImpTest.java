package org.example.service;

import org.example.entity.Product;
import org.example.repository.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class ProductServImpTest {

    @Autowired
    private ProductServ productServ;

    @MockBean
    private ProductRepo productRepo;

    @Test
    @DisplayName("Test findBy Id")
    void findById() {

        // Setup our mock
        Product mockProduct = new Product ( 1, "Product Name", 10,  1);
        doReturn(Optional.of(mockProduct)).when (productRepo).findById(1);

        // Execute the service call
        Optional <Product> returnedProduct = productServ.findById(1);

        // Assert the response
        Assertions.assertTrue (returnedProduct.isPresent(), "Product was not found");
        Assertions.assertSame (returnedProduct.get(),mockProduct, "Products should be the same");

    }

    @Test
    @DisplayName("Test findById not Found")
    void testFinfByIdNotFound(){
        //setup Mok
        Product mockProduct = new Product ( 1, "Product Name", 10,  1);
        doReturn(Optional.empty()).when (productRepo).findById(1);

        // Execute the service call
        Optional <Product> returnedProduct = productServ.findById(1);

        // Assert the response
        Assertions.assertSame (returnedProduct.get(),mockProduct, "Products was not found ");
    }


    @Test
    void findAll() {
        //setup Mok
        Product mockProduct = new Product ( 1, "Product Name", 10,  1);
        Product mockProduct2 = new Product ( 1, "Product Name 2", 15,  3);
        doReturn(Arrays.asList(mockProduct,mockProduct2)).when(productRepo).findAll();

        // Execute the service call
        List<Product> returnedProduct = productServ.findAll();

        // Assert the response
        Assertions.assertEquals (2, returnedProduct.size(), "FindAll should return 2 ");

    }

    @Test
    void save() {
        Product mockProduct = new Product ( 1, "Product Name", 10,  1);
        doReturn(mockProduct).when(productRepo).save(any());

        Product returnedProduct= productServ.save(mockProduct);

        Assertions.assertNotNull(returnedProduct,"The saved product should not be null");
        Assertions.assertEquals(returnedProduct.getVersion().intValue(),"The version for a new product should be 1");

    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void delete() {
    }
}