package com.example.individualpr.Models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "personalAccount")
public class PersonalAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long numPersonalAccount;

    private Double balanceAccount;

    @OneToOne(optional = true)
    @JoinColumn(name = "client_id")
    private Client clients;

    @OneToOne(mappedBy = "personalAccounts")
    private EthernetContract ethernetContract;

    @ManyToOne(optional = true)
    private Rate rates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumPersonalAccount() {
        return numPersonalAccount;
    }

    public void setNumPersonalAccount(Long numPersonalAccount) {
        this.numPersonalAccount = numPersonalAccount;
    }

    public Client getClients() {
        return clients;
    }

    public void setClients(Client clients) {
        this.clients = clients;
    }

    public Rate getRates() {
        return rates;
    }

    public void setRates(Rate rates) {
        this.rates = rates;
    }

    public EthernetContract getEthernetContract() {
        return ethernetContract;
    }

    public void setEthernetContract(EthernetContract ethernetContract) {
        this.ethernetContract = ethernetContract;
    }

    public Double getBalanceAccount() {
        return balanceAccount;
    }

    public void setBalanceAccount(Double balanceAccount) {
        this.balanceAccount = balanceAccount;
    }
}
