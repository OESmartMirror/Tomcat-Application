package com.lookingglass.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "pictures", schema = "datatable")
public class PicturesEntity {
    private Integer id;
    private byte[] picture;
    private Timestamp timeStamp;
    private UsersEntity usersByUserId;

    //filenév alapján törlés
    //név alappján kép letöltés
    //képfeltöltés
    //képeket egyben zipbe? összes kép
    //külön adatbázis tábla ha történt változás az adott ebmernél akkor bekerül ide a lablje és tudni lehet, hogy mit kell tenni

    //program és program | user paraméáterek beállítása

    //tükrös regisztráció
    //{Register | User} : {Label} módon kellene

    public PicturesEntity()
    {

    }

    public PicturesEntity(byte[] _arr)
    {
        this.picture = _arr;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public PicturesEntity(UsersEntity _usersByUserId, byte[] _arr)
    {
        this.picture = _arr;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
    }

    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!Arrays.equals(picture, that.picture)) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(picture);
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
