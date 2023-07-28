package com.example.pfe_project.controllers.role;


import com.example.pfe_project.models.role.Role;
import com.example.pfe_project.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("api/v1/role")
public class RoleController {
    private final RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService)
    {
        this.roleService  = roleService;
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<Role> getRoleById(@Min(1) @PathVariable("id")int  id)
    {
        return roleService.getRoleById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> createRole(@RequestBody @Valid Role role)
    {
        return roleService.createRole(role);
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<String> updateRoleById(@Min(1) @PathVariable("id") int id,@RequestBody @Valid Role roleDetails)
    {
        return roleService.updateRoleById(id,roleDetails);
    }
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteRoleById(@Min(1) @PathVariable("id") int id)
    {
        return roleService.deleteRoleById(id);
    }
}
