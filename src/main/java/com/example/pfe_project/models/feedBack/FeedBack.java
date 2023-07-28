package com.example.pfe_project.models.feedBack;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "feed_back")
public class FeedBack {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "feed_back_name",unique = true,nullable = false)
    private String feedBackName;


    @OneToMany(mappedBy = "feedBack", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<FeedBackUser> feedBackUsers = new ArrayList<>();



    public FeedBack (String feedBackName)
    {
        this.feedBackName = feedBackName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }



    public void setFeedBackName(String feedBackName)
    {
        this.feedBackName = feedBackName;
    }
    public String getFeedBackName()
    {
        return this.feedBackName;
    }



}
