package com.example.individualpr.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    public String  birthdate = "19/01/2003";
    @NotNull(message = "Поле не может быть пустым")
    private Long serialpassport;

    @NotNull(message = "Поле не может быть пустым")
    private Long numberpassport;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 250, message = "От 1 до 250 символов")
    private String address;

    @NotNull(message = "Поле не может быть пустым")
    @Column(unique = true)
    private String numberphone;

    @OneToOne(optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = true)
    private Post post;

    @OneToMany(mappedBy = "employees", fetch = FetchType.EAGER)
    private Set<Cheque> cheques;
    @OneToMany(mappedBy = "employes", fetch = FetchType.EAGER)
    private Set<Report> report;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getSerialpassport() {
        return serialpassport;
    }

    public void setSerialpassport(Long serialpassport) {
        this.serialpassport = serialpassport;
    }
    public Long getNumberpassport() {
        return numberpassport;
    }

    public void setNumberpassport(Long numberpassport) {
        this.numberpassport = numberpassport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Set<Cheque> getCheques() {
        return cheques;
    }

    public void setCheques(Set<Cheque> cheques) {
        this.cheques = cheques;
    }

    public Set<Report> getReport() {
        return report;
    }

    public void setReport(Set<Report> report) {
        this.report = report;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }
}
