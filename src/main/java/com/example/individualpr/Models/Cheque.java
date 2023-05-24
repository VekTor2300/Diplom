package com.example.individualpr.Models;


import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "cheque")
public class Cheque{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Ввод обязателен")
    @Range(min = 0, max = 500, message = "Значение от 0 до 500")
    private Integer quantity;
    @NotNull(message = "Необходимо ввести время")
    private String timesell;

    @ManyToOne(optional = true)
    private Client client;

    @ManyToOne(optional = true)
    private Employee employees;

    @NotNull(message = "Ввод обязателен")
    @DecimalMin(value = "0.00", message = "Стоимость не может быть отрицательной")
    private BigDecimal totalCost;

    public Cheque() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTimesell() {
        return timesell;
    }

    public void setTimesell(String timesell) {
        this.timesell = timesell;
    }

    public com.example.individualpr.Models.Client getClient() {
        return client;
    }

    public void setClient(com.example.individualpr.Models.Client client) {
        this.client = client;
    }

    public com.example.individualpr.Models.Employee getEmployees() {
        return employees;
    }

    public void setEmployees(com.example.individualpr.Models.Employee employees) {
        this.employees = employees;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
