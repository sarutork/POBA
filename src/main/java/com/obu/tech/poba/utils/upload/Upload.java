package com.obu.tech.poba.utils.upload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@ToString
@Entity
@Table(name = "upload", schema = "poba")
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @Column(name = "upload_id")
    private Long id;

    @ToString.Exclude
    @Column(name = "upload_group")
    private String group;

    @ToString.Exclude
    @Column(name = "reference_key")
    private Long reference;

    @Column(name = "original_name")
    private String filename;

    private String path;

    public Upload(String group, Long reference) {
        this.group = group;
        this.reference = reference;
    }
}
