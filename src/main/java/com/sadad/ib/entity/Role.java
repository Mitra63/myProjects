package com.sadad.ib.entity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name="role_id")
    private Long roleId;
    @Column(name="role_name")
    private String roleName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<RestUser> users = new HashSet<>();
}
