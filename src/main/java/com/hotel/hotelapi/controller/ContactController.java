package com.hotel.hotelapi.controller;

import com.hotel.hotelapi.entity.ContactEntity;
import com.hotel.hotelapi.model.Response;
import com.hotel.hotelapi.service.ContactServiceImpl;
import com.hotel.hotelapi.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {
    @Autowired
    private IContactService contactService = new ContactServiceImpl();
    @PostMapping("/create")
    private Response sendContact(@RequestBody ContactEntity contact){
        Response response = new Response();

        boolean equals = contactService.createContact(contact);
        if(equals==true)
            response = new Response(true,"Successfully",true);
        else
            response = new Response(false,"Error create contact",false);
        return response;
    }
}
