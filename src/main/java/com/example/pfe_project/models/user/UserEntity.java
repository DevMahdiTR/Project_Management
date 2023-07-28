package com.example.pfe_project.models.user;

import com.example.pfe_project.models.feedBack.FeedBackUser;
import com.example.pfe_project.models.holiday.HolidayType;
import com.example.pfe_project.models.role.Role;
import com.example.pfe_project.models.training_session.TrainingSession;
import com.example.pfe_project.models.Token.Token;
import com.example.pfe_project.models.work_position.WorkPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id",unique = true)
    private long id;

    @Column(name ="username",unique = true, nullable = false)
    @Size(min = 2 , max = 15, message = "username should be between 2 and 15 characaters")
    private String username;

    @Column(name = "email", unique = true,nullable = false)
    private String email;

    @Column(name = "phone_number",unique = true,nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @Size(min = 2 , max = 15, message = "password should be between 2 and 15 characaters")
    private String password;

    @Column(name = "sold_vacation")
    private long soldVacation = 30;

    @Column(name = "is_enabled")
    private boolean isEnabled = false;


    @OneToOne
    @JoinColumn(name ="work_position_id")
    private WorkPosition workPosition;

    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL)
    private List<Token> tokens;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "training_session_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "training_session_id"))
    private List<TrainingSession> enrolledTrainingSessions = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "feed_back_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "feed_back_id"))
    private List<FeedBackUser> feedBackUsers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "holiday_request",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "holiday_type_id"))
    private List<HolidayType> holidayTypeList = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();










    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
    public void addRole(Role role) {
        this.roles.add(role);
    }
    public void addHolidayType(HolidayType holidayType){this.holidayTypeList.add(holidayType);}
}
