package com.group3.projectmanagementapi.customeruser;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group3.projectmanagementapi.customeruser.model.Customeruser;
import com.group3.projectmanagementapi.customeruser.model.dto.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomeruserController {

    private final CustomeruserService customeruserService;

    @PostMapping("/register")
    public ResponseEntity<CustomeruserCustomeResponse> register(
            @Valid @RequestBody CustomeruserRegisterRequest customeruserRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().get(0).getDefaultMessage();

            CustomeruserCustomeResponse response = new CustomeruserCustomeResponse(400, errors, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Customeruser existingUser = customeruserService.createOne(customeruserRequest.convertToEntity());

        if (existingUser != null) {
            CustomeruserCustomeResponse response = new CustomeruserCustomeResponse(201, "Registration successful",
                    existingUser.convertToResponse());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomeruserCustomeResponse response = new CustomeruserCustomeResponse(400, "Username already exists",
                null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomeruserCustomeResponse> login(
            @Valid @RequestBody CustomeruserLoginRequest loginRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().get(0).getDefaultMessage();

            CustomeruserCustomeResponse response = new CustomeruserCustomeResponse(400, errors, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Customeruser user = customeruserService.login(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());

        if (user != null) {
            CustomeruserCustomeResponse response = new CustomeruserCustomeResponse(200, "Login successful",
                    user.convertToResponse());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomeruserCustomeResponse response = new CustomeruserCustomeResponse(400, "Invalid username or password",
                null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/customerusers")
    public ResponseEntity<List<CustomeruserResponse>> findAll() {
        List<Customeruser> customerusers = this.customeruserService.findAll();
        List<CustomeruserResponse> customeruserResponses = customerusers.stream().map(c -> c.convertToResponse())
                .toList();

        return ResponseEntity.status(200).body(customeruserResponses);
    }

    @GetMapping("/customerusers/{id}")
    public ResponseEntity<CustomeruserResponse> findById(@PathVariable("id") Long id) {
        Customeruser customeruser = this.customeruserService.findOneById(id);
        CustomeruserResponse response = customeruser.convertToResponse();

        return ResponseEntity.status(200).body(response);
    }
}
