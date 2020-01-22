package com.lookingglass.model;

import javax.persistence.*;

@Entity
@Table(name = "program_parameters", schema = "datatable")
public class ProgramParametersEntity {
    private Integer id;
    private Integer programId;
    private String parameterName;
    private String parameterValue;
    private ProgramsEntity programsByProgramId;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "program_id", nullable = true)
    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
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

        ProgramParametersEntity that = (ProgramParametersEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (programId != null ? !programId.equals(that.programId) : that.programId != null) return false;
        if (parameterName != null ? !parameterName.equals(that.parameterName) : that.parameterName != null)
            return false;
        if (parameterValue != null ? !parameterValue.equals(that.parameterValue) : that.parameterValue != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (programId != null ? programId.hashCode() : 0);
        result = 31 * result + (parameterName != null ? parameterName.hashCode() : 0);
        result = 31 * result + (parameterValue != null ? parameterValue.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    public ProgramsEntity getProgramsByProgramId() {
        return programsByProgramId;
    }

    public void setProgramsByProgramId(ProgramsEntity programsByProgramId) {
        this.programsByProgramId = programsByProgramId;
    }
}
