package com.example.springboot.User.services;

import com.example.springboot.User.converter.RoleConverter;
import com.example.springboot.User.dto.RoleDTO;
import com.example.springboot.User.entities.RoleEntity;
import com.example.springboot.User.repositories.RoleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleEntityRepository roleEntityRepository;
    @Autowired
    RoleConverter roleConverter;

    public List<RoleDTO> findAll() {
        List<RoleEntity> entities = roleEntityRepository.findAll();
        return roleConverter.toDtoList(entities);
    }

    public Page<RoleDTO> findAllPageable(Pageable pageable){
        Page<RoleEntity> roleEntityPage = roleEntityRepository.findAll(pageable);
        if( !roleEntityPage.hasContent() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // convert the resulting list to dto
        Page<RoleDTO> roleDTOs = roleEntityPage.map(roleConverter::toDTO);
        return roleDTOs;
    }


    public RoleEntity findByRoleNameOrCreate(String roleName){
        Optional<RoleEntity> role = roleEntityRepository.findOneByRoleName(roleName);
        if(role.isEmpty()){
            RoleEntity newRole = new RoleEntity();
            newRole.setRoleName(roleName);
            return roleEntityRepository.save(newRole);
        }else{
            return role.get();
        }
    }

    public RoleDTO findById(Long id) {
        Optional<RoleEntity> roleEntity = roleEntityRepository.findById(id);
        if( roleEntity.isPresent() )  return roleConverter.toDTO(roleEntity.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public RoleDTO save(RoleDTO roleDTO) {
        RoleEntity roleEntity = roleConverter.toEntity(roleDTO);
        return roleConverter.toDTO( roleEntityRepository.save( roleEntity ) );
    }

    public void delete(Long id) {
        if(roleEntityRepository.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no role with id "+id);
        roleEntityRepository.deleteById(id);
    }
}
