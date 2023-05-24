package com.example.individualpr.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "subMemo")
public class SubscriberMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = true)
    @JoinColumn(name = "client_id")
    private Client clients;

    @NotNull(message = "Не может быть пустым")
    private String ipAddress = "Назначается по DHCP";

    @NotNull(message = "Не может быть пустым")
    private String maskUnderWeb = "Назначается по DHCP";

    @NotNull(message = "Не может быть пустым")
    private String mainGateway = "Назначается по DHCP";

    @NotNull(message = "Не может быть пустым")
    private String preferDNSserver;

    @NotNull(message = "Не может быть пустым")
    private String alternateDNSserver;

    @NotNull(message = "Не может быть пустым")
    private String fastSetupConnect = "http://setup.well-comm.ru";

    @NotNull(message = "Не может быть пустым")
    private String typeConnect = "L2TP (без шифрования)";

    @NotNull(message = "Не может быть пустым")
    private String serverAccess = "L2TP.well-comm.ru";

    @NotNull(message = "Не может быть пустым")
    private String protocolAuth = "CHAP";

    private String serverMessage = "mail.moscow.net";

    @Column(name = "real_IP_Address", nullable = true)
    private String realIPAddress;

    @OneToOne(mappedBy = "subscriberMemos")
    private EthernetContract ethernetContract;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMaskUnderWeb() {
        return maskUnderWeb;
    }

    public void setMaskUnderWeb(String maskUnderWeb) {
        this.maskUnderWeb = maskUnderWeb;
    }

    public String getMainGateway() {
        return mainGateway;
    }

    public void setMainGateway(String mainGateway) {
        this.mainGateway = mainGateway;
    }

    public String getPreferDNSserver() {
        return preferDNSserver;
    }

    public void setPreferDNSserver(String preferDNSserver) {
        this.preferDNSserver = preferDNSserver;
    }

    public String getAlternateDNSserver() {
        return alternateDNSserver;
    }

    public void setAlternateDNSserver(String alternateDNSserver) {
        this.alternateDNSserver = alternateDNSserver;
    }

    public String getFastSetupConnect() {
        return fastSetupConnect;
    }

    public void setFastSetupConnect(String fastSetupConnect) {
        this.fastSetupConnect = fastSetupConnect;
    }

    public String getTypeConnect() {
        return typeConnect;
    }

    public void setTypeConnect(String typeConnect) {
        this.typeConnect = typeConnect;
    }

    public String getServerAccess() {
        return serverAccess;
    }

    public void setServerAccess(String serverAccess) {
        this.serverAccess = serverAccess;
    }

    public String getProtocolAuth() {
        return protocolAuth;
    }

    public void setProtocolAuth(String protocolAuth) {
        this.protocolAuth = protocolAuth;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public String getRealIPAddress() {
        return realIPAddress;
    }

    public void setRealIPAddress(String realIPAddress) {
        this.realIPAddress = realIPAddress;
    }

    public Client getClients() {
        return clients;
    }

    public void setClients(Client clients) {
        this.clients = clients;
    }

    public EthernetContract getEthernetContract() {
        return ethernetContract;
    }

    public void setEthernetContract(EthernetContract ethernetContract) {
        this.ethernetContract = ethernetContract;
    }
}
