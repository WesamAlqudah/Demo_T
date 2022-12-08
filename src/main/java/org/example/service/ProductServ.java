package org.example.service;

import org.example.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServ {

    Optional<Product> findById(Integer id);

    List<Product> findAll();

    Product save(Product product);

    boolean update(Product p);
    boolean delete(Integer id);
}
