package com.lookingglass.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "pictures", schema = "datatable")
public class PicturesEntity {
    private int id;
    private byte[] picture;
    private Timestamp timeStamp;

    public PicturesEntity()
    {

    }

    public PicturesEntity(byte[] blob)
    {
        this.picture = blob;
        this.timeStamp =  new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "picture", nullable = false)
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "time_stamp", nullable = false)
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PicturesEntity that = (PicturesEntity) o;
        return id == that.id &&
                Arrays.equals(picture, that.picture) &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, timeStamp);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }
}
