package com.lookingglass.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "programs", schema = "datatable")
public class ProgramsEntity {
    private Integer id;
    private String name;
    private Integer userId;
    private Collection<ProgramParametersEntity> programParametersById;
    private UsersEntity usersByUserId;

    public ProgramsEntity()
    {

    }

    public ProgramsEntity(String _name)
    {
        this.name = _name;
    }

    public void addParameters( ProgramParametersEntity _parameter)
    {
        if(!this.programParametersById.contains(_parameter))
        {
            this.programParametersById.add(_parameter);
        }
    }

    public void addParameters(String _paramName, String _paramValue)
    {
        ProgramParametersEntity temp = new ProgramParametersEntity(_paramName,_paramValue);
        if (!this.programParametersById.contains(temp))
        {
            this.programParametersById.add(temp);
        }
    }

    public void addParameters(List<ProgramParametersEntity> _parameters)
    {
        for(ProgramParametersEntity param : _parameters)
        {
            if (!this.programParametersById.contains(param))
            {
                this.programParametersById.add(param);
            }
        }
    }

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramsEntity that = (ProgramsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "programsByProgramId")
    public Collection<ProgramParametersEntity> getProgramParametersById() {
        return programParametersById;
    }

    public void setProgramParametersById(Collection<ProgramParametersEntity> programParametersById) {
        this.programParametersById = programParametersById;
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
