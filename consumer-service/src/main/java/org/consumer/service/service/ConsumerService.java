package org.consumer.service.service;

import org.consumer.service.dto.ConsumerDto;
import org.consumer.service.dto.SubscriptionDto;
import org.domain.dto.CredentialDto;
import org.domain.model.Consumer;
import org.domain.model.Product;
import org.domain.repository.ConsumerRepository;
import org.domain.utils.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerRepository repository;

    @Autowired
    private Authentication authentication;

    public Consumer signUp(ConsumerDto consumerDto) {
        return repository.save((new Consumer(
                consumerDto.getName(), consumerDto.getUsername(), consumerDto.getPassword(),
                consumerDto.getEmail(), false, new LinkedList<>()
        )));
    }

    public Consumer updateBadge(String token, Product product, Boolean badge) throws Exception {
        Consumer consumer = findByToken(token);
        if (badge) {
            consumer.getProducts().add(product);
        } else {
            consumer.getProducts().remove(product);
        }
        return repository.save(consumer);
    }

    public String login(CredentialDto credentialDto) throws Exception {
        return authentication.login(credentialDto, repository);
    }

    public Consumer findByToken(String token) throws Exception {
        String username = authentication.validateAuthorization(token, repository);
        return repository.findByUsername(username)
                .orElseThrow(() -> new Exception("No consumer with username = " + username));
    }

    public Boolean hasBadge(String token, Product product) throws Exception {
        return findByToken(token)
                .getProducts()
                .contains(product);
    }

    public Consumer findProfile(String token) throws Exception {
        return findByToken(token);
    }

    public Consumer subscription(String token, SubscriptionDto subscriptionDto) throws Exception {
        Consumer consumer = findByToken(token);
        consumer.setSubscription(subscriptionDto.getSubscription());
        return repository.save(consumer);
    }
}
