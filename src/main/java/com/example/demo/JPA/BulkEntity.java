package com.example.demo.JPA;

import javax.persistence.*;

@Entity
@Table(name = "bulk")
public class BulkEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "data")
    private byte[] data;

    BulkEntity(byte[] data) {
        this.data = data;
    }
}
