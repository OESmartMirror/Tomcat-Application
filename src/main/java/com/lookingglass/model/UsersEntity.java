package com.lookingglass.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import com.lookingglass.utils.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users", schema = "datatable")
public class UsersEntity {
    private Integer id;
    private String label;
    private Collection<PicturesEntity> picturesById;
    private Collection<ProgramsEntity> programsById;
    private Collection<UsersParametersEntity> usersParametersById;

    public UsersEntity()
    {

    }

    public UsersEntity(String _email)
    {
        UsersParametersEntity temp = new UsersParametersEntity("E-mail",_email);

        if (!this.usersParametersById.contains(temp))
        {
            this.label = String.valueOf(Math.abs(_email.hashCode()));
            this.usersParametersById.add(temp);
        }
    }

    public void AddPicture(PicturesEntity _picture)
    {
        if (!this.picturesById.contains(_picture))
        {
            this.picturesById.add(_picture);
        }
    }

    public void AddPictures(List<PicturesEntity> _pictures)
    {
        for(PicturesEntity pic : _pictures)
        {
            if (this.picturesById.contains(pic))
            {
                this.picturesById.add(pic);
            }
        }
    }

    public void AddPictures( String _pathOfFolder)
    {
        try (Stream<Path> walk = Files.walk(Paths.get(_pathOfFolder)))
        {
            List<String> result = walk.map(Path::toString).filter(f -> f.endsWith(".jpg")).collect(Collectors.toList());
            for(String str : result)
            {
                PicturesEntity temp = new PicturesEntity(Utils.loadImgToByteArray(_pathOfFolder.concat("//").concat(str)));
                if(!this.picturesById.contains(temp)) this.picturesById.add(temp);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void AddParameter(UsersParametersEntity _parameter)
    {
        if (!this.usersParametersById.contains(_parameter))
        {
            this.usersParametersById.add(_parameter);
        }
    }

    public void AddParameter(String _paramName, String _paramValue)
    {
        UsersParametersEntity temp = new UsersParametersEntity(_paramName,_paramValue);
        if (!this.usersParametersById.contains(temp))
        {
            this.usersParametersById.add(temp);
        }
    }

    public void AddParameters(List<UsersParametersEntity> _parameters)
    {
        for (UsersParametersEntity param : _parameters)
        {
            if (!this.usersParametersById.contains(param))
            {
                this.usersParametersById.add(param);
            }
        }
    }

    public void AddProgram(ProgramsEntity _program)
    {
        if (!this.programsById.contains(_program))
        {
            this.programsById.add(_program);
        }
    }

    public void AddProgram(List<ProgramsEntity> _programs)
    {
        for(ProgramsEntity prog : _programs)
        {
            if (!this.programsById.contains(prog))
            {
                this.programsById.add(prog);
            }
        }
    }

    public void AddProgram(String _programName)
    {
        ProgramsEntity temp = new ProgramsEntity(_programName);
        if(!this.programsById.contains(temp))
        {
            this.programsById.add(temp);
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
    @Column(name = "label", nullable = false, length = 20)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<PicturesEntity> getPicturesById() {
        return picturesById;
    }

    public void setPicturesById(Collection<PicturesEntity> picturesById) {
        this.picturesById = picturesById;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<ProgramsEntity> getProgramsById() {
        return programsById;
    }

    public void setProgramsById(Collection<ProgramsEntity> programsById) {
        this.programsById = programsById;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UsersParametersEntity> getUsersParametersById() {
        return usersParametersById;
    }

    public void setUsersParametersById(Collection<UsersParametersEntity> usersParametersById) {
        this.usersParametersById = usersParametersById;
    }
}
