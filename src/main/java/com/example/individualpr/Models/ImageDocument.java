package com.example.individualpr.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idImage;

    private String name;

    private String originalFileName;

    private Long size;

    private String contentType;

    @Lob
    private byte[] bytes;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, mappedBy = "imageDocument")
    private Documents documents;
}
