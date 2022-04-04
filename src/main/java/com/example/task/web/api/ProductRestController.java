package com.example.task.web.api;
import com.example.task.model.binding.ProductBindingModel;
import com.example.task.model.binding.ProductChangeBindingModel;
import com.example.task.model.view.ProductViewModel;
import com.example.task.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductViewModel>> listProducts() {

        return ResponseEntity.ok(this.productService.getAllProducts());

    }


    @PostMapping("/product")
    public ResponseEntity<ProductViewModel> addProduct(
            @RequestBody @Valid ProductBindingModel newProduct
    ) {

        ProductViewModel createdProduct = this.productService.addProduct(newProduct);


        URI newProductUri = URI.create(String.format("/api/product/%d", createdProduct.getId()));

        return ResponseEntity.created(newProductUri).body(createdProduct);

    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductViewModel> getProduct(@PathVariable Long productId) {

        ProductViewModel product = this.productService.getProductById(productId);

        return ResponseEntity.ok(product);
    }

    @PutMapping("/product")
    public ResponseEntity<ProductViewModel> updateProduct(@RequestBody @Valid ProductChangeBindingModel product) {


        ProductViewModel changedProduct = this.productService.changeProduct(product);
        return ResponseEntity.ok(changedProduct);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){

       String message = this.productService.deleteProduct(productId);

        return ResponseEntity.ok(message);
    }


}
