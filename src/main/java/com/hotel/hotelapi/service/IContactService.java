package com.hotel.hotelapi.service;

import com.hotel.hotelapi.entity.ContactEntity;
import com.hotel.hotelapi.model.ContactModel;
import io.swagger.v3.oas.annotations.info.Contact;

import java.util.List;

public interface IContactService {
    boolean createContact(ContactEntity contact);
    List<ContactModel> getAllContact(int status);
    ContactModel getContactById();

    ContactModel completeContact(Integer id);
}
