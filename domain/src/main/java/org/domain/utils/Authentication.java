package org.domain.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.domain.dao.CredentialDao;
import org.domain.repository.CompanyRepository;
import org.domain.repository.ConsumerRepository;
import org.domain.repository.RepresentativeRepository;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.UUID;

@Service
public class Authentication {

    private final byte[] SECRET = UUID.randomUUID().toString().getBytes();

    public String login(CredentialDao credentialDao, Object repository) throws Exception {
        if (repository instanceof RepresentativeRepository) {
            ((RepresentativeRepository) repository)
                    .findByUsernameAndPassword(credentialDao.getUsername(), credentialDao.getPassword())
                    .orElseThrow(() -> new Exception("Invalid credentials"));
        } else if (repository instanceof ConsumerRepository) {
            ((ConsumerRepository) repository)
                    .findByUsernameAndPassword(credentialDao.getUsername(), credentialDao.getPassword())
                    .orElseThrow(() -> new Exception("Invalid credentials"));
        } else if (repository instanceof CompanyRepository) {
            ((CompanyRepository) repository)
                    .findByUsernameAndPassword(credentialDao.getUsername(), credentialDao.getPassword())
                    .orElseThrow(() -> new Exception("Invalid credentials"));
        }
        return assignToken(credentialDao);
    }

    public String validateAuthorization(String token, Object repository) throws Exception {
        String username = parseToken(token);
        if (repository instanceof RepresentativeRepository) {
            ((RepresentativeRepository) repository)
                    .findByUsername(username)
                    .orElseThrow(() -> new Exception("Invalid token"));
        } else if (repository instanceof ConsumerRepository) {
            ((ConsumerRepository) repository)
                    .findByUsername(username)
                    .orElseThrow(() -> new Exception("Invalid token"));
        } else if (repository instanceof CompanyRepository) {
            ((CompanyRepository) repository)
                    .findByUsername(username)
                    .orElseThrow(() -> new Exception("Invalid token"));
        }
        return username;
    }

    private String assignToken(CredentialDao credentialDao) {
        return "{\"token\": \"" +
                Jwts.builder()
                        .setId(credentialDao.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setSubject("my-manuals")
                        .setIssuer("my-manuals")
                        .signWith(new SecretKeySpec(SECRET, SignatureAlgorithm.HS256.getJcaName()), SignatureAlgorithm.HS256)
                        .setExpiration(new Date(System.currentTimeMillis() * 1000))
                        .compact()
                + "\"}";
    }

    private String parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getId();
    }
}
