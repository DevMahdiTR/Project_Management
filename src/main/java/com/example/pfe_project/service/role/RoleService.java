package com.example.pfe_project.service.role;

import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.models.role.Role;
import com.example.pfe_project.repository.role.RoleRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
public class RoleService {

    private final RoleRepository roleRepository;


    @Autowired
    public RoleService(@NotNull  RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }


    public ResponseEntity<String> createRole(Role role)
    {
        roleRepository.save(role);
        final String responseMessage  = String.format("Role %s created successfully.",role.getName());
        return new ResponseEntity<String>(responseMessage,HttpStatus.CREATED);
    }


    public ResponseEntity<Role> getRoleById(int roleId)
    {
        final Role role = roleRepository.fetchRoleByName(roleId).orElseThrow(()-> new ResourceNotFoundException(String.format("Role Id : %d is not found", roleId)));
        return ResponseEntity.ok(role);
    }

    public ResponseEntity<String> updateRoleById(int roleId, @Valid @RequestBody @NotNull Role roleDetails)
    {
        final Role role = roleRepository.fetchRoleByName(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role with ID %d not found", roleId)));

        role.setName(roleDetails.getName());
        final Role updatedRole = roleRepository.save(role);

        final String responseMessage = String.format("Role with name : %s is updated to %s",role.getName(),updatedRole.getName());
        return ResponseEntity.ok(responseMessage);
    }

    public ResponseEntity<String> deleteRoleById(int roleId) {
        Role role = roleRepository.fetchRoleByName(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role with ID %d not found", roleId)));

        roleRepository.deleteById(roleId);
        final String responseMessage = String.format("Role with ID %d has been deleted", roleId);
        return ResponseEntity.ok(responseMessage);
    }

}
