package org.adaschool.api.controller.product;

import org.adaschool.api.repository.product.Product;
import org.adaschool.api.repository.product.ProductDto;
import org.adaschool.api.service.product.ProductsService;
import org.adaschool.api.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/products/")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(@Autowired ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        //TODO implement this method
        URI createdProductUri = URI.create("");
        return ResponseEntity.created(createdProductUri).body(productsService.save(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        //TODO implement this method
        return ResponseEntity.ok(productsService.all());
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<Product>(productsService.findById(id).get(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") String id) {
        Product product = new Product(productDto);
        Optional<Product> productoExiste = productsService.findById(id);
        if (!productoExiste.isEmpty()) {
            productsService.update(product, id);
            productsService.save(productoExiste.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new ProductNotFoundException(id);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable("id") String id) {
        Optional<Product> productoExiste = productsService.findById(id);
        if (!productoExiste.isEmpty()) {
            productsService.deleteById(id);

        }else{
            throw new ProductNotFoundException(id);
        }
    }
}
