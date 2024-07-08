package com.nhom7.qltd.service;

import com.nhom7.qltd.model.Contact;
import com.nhom7.qltd.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    @Autowired
    private  final ContactRepository contactRepository;
    public List<Contact> getAllContact(){
        return contactRepository.findAll();
    }
    public Contact addContact(Contact contact) {
        return contactRepository.save(contact);
    }
}
