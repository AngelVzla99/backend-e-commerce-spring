package com.example.springboot.User.services;

import com.example.springboot.User.converter.RoleConverter;
import com.example.springboot.User.dto.*;
import com.example.springboot.User.entities.RoleEntity;
import com.example.springboot.User.repositories.RoleEntityRepository;
import com.example.springboot.User.converter.UserConverter;
import com.example.springboot.User.entities.UserEntity;
import com.example.springboot.User.converter.AddressConverter;
import com.example.springboot.User.converter.CartItemConverter;
import com.example.springboot.User.repositories.UserEntityRepository;
import com.example.springboot.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    RoleEntityRepository roleEntityRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleConverter roleConverter;
    @Autowired
    AddressConverter addressConverter;
    @Autowired
    CartItemConverter cartItemConverter;
    @Autowired
    CartItemService cartItemService;

    public UserDTO findById(Long id) {
        UserConverter mapper = new UserConverter();
        Optional<UserEntity> user = userEntityRepository.findById(id);
        if (user.isPresent()) return mapper.toDTO(user.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user is not in the database");
    }

    public UserDTO create(UserDTO dto) {
        // Search each role in the database
        Set<RoleEntity> roles = new HashSet<>();
        for (Long id : dto.getRoles()) {
            Optional<RoleEntity> roleTemp = roleEntityRepository.findById(id);
            if (roleTemp.isPresent()) roles.add(roleTemp.get());
            else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if(dto.getId()==null){
            Optional<UserEntity> user = userEntityRepository.findOneByEmail(dto.getEmail());
            if (user.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email already exist");
        }

        // convert DTO to Entity
        UserEntity userEntity = userConverter.toEntity(dto, roles);
        return userConverter.toDTO(userEntityRepository.save(userEntity));
    }

    public UserDTO partialUpdate(Long userId, PartialUpdateUserDto dto){
        // TODO: use a library for this
        UserEntity user = userEntityRepository
                .findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The product with id "+ userId +" was not found"));

        String newPassword = dto.getPassword();
        if(newPassword!=null && !newPassword.isEmpty()){
            newPassword =  new BCryptPasswordEncoder().encode(newPassword).toString();
            user.setPassword(newPassword);
        }

        String newResetPasswordToken = dto.getResetPasswordToken();
        if(newResetPasswordToken!=null && !newResetPasswordToken.isEmpty()){
            user.setResetPasswordToken(newResetPasswordToken);
        }

        String firstName = dto.getFirstName();
        if(firstName!=null && !firstName.isEmpty()){
            user.setFirstName(firstName);
        }

        String lastName = dto.getLastName();
        if(lastName!=null && !lastName.isEmpty()){
            user.setLastName(lastName);
        }

        String phoneNumber = dto.getPhoneNumber();
        if(phoneNumber!=null && !phoneNumber.isEmpty()){
            user.setPhoneNumber(phoneNumber);
        }

        String email = dto.getEmail();
        if(email!=null && !email.isEmpty()){
            user.setEmail(email);
        }

        UserEntity newEntity = userEntityRepository.save(user);
        return userConverter.toDTO(newEntity);
    }

    public boolean isEmailInDataBase(String email){
        Optional<UserEntity> user = userEntityRepository.findOneByEmail(email);
        return user.isPresent();
    }

    public UserDTO findByEmail(String email){
        Optional<UserEntity> user = userEntityRepository.findOneByEmail(email);
        if (user.isPresent()) return userConverter.toDTO(user.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user is not in the database");
    }

    public UserDTO addRole(Long userId, List<Long> roleIds){
        UserEntity userEntity = userEntityRepository
                .findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with id "+ userId +" was not found"));

        // add each role the user list
        Set<RoleEntity> userRoles = userEntity.getRoles();
        for (Long roleId : roleIds) {
            RoleEntity role = roleEntityRepository
                    .findById(roleId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role " + roleId + " not found"));
            userRoles.add(role);
        }

        // update the database
        userEntity.setRoles(userRoles);
        return userConverter.toDTO( userEntityRepository.save(userEntity) );
    }

    public UserDTO createCustomer(UserDTO user){
        if(this.isEmailInDataBase(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email already exist");
        }

        // Search each role in the database
        RoleEntity role = roleService.findByRoleNameOrCreate("customer");
        Set<RoleEntity> roleEntities = new HashSet<>();
        roleEntities.add( role );

        // convert DTO to Entity
        UserEntity userEntity = userConverter.toEntity(user, roleEntities);
        return userConverter.toDTO(userEntityRepository.save(userEntity));
    }

    public UserDTO findByEmailToken(String email){
        return findByEmail(email);
    }

    public List<RoleDTO> getRoles(String email){
        UserEntity entity = findUserEntityByEmail(email);
        return roleConverter.toDtoList(entity.getRoles().stream().toList());
    }

    public List<AddressDTO> getAddresses(String email){
        UserEntity entity = findUserEntityByEmail(email);
        return addressConverter.toDtoList(entity.getAddresses());
    }

    public List<CartItemDTO> getCartItems(String email){
        UserEntity entity = findUserEntityByEmail(email);
        return cartItemConverter.toDtoList(entity.getCartItems());
    }

    public void updateUserCart(String email, List<CartItemDTO> productIds){
        UserEntity user = findUserEntityByEmail(email);
        user.getCartItems().forEach(item -> cartItemService.delete(item.getId()));
        // search the list of cart items
        productIds.forEach(product -> product.setUserId(user.getId()));
        // update the database
        cartItemService.saveAll( productIds );
    }

    public void delete(Long id) {
        userEntityRepository.deleteById(id);
    }

    public String getUserToken(String email, String password) throws UsernameNotFoundException {
        // get user data
        UserEntity user = findUserEntityByEmail(email);
        // bad password
        if (! new BCryptPasswordEncoder().matches(password, user.getPassword()))
            throw new UsernameNotFoundException("The user with email "+email+" does not exist");
        // get roles
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return TokenUtils.createTokenUser(email,authorities);
    }

    public Page<UserDTO> findAllPageable(Pageable pageable) {
        Page<UserEntity> responsePage = userEntityRepository.findAll(pageable);
        if (!responsePage.hasContent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return responsePage.map(userConverter::toDTO);
    }

    // =============================
    //    method to get entities  //
    // =============================

    public UserEntity findUserEntityByEmail(String email){
        return userEntityRepository
                .findOneByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The email "+email+ " was not found"));
    }
}
