package org.ad.agent.service.service;

import org.domain.dto.CredentialDto;
import org.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdAgentService {

    @Autowired
    private UserService userService;

    public String logIn(CredentialDto credentialDto) throws Exception {
        return userService.logInAsAdAgent(credentialDto);
    }

    public String logOut(String token) throws Exception {
        return userService.logOutAsAdAgent(token);
    }
}
