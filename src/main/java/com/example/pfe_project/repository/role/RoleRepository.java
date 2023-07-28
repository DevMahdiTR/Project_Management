package com.example.pfe_project.repository.role;

import com.example.pfe_project.models.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "SELECT R FROM Role R WHERE R.name = :name")
    Optional<Role> fetchRoleByName(String name);

    @Query(value = "SELECT R FROM Role R WHERE R.id = :id")
    Optional<Role> fetchRoleByName(long id);

    @Query(value = "DELETE FROM Role R WHERE R.id = :id")
    Role deleteRoleById(long id);
    @Query(value = "DELETE FROM Role R WHERE R.name = :name")
    Role deleteRoleByName(String name);


}
