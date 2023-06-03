package com.group3.projectmanagementapi.customeruser;

import java.util.List;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import com.group3.projectmanagementapi.customeruser.model.Customeruser;
import com.group3.projectmanagementapi.customeruser.model.Image;
import com.group3.projectmanagementapi.customeruser.model.dto.CustomeruserCustomeResponse;
import com.group3.projectmanagementapi.customeruser.model.dto.CustomeruserLoginRequest;
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

    @PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CustomeruserResponse> createOneWithImage(
            @Valid @RequestPart("customeruserRequest") CustomeruserRegisterRequest customeruserRegisterRequest,
            @RequestPart("image") MultipartFile imageFile) {
        if (customeruserRegisterRequest.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String url = this.imageService.save(imageFile);
        Customeruser newMovie = customeruserRegisterRequest.convertToEntity();
        Image image = Image.builder().name(imageFile.getOriginalFilename()).type(imageFile.getContentType()).url(url)
                .build();
        newMovie.setImage(image);
        Customeruser saveMovie = this.customeruserService.createOne(newMovie);
        CustomeruserResponse movieResponse = saveMovie.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(movieResponse);
    }

    @GetMapping("/customerusers/{id}/images")
    public ResponseEntity<byte[]> getOneImageByMovie(@PathVariable("id") Long id) throws IOException {
        Customeruser movie = this.customeruserService.findOneById(id);
        Image image = movie.getImage();
        Resource resource = this.imageService.load(image);

        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getType()))
                .body(resource.getContentAsByteArray());
    }

    @GetMapping("/unassigned-customerusers/{projectId}")
    public ResponseEntity<List<CustomeruserResponse>> getCustomersNotInProject(
            @PathVariable("projectId") Long projectId) {
        List<Customeruser> customers = customeruserService.getCustomersNotInProject(projectId);
        List<CustomeruserResponse> customeruserResponses = customers.stream().map(c -> c.convertToResponse())
                .toList();
        return ResponseEntity.ok(customeruserResponses);
    }
}
