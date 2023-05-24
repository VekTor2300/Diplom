package com.example.individualpr.Models;

import javax.persistence.*;

@Entity
@Table(name = "docs")
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "serial_pass", unique = true, nullable = false)
    private String serialPass;

    @Column(name = "num_pass", unique = true, nullable = false)
    private String numPass;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_doc_id")
    private ImageDocument imageDocument;

    @OneToOne(optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean checked = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImageDocument getImageDocument() {
        return imageDocument;
    }

    public void setImageDocument(ImageDocument imageDocument) {
        this.imageDocument = imageDocument;
    }


    public String getSerialPass() {
        return serialPass;
    }

    public void setSerialPass(String serialPass) {
        this.serialPass = serialPass;
    }

    public String getNumPass() {
        return numPass;
    }

    public void setNumPass(String numPass) {
        this.numPass = numPass;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
