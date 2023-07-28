package com.example.pfe_project.models.feedBack;


import com.example.pfe_project.models.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "feed_back_user")
public class FeedBackUser {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "feed_back_id")
    private FeedBack feedBack;

    @Column(name = "comment")
    private String comment;

}
