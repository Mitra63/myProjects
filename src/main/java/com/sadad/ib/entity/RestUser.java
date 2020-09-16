package com.sadad.ib.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "rest_user")
public class RestUser implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name="user_id")
    private Long userId;
    private String username;
    private String password;
    private boolean disabled;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;


}
