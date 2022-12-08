package org.example.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.Product;
import org.example.service.ProductServImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductContr {

    private static final Logger logger = LogManager.getLogger(ProductContr.class);

    private final ProductServImp productServ;

    public ProductContr (ProductServImp productServ){

        this.productServ=productServ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id){

        return productServ.findById(id)
                .map(product ->{//**
                    try {
                        return  ResponseEntity
                                .ok()
                                .eTag(Integer.toString((product.getVersion())))
                                .location(new URI("/product/" + product.getID()))
                                .body(product);
                    } catch (URISyntaxException e) {
                         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                    })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public Iterable<Product> getProducts(){

         return productServ.findAll();
    }

    @PostMapping("/addproduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){

         logger.info("creating new product with name: {}, quantity:{}",product.getName(), product.getQuantity());

         Product newProduct = productServ.save(product);

            try{
                return ResponseEntity
                        .created(new URI("/product"+newProduct.getID()))
                        .eTag(Integer.toString(newProduct.getVersion()))
                        .body(newProduct);
            }catch (URISyntaxException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }

    @PutMapping("/product/{id}")
    public  ResponseEntity<?> updateProduct (@RequestBody Product product,
                                             @PathVariable Integer id,
                                             @RequestHeader ("If-Match") Integer ifMatch){

     logger.info("Updating product with id: {}, name: {}, quantity: {}", id, product.getName(), product.getQuantity());

            Optional<Product> existingProduct = productServ.findById(id);

            return existingProduct.map(p->{
                //compare eTags
                logger.info("Product with ID: "+ id + "has a version of " + p.getVersion()
                + ".Updating is for If-Match: " + ifMatch);
                if(!p.getVersion().equals(ifMatch)){
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }

                p.setName(product.getName());
                p.setQuantity(product.getQuantity());
                p.setVersion(p.getVersion()+1);

                logger.info("Updating product with ID: "+ p.getID()
                +"-> name "+ p.getName()
                + "quantity= "+  p.getQuantity()
                + ", Version=" + p.getVersion());

                try {
                    //update product and return ->OK
                    if(productServ.update(p)){
                        return ResponseEntity.ok()
                                .location(new URI("/product/"+ p.getID()))
                                .eTag(Integer.toString(p.getVersion()))
                                .body(p);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                } catch (URISyntaxException e){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            })
                    .orElse(ResponseEntity.notFound().build());

        }
    @DeleteMapping("/product/{id")
    public  ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        logger.info("Deleting product with ID{}",id);

        Optional<Product> exisitngProduct= productServ.findById(id);

        return exisitngProduct.map(p ->{
            if (productServ.delete(p.getID())){
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
