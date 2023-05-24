package com.example.individualpr.Models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "appStatus")
public class AppStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 250, message = "От 1 до 250 символов")
    private String name;

    @OneToMany(mappedBy = "appStatus", fetch = FetchType.EAGER)
    private Collection<RequestSupport> requestSupports;

    public AppStatus(Long id, String name, Collection<RequestSupport> requestSupports) {
        this.id = id;
        this.name = name;
        this.requestSupports = requestSupports;
    }

    public AppStatus() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<RequestSupport> getRequestSupports() {
        return requestSupports;
    }

    public void setRequestSupports(Collection<RequestSupport> requestSupports) {
        this.requestSupports = requestSupports;
    }
}
