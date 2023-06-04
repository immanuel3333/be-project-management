package com.group3.projectmanagementapi.customeruser;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.group3.projectmanagementapi.applicationuser.ApplicationUser;
import com.group3.projectmanagementapi.customeruser.exception.CustomeruserNotFoundException;
import com.group3.projectmanagementapi.customeruser.model.Customeruser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomeruserService {

    private final CustomeruserRepository customeruserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Customeruser findByUsername(String username) {
        return customeruserRepository.findByUsername(username);
    }

    public Customeruser createOne(Customeruser customeruser) {
        Customeruser isExists = this.customeruserRepository.findByUsernameOrEmail(customeruser.getUsername(),
                customeruser.getEmail());

        if (isExists != null) {
            return null;
        }

        ApplicationUser applicationUser = customeruser.getApplicationUser();
        String hashPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(hashPassword);

        return customeruserRepository.save(customeruser);
    }

    public Customeruser findOneById(Long id) {
        return this.customeruserRepository.findById(id).orElseThrow(() -> new CustomeruserNotFoundException());
    }

    public List<Customeruser> findAll() {
        return customeruserRepository.findAll();
    }

    public Customeruser login(String usernameOrEmail, String password) {
        Customeruser user = customeruserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (user != null) {
            return user;
        }

        return null;
    }

    public List<Customeruser> getCustomersNotInProject(Long projectId, Optional<String> optionalEmail) {
        if (optionalEmail.isPresent()) {
            return this.customeruserRepository.findUnassignedUsers(projectId, optionalEmail.get());
        }
        return this.customeruserRepository.findUnassignedUsers(projectId, optionalEmail.get());
    }

}
