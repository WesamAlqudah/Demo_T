package org.example.service;


import org.example.entity.Product;
import org.example.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServImp implements ProductServ {

    private final ProductRepo productRepo;

    @Autowired
    public ProductServImp(ProductRepo productRepo){
        this.productRepo=productRepo;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepo.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public Product save(Product product) {
        product.setVersion(1);
        return productRepo.save(product);
    }

    @Override
    public boolean update(Product p) {

        return productRepo.update(p);
    }

    @Override
    public boolean delete(Integer id) {
        return productRepo.delete(id);
    }

}
