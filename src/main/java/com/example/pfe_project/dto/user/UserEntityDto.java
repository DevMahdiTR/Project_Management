package com.example.pfe_project.dto.user;


import com.example.pfe_project.models.role.Role;
import com.example.pfe_project.models.user.UserEntity;
import com.example.pfe_project.models.work_position.WorkPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityDto {

    private long id;
    private String username;
    private String email;
    private String phoneNumber;
    private boolean isEnable;
    private long soldVacation;
    private WorkPosition workPosition;
    private List<Role> roles;


    public UserEntityDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.roles = userEntity.getRoles();
        this.phoneNumber = userEntity.getPhoneNumber();
        this.isEnable = userEntity.isEnabled();
        this.soldVacation = userEntity.getSoldVacation();
        this.workPosition = userEntity.getWorkPosition();
    }
}
