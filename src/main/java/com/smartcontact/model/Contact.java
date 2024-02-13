package com.smartcontact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;



import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contact")
public class Contact {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;
    private String name;
    private String phone;
    private String email;
    @Column(length = 1000)
    private String detail;

    @JsonIgnore
    @ManyToOne
    private User user;

    public Contact(){

    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

     public boolean equals(Object obj){
        return this.cId==((Contact)obj).getcId();
     }
}
