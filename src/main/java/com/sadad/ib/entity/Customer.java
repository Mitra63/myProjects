package com.sadad.ib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "customer")
public class Customer  implements Serializable {
    @Id
    @Column(name="user_id")
    private Long userId;
    @Column(name="first_Name")
    private String firstName;
    @Column(name="last_Name")
    private String lastName;
    private String mobile;
    @Column(name="national_Code")
    private String nationalCode;
    private String address;


}
