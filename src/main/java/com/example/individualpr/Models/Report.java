package com.example.individualpr.Models;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
@Entity
@Table(name = "report")
public class Report {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @NotNull(message = "Необходимо ввести дату")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Temporal(TemporalType.DATE)
        @PastOrPresent(message = "Дата не может быть будущей")
        private Date dataStart;

        @NotNull(message = "Необходимо ввести дату")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Temporal(TemporalType.DATE)
        @PastOrPresent(message = "Дата не может быть будущей")
        private Date dataEnd;

        @NotNull(message = "Поле не может быть пустым")
        @Size(min = 1, max = 100, message = "От 1 до 250 символов")
        private String content;

        @ManyToOne(optional = true)
        private Employee employes;

    public Report(Long id, Date dataStart, Date dataEnd, String content, Employee employes) {
        this.id = id;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
        this.content = content;
        this.employes = employes;
    }

    public Report() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataStart() {
        return dataStart;
    }

    public void setDataStart(Date dataStart) {
        this.dataStart = dataStart;
    }

    public Date getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(Date dataEnd) {
        this.dataEnd = dataEnd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Employee getEmployes() {
        return employes;
    }

    public void setEmployes(Employee employes) {
        this.employes = employes;
    }
}
