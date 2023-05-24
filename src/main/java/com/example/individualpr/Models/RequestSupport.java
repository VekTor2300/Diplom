package com.example.individualpr.Models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "requestSupp")
public class RequestSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long codeApp;

    private String dateApp;

    @NotNull(message = "Поле не может быть пустым")
    @Size(min = 1, max = 250, message = "От 1 до 250 символов")
    private String fio;

    @NotNull(message = "Поле не может быть пустым")
    @Column(name = "number_phone", unique = true)
    private Long numberPhone;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 250, message = "От 1 до 250 символов")
    private String address;

    @ManyToOne(optional = true)
    private AppStatus appStatus;

    @Column(name = "is_deleted")
    private boolean deleted = Boolean.FALSE;

    public RequestSupport(Long id, Long codeApp, String dateApp, String fio, Long numberPhone, String address, AppStatus appStatus) {
        this.id = id;
        this.codeApp = codeApp;
        this.dateApp = dateApp;
        this.fio = fio;
        this.numberPhone = numberPhone;
        this.address = address;
        this.appStatus = appStatus;
    }

    public RequestSupport() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodeApp() {
        return codeApp;
    }

    public void setCodeApp(Long codeApp) {
        this.codeApp = codeApp;
    }

    public String getDateApp() {
        return dateApp;
    }

    public void setDateApp(String dateApp) {
        this.dateApp = dateApp;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Long getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(Long numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AppStatus getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(AppStatus appStatus) {
        this.appStatus = appStatus;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
