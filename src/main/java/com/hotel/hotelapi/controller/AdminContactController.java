package com.hotel.hotelapi.controller;


import com.google.api.Http;
import com.hotel.hotelapi.model.ContactModel;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management/contact")
public class AdminContactController {
    @Autowired
    private IContactService contactService;

    @GetMapping("/all/{status}")
    public ResponseEntity<Response> getAllContact(@PathVariable("status") int status) {
        try {
            List<ContactModel> contactModel = contactService.getAllContact(status);
            return ResponseEntity.ok(new Response(true, "Fetch data success!", contactModel));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(false, "Fetch data failed: " + e.getMessage(), null));
        }
    }
    @PostMapping("/{id}")
    public ResponseEntity<Response> addContact(@PathVariable("id") int id) {
        return ResponseEntity.ok(new Response(true,"Success",contactService.completeContact(id)));
    }
}
