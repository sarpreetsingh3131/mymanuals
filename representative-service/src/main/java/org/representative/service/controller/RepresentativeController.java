package org.representative.service.controller;

import org.domain.dto.*;
import org.domain.model.ServiceProvider;
import org.representative.service.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public class RepresentativeController {

    @Autowired
    private RepresentativeService service;

    @PutMapping(value = "/api/representative/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String logIn(@RequestBody @Valid CredentialDto credentialDto) throws Exception {
        return service.logIn(credentialDto);
    }

    @PostMapping(value = "/api/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductWithoutBadgeDto saveProduct(@RequestBody @Valid CreateProductDto createProductDto,
                                              @RequestHeader("Authorization") @NotBlank String token) throws Exception {
        return service.saveProduct(createProductDto, token);
    }

    @DeleteMapping(value = "/api/product/image/{id}")
    public ProductWithoutBadgeDto deleteImageById(@PathVariable @NotNull Long id,
                                                  @RequestHeader("Authorization") @NotBlank String token) throws Exception {
        return service.deleteImageById(id, token);
    }

    @DeleteMapping(value = "/api/product/manual/{id}")
    public ProductWithoutBadgeDto deleteManualById(@PathVariable @NotNull Long id,
                                                   @RequestHeader("Authorization") @NotBlank String token) throws Exception {
        return service.deleteManualById(id, token);
    }

    @GetMapping(value = "/api/product/selection")
    public List<ProductWithSelectionDto> findProductsWithSelection(@RequestHeader("Authorization")
                                                                   @NotBlank String token) throws Exception {
        return service.findProductsWithSelection(token);
    }

    @PutMapping(value = "/api/serviceprovider/authorization", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ServiceProvider updateServiceProviderAuthorization(@RequestBody @Valid ServiceProviderAuthorizationDto
                                                                      serviceProviderAuthorizationDto,
                                                              @RequestHeader("Authorization")
                                                              @NotBlank String token) throws Exception {
        return service.updateServiceProviderAuthorization(serviceProviderAuthorizationDto, token);
    }

    @PostMapping(value = "api/representative/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String sendEmail(@RequestBody @Valid EmailDto emailDto,@RequestHeader("Authorization") @NotBlank String token) throws Exception {
        return service.sendEmailToConsumers(token, emailDto.getSubject(), emailDto.getBody());
    }
}
