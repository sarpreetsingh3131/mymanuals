package org.product.service.controller;

import org.domain.model.Product;
import org.product.service.dao.BadgeDao;
import org.product.service.dao.ProductDao;
import org.product.service.dto.ProductDto;
import org.product.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public class ProductController {

    @Autowired
    Environment environment;
    @Autowired
    private ProductService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product save(@RequestBody @Valid ProductDao productDao,
                        @RequestHeader("Authorization") @NotBlank String token) throws Exception {
        return service.save(productDao, token);
    }

    @GetMapping(value = "/category/{id}")
    public List<Product> findByCategoryId(@PathVariable @NotNull Long id) {
        return service.findByCategoryId(id);
    }

    @GetMapping
    public List<Product> search(@RequestParam("search") @NotBlank String query) {
        return service.search(query);
    }

    @GetMapping(value = "/{id}")
    public ProductDto findById(@RequestHeader(value = "Authorization", required = false, defaultValue = "") String token,
                               @PathVariable @NotNull Long id) throws Exception {
        return service.findById(id, token);
    }

    @GetMapping(value = "/latest")
    public List<Product> findLatest() {
        return service.findLatest();
    }

    @DeleteMapping("/image/{id}")
    public Product deleteImageById(@PathVariable @NotNull Long id,
                                   @RequestHeader("Authorization") String token) throws Exception {
        return service.deleteImageById(id, token);
    }

    @DeleteMapping("/manual/{id}")
    public Product deleteManualById(@PathVariable @NotNull Long id,
                                    @RequestHeader("Authorization") String token) throws Exception {
        return service.deleteManualById(id, token);
    }

    @PostMapping(value = "/badge", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto badge(@RequestBody @Valid BadgeDao badgeDao,
                            @RequestHeader("Authorization") @NotBlank String token) throws Exception {
        return service.badge(badgeDao, token);
    }
}
