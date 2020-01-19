package com.lookingglass.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "programs", schema = "datatable")
public class ProgramsEntity {
    private int id;
    private String name;

    private ArrayList<ProgramParametersEntity> programParameters = new ArrayList<>();

    public ProgramsEntity()
    {

    }

    ProgramsEntity(String _programName)
    {
        this.name = _programName;
        this.programParameters.add(new ProgramParametersEntity("ProgramName",_programName));
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
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramsEntity that = (ProgramsEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
