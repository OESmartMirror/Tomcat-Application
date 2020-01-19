package com.lookingglass.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users_parameters", schema = "datatable")
public class UsersParametersEntity {
    private int id;
    private String parameterName;
    private String parameterValue;
    private UsersEntity usersByUserId;

    public UsersParametersEntity()
    {

    }

    public UsersParametersEntity(String _paramName, String _parameterValue)
    {
        this.parameterName = _paramName;
        this.parameterValue = _parameterValue;
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
    @Column(name = "parameter_name", nullable = false, length = 20)
    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    @Basic
    @Column(name = "parameter_value", nullable = false, length = 100)
    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersParametersEntity that = (UsersParametersEntity) o;
        return id == that.id &&
                Objects.equals(parameterName, that.parameterName) &&
                Objects.equals(parameterValue, that.parameterValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parameterName, parameterValue);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
