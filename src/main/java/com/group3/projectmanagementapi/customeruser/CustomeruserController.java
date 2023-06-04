package com.group3.projectmanagementapi.customeruser;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.group3.projectmanagementapi.customeruser.model.Customeruser;
import com.group3.projectmanagementapi.customeruser.model.Image;
import com.group3.projectmanagementapi.customeruser.model.dto.CustomeruserCustomeResponse;
import com.group3.projectmanagementapi.customeruser.model.dto.CustomeruserRegisterRequest;
import com.group3.projectmanagementapi.customeruser.model.dto.CustomeruserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomeruserController {

        private final CustomeruserService customeruserService;
        private final ImageService imageService;

        @GetMapping("/customerusers")
        public ResponseEntity<List<CustomeruserResponse>> findAll() {
                List<Customeruser> customerusers = this.customeruserService.findAll();
                List<CustomeruserResponse> customeruserResponses = customerusers.stream()
                                .map(c -> c.convertToResponse())
                                .toList();

                return ResponseEntity.status(200).body(customeruserResponses);
        }

        @GetMapping("/customerusers/{id}")
        public ResponseEntity<CustomeruserResponse> findById(@PathVariable("id") Long id) {
                Customeruser customeruser = this.customeruserService.findOneById(id);
                CustomeruserResponse response = customeruser.convertToResponse();

                return ResponseEntity.status(200).body(response);
        }

        @PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE,
                        MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<CustomeruserCustomeResponse> createOneWithImage(
                        @Valid @RequestPart("customeruserRequest") CustomeruserRegisterRequest customeruserRegisterRequest,
                        @RequestPart("image") MultipartFile imageFile, BindingResult bindingResult) {

                if (bindingResult.hasErrors()) {
                        String errors = bindingResult.getAllErrors().get(0).getDefaultMessage();

                        CustomeruserCustomeResponse response = new CustomeruserCustomeResponse(400, errors, null);
                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                String fileName = Long.toString(System.currentTimeMillis())
                                + imageFile.getOriginalFilename()
                                                .substring(imageFile.getOriginalFilename().lastIndexOf("."));

                String url = this.imageService.save(imageFile, fileName);

                Customeruser newCust = customeruserRegisterRequest.convertToEntity();
                Image image = Image.builder().name(fileName).type(imageFile.getContentType()).url(url)
                                .build();
                newCust.setImage(image);
                Customeruser saveCust = this.customeruserService.createOne(newCust);

                if (saveCust != null) {
                        CustomeruserCustomeResponse response = new CustomeruserCustomeResponse(201,
                                        "Registration successful",
                                        saveCust.convertToResponse());
                        return new ResponseEntity<>(response, HttpStatus.CREATED);
                }

                CustomeruserCustomeResponse response = new CustomeruserCustomeResponse(400,
                                "Username or Email already exists",
                                null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        @GetMapping("/customerusers/{id}/images")
        public ResponseEntity<byte[]> getOneImageByCustomer(@PathVariable("id") Long id) throws IOException {
                Customeruser customeruser = this.customeruserService.findOneById(id);
                Image image = customeruser.getImage();
                Resource resource = this.imageService.load(image);

                return ResponseEntity.ok().contentType(MediaType.valueOf(image.getType()))
                                .body(resource.getContentAsByteArray());
        }

        @GetMapping("/unassigned-customerusers/{projectId}")
        public ResponseEntity<List<CustomeruserResponse>> getCustomersNotInProject(
                        @RequestParam(required = false, name = "email", defaultValue = "") Optional<String> optionalEmail,
                        @PathVariable("projectId") Long projectId) {
                List<Customeruser> customers = customeruserService.getCustomersNotInProject(projectId, optionalEmail);
                List<CustomeruserResponse> customeruserResponses = customers.stream().map(c -> c.convertToResponse())
                                .toList();
                return ResponseEntity.ok(customeruserResponses);
        }
}
