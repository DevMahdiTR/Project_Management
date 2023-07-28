package com.example.pfe_project.controllers.user;

import com.example.pfe_project.dto.user.UserEntityDto;
import com.example.pfe_project.models.role.Role;
import com.example.pfe_project.service.user.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/user")
public class UserEntityController {


    private final UserEntityService userEntityService;

    @Autowired
    public UserEntityController(UserEntityService userEntityService)
    {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserEntityDto> fetchUserById(@PathVariable("id") int id) {return userEntityService.fetchUserById(id);}

    @GetMapping("/get/{username}")
    public ResponseEntity<UserEntityDto> fetchUserWithUsername(@PathVariable("username") String username) {return userEntityService.fetchUserWithUsername(username);}

    @GetMapping("/get/{email}")
    public ResponseEntity<UserEntityDto> fetchUserWithEmail(@PathVariable("email") String email) {return userEntityService.fetchUserWithEmail(email);}

    @GetMapping("/get/{phoneNumber}")
    public ResponseEntity<UserEntityDto> fetchUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {return userEntityService.fetchUserByPhoneNumber(phoneNumber);}




    @PutMapping("/update/addRole/{id}")
    public ResponseEntity<String> addRoleToUserById(@PathVariable int id,@RequestBody Role role) {return userEntityService.addRoleToUserById(id,role);}

    @PutMapping("/update/removeRole/{id}")
    public ResponseEntity<String> removeUserRoleById(@PathVariable int id , @RequestBody Role role)  {return userEntityService.removeUserRoleById(id, role);}


    @PutMapping("/update/enable/username/{username}")
    public ResponseEntity<String> enableUserByUsername(@PathVariable String username)
    {
        return userEntityService.enableUserByUsername(username);
    }


    @PutMapping("/update/enable/id/{id}")
    public ResponseEntity<String> enableUserById(@PathVariable int id)
    {
        return userEntityService.enableUserById(id);
    }

    @PutMapping("/update/disable/id/{id}")
    public ResponseEntity<String> disableUserById(@PathVariable int id){ return userEntityService.disableUserById(id);}


    @PutMapping("/update/disable/username/{username}")
    public ResponseEntity<String> disableUserByUsername(@PathVariable String username)
    {
        return userEntityService.disableUserByUsername(username);
    }

    @GetMapping("/users/username/{prefix}")
    public ResponseEntity<List<UserEntityDto>> fetchUsersWithUsernamePrefix(@PathVariable("prefix") String prefix) {
        return userEntityService.fetchUsersWithUsernamePrefix(prefix);
    }

    @GetMapping("/users/email/{prefix}")
    public ResponseEntity<List<UserEntityDto>> fetchUsersWithEmailPrefix(@PathVariable("prefix") String prefix) {
        return userEntityService.fetchUsersWithEmailPrefix(prefix);
    }
    @GetMapping("/users/phonenumber/{prefix}")
    public ResponseEntity<List<UserEntityDto>> fetchUsersWithPhoneNumberPrefix(@PathVariable("prefix") String prefix) {
        return userEntityService.fetchUsersWithPhoneNumberPrefix(prefix);
    }
}
