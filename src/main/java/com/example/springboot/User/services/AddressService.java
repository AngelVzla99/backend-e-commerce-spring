package com.example.springboot.User.services;

import com.example.springboot.User.entities.UserEntity;
import com.example.springboot.User.repositories.UserEntityRepository;
import com.example.springboot.User.converter.AddressConverter;
import com.example.springboot.User.dto.AddressDTO;
import com.example.springboot.User.entities.AddressEntity;
import com.example.springboot.User.repositories.AddressEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AddressService {
    @Autowired
    private AddressEntityRepository addressEntityRepository;
    @Autowired
    private AddressConverter addressConverter;
    @Autowired
    UserEntityRepository userEntityRepository;

    public AddressDTO save(AddressDTO addressDTO) {
        AddressEntity entity = addressConverter.toEntity(addressDTO);
        return addressConverter.toDto(addressEntityRepository.save(entity));
    }

    public void delete(Long userId, Long id) {
        // find user in the db
        UserEntity user = userEntityRepository
                .findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This address don't belong to the user of the token, or not exist"));
        // search address in the db
        AddressEntity address = addressEntityRepository
                .findByUserAndId(user,id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This address don't belong to the user of the token, or not exist"));
        // delete from the db
        addressEntityRepository.deleteById(id);
    }
}
