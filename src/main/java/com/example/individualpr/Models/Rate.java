package com.example.individualpr.Models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nameRate;

    private String maxThroughput;

    private BigDecimal subscriptionFee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRate() {
        return nameRate;
    }

    public void setNameRate(String nameRate) {
        this.nameRate = nameRate;
    }

    public String getMaxThroughput() {
        return maxThroughput;
    }

    public void setMaxThroughput(String maxThroughput) {
        this.maxThroughput = maxThroughput;
    }

    public BigDecimal getSubscriptionFee() {
        return subscriptionFee;
    }

    public void setSubscriptionFee(BigDecimal subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
    }
}
