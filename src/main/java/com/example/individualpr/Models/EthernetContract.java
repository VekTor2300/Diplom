package com.example.individualpr.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ethernetContract")
public class EthernetContract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Не может быть пустым")
    private String ContractId;

    private boolean deleted = Boolean.FALSE;

    @OneToOne(optional = true)
    @JoinColumn(name = "client_id")
    private Client clients;

    @OneToOne(optional = true)
    @JoinColumn(name = "personal_Account_id")
    private PersonalAccount personalAccounts;

    @OneToOne(optional = true)
    @JoinColumn(name = "subscriber_Memo_id")
    private SubscriberMemo subscriberMemos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractId() {
        return ContractId;
    }

    public void setContractId(String contractId) {
        ContractId = contractId;
    }

    public Client getClients() {
        return clients;
    }

    public void setClients(Client clients) {
        this.clients = clients;
    }

    public PersonalAccount getPersonalAccounts() {
        return personalAccounts;
    }

    public void setPersonalAccounts(PersonalAccount personalAccounts) {
        this.personalAccounts = personalAccounts;
    }

    public SubscriberMemo getSubscriberMemos() {
        return subscriberMemos;
    }

    public void setSubscriberMemos(SubscriberMemo subscriberMemos) {
        this.subscriberMemos = subscriberMemos;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
