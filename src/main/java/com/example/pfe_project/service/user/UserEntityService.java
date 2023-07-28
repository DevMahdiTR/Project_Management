package com.example.pfe_project.service.user;


import com.example.pfe_project.dto.user.UserEntityDto;
import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.models.role.Role;
import com.example.pfe_project.models.user.UserEntity;
import com.example.pfe_project.repository.role.RoleRepository;
import com.example.pfe_project.repository.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserEntityService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private UserEntityService(UserRepository userRepository,RoleRepository roleRepository)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public ResponseEntity<UserEntityDto> fetchUserById(long userId) {
        UserEntity user = getUserById(userId);

        UserEntityDto userEntityDto = new UserEntityDto(user);
        return ResponseEntity.ok(userEntityDto);
    }

    public ResponseEntity<String> addRoleToUserById(final long userId, @NotNull final Role role) {
        final Role roleSaved = getRoleByName(role.getName());
        final UserEntity userEntity = getUserById(userId);

        if(userHasRole(userEntity,roleSaved))
        {
            final String failedResponse = String.format("User with ID %d already has Role %s.",userEntity.getId(),role.getName());
            return new ResponseEntity<String>(failedResponse,HttpStatus.BAD_REQUEST);
        }
        userEntity.addRole(roleSaved);
        userRepository.save(userEntity);
        return new ResponseEntity<String>(String.format("Role '%s' is added to user with id %d", roleSaved.getName(), userId),HttpStatus.CREATED);
    }

    public ResponseEntity<String> removeUserRoleById(long userId, @NotNull Role role)
    {
        final String roleName = role.getName();
        final Role roleSaved = getRoleByName(roleName);
        final UserEntity userEntity = getUserById(userId);

        if(!userHasRole(userEntity,roleSaved))
        {
            final String failedResponse = String.format("User with ID %d does not has Role with name %s.",userEntity.getId(),role.getName());
            return new ResponseEntity<String>(failedResponse,HttpStatus.BAD_REQUEST);
        }


        userEntity.getRoles().removeIf(roleSaved::equals);
        userRepository.save(userEntity);

        final String responseMessage = String.format("Role '%s' removed from user with ID %d'", roleName, userId);
        return ResponseEntity.ok().body(responseMessage);
    }


    public ResponseEntity<String> enableUserByUsername(String username)
    {
        return enableOrDisableUserByUsername(username,true);
    }

    public ResponseEntity<String> disableUserByUsername(String username)
    {
        return enableOrDisableUserByUsername(username,false);
    }
    public ResponseEntity<String> enableUserById(long userId) {
        return enableOrDisableUserById(userId, true);
    }

    public ResponseEntity<String> disableUserById(long userId) {
        return enableOrDisableUserById(userId, false);
    }

    private @NotNull ResponseEntity<String> enableOrDisableUserById(long userId, boolean enabled) {
        final UserEntity user = getUserById(userId);
        user.setEnabled(enabled);
        userRepository.save(user);
        final String status = enabled ? "enabled" : "disabled";
        return new ResponseEntity<String>(String.format("User with ID : %d is %s.", userId, status), HttpStatus.OK);
    }
    private @NotNull ResponseEntity<String> enableOrDisableUserByUsername(String username, boolean enabled) {
        final UserEntity user = getUserByName(username);
        user.setEnabled(enabled);
        userRepository.save(user);
        final String status = enabled ? "enabled" : "disabled";
        return new ResponseEntity<String>(String.format("Username with name : %s is %s.", user.getUsername(), status), HttpStatus.OK);
    }

    private @NotNull ResponseEntity<List<UserEntityDto>> fetchUsersWithPrefix(String prefix, @NotNull Function<String, List<UserEntity>> fetchFunction) {
        List<UserEntity> usersSaved = fetchFunction.apply(prefix);
        List<UserEntityDto> usersDto = usersSaved.stream()
                .map(UserEntityDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usersDto);
    }
    public ResponseEntity<List<UserEntityDto>> fetchUsersWithUsernamePrefix(String prefix) {
        return fetchUsersWithPrefix(prefix, userRepository::fetchUsersWithUsernamePrefix);
    }

    public ResponseEntity<List<UserEntityDto>> fetchUsersWithEmailPrefix(String prefix) {
        return fetchUsersWithPrefix(prefix, userRepository::fetchUsersWithEmailPrefix);
    }

    public ResponseEntity<List<UserEntityDto>> fetchUsersWithPhoneNumberPrefix(String prefix) {
        return fetchUsersWithPrefix(prefix, userRepository::fetchUsersWithPhoneNumberPrefix);
    }



    public ResponseEntity<UserEntityDto> fetchUserWithAttribute(String attribute, @NotNull Function<String, Optional<UserEntity>> fetchFunction)
    {
        UserEntity userSaved = fetchFunction.apply(attribute).orElseThrow(()->new ResourceNotFoundException("User not found"));
        UserEntityDto userEntityDto = new UserEntityDto(userSaved);
        return ResponseEntity.ok(userEntityDto);
    }

    public ResponseEntity<UserEntityDto> fetchUserWithEmail(String email)
    {
        return fetchUserWithAttribute(email,userRepository::fetchUserWithEmail);
    }
    public ResponseEntity<UserEntityDto> fetchUserWithUsername(String username)
    {
        return fetchUserWithAttribute(username,userRepository::fetchUserWithUsername);
    }
    public ResponseEntity<UserEntityDto> fetchUserByPhoneNumber(String phoneNumber)
    {
        return fetchUserWithAttribute(phoneNumber,userRepository::fetchUserByPhoneNumber);
    }



    public UserEntity getUserById(long userId) {
        return userRepository.fetchUserWithId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID %d could not be found.", userId)));
    }
    public UserEntity getUserByName(String username) {
        return userRepository.fetchUserWithUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with name %s could not be found.", username)));
    }
    public Role getRoleByName(String roleName) {
        return roleRepository.fetchRoleByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The role with name %s could not be found.", roleName)));
    }

    private boolean userHasRole(UserEntity userEntity,Role role)
    {
        return userEntity.getRoles().contains(role);
    }
}
