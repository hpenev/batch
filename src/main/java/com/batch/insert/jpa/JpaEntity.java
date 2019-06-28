package com.batch.insert.jpa;

import javax.persistence.*;

@Entity
@Table(name = "bulk")
public class JpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "data")
    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public JpaEntity(byte[] data) {
        this.data = data;
    }
}
