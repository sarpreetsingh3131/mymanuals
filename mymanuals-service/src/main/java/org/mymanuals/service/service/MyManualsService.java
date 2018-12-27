package org.mymanuals.service.service;

import org.domain.converter.ProductConverter;
import org.domain.dto.ProductWithoutBadgeDto;
import org.domain.model.Manual;
import org.domain.model.Product;
import org.domain.repository.ProductRepository;
import org.domain.service.ManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyManualsService {

    @Autowired
    private ProductConverter converter;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManualService manualService;

    public List<ProductWithoutBadgeDto> findProductsByCategoryId(Long id) {
        return converter.toProductWithoutBadgeDto(productRepository.findByCategoryId(id));
    }

    public List<ProductWithoutBadgeDto> searchProducts(String query) {
        return converter.toProductWithoutBadgeDto(
                productRepository
                        .findTop10ByNameIgnoreCaseContainingOrModelIgnoreCaseContainingOrCategoryNameIgnoreCaseContaining(
                                query, query, query));

    }

    public List<ProductWithoutBadgeDto> find10LatestProducts() {
        return converter.toProductWithoutBadgeDto(productRepository.findTop10ByOrderByIdDesc());
    }

    public Manual updateManualViews(Long id) throws Exception {
        Manual manual = manualService.findById(id);
        manual.setViews(manual.getViews() + 1);
        return manualService.update(manual);
    }

    public ProductWithoutBadgeDto findProductWithoutBadge(Long id) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("no product with id = " + id));
        product.setViews(product.getViews() + 1);
        return converter.toProductWithoutBadgeDto(productRepository.save(product));
    }
}
