package com.hotel.hotelapi.service;

import com.hotel.hotelapi.entity.ContactEntity;
import com.hotel.hotelapi.model.ContactModel;
import com.hotel.hotelapi.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements IContactService{
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public boolean createContact(ContactEntity contact){
        try{
            contact.setStatus(0);
            contactRepository.save(contact);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public List<ContactModel> getAllContact(int status) {
        List<ContactEntity> contactEntities = contactRepository.findAllByStatusEquals(status);
        return contactEntities.stream()
                .map(contactEntity -> modelMapper.map(contactEntity, ContactModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ContactModel getContactById() {
        return null;
    }
    @Override
    public ContactModel completeContact(Integer id){
        ContactEntity contactEntity = contactRepository.findById(id).get();
        contactEntity.setStatus(1);
        ContactEntity saved =  contactRepository.save(contactEntity);
        return modelMapper.map(saved, ContactModel.class);
    }
}
