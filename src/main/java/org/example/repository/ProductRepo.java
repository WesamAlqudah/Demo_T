package org.example.repository;

import org.example.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepo  {

    Optional<Product> findById(Integer id);

    List <Product> findAll();

    boolean update(Product product);

    Product save(Product product);

    boolean delete ( Integer id);

}
