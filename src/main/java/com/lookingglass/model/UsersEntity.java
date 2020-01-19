package com.lookingglass.model;

import com.lookingglass.utils.Utils;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users", schema = "datatable")
public class UsersEntity
{
    private int id;
    private String label;

    private ArrayList<PicturesEntity> picturesEntities = new ArrayList<>();
    private ArrayList<UsersParametersEntity> usersParametersEntities = new ArrayList<>();
    private ArrayList<ProgramsEntity> userProgramesEntity = new ArrayList<>();

    public UsersEntity()
    {

    }

    public UsersEntity(String _email)
    {
        UsersParametersEntity temp = new UsersParametersEntity("E-mail",_email);

        if (!this.usersParametersEntities.contains(temp))
        {
            this.label = String.valueOf(Math.abs(_email.hashCode()));
            this.usersParametersEntities.add(temp);
        }
    }

    public void AddPicture(PicturesEntity _picture)
    {
        if (!this.picturesEntities.contains(_picture))
        {
            this.picturesEntities.add(_picture);
        }
    }

    public void AddPictures(List<PicturesEntity> _pictures)
    {
        for(PicturesEntity pic : _pictures)
        {
            if (this.picturesEntities.contains(pic))
            {
                this.picturesEntities.add(pic);
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
                if(!this.picturesEntities.contains(temp)) this.picturesEntities.add(temp);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void AddParameter(UsersParametersEntity _parameter)
    {
        if (!this.usersParametersEntities.contains(_parameter))
        {
            this.usersParametersEntities.add(_parameter);
        }
    }

    public void AddParameter(String _paramName, String _paramValue)
    {
        UsersParametersEntity temp = new UsersParametersEntity(_paramName,_paramValue);
        if (!this.usersParametersEntities.contains(temp))
        {
            this.usersParametersEntities.add(temp);
        }
    }

    public void AddParameters(List<UsersParametersEntity> _parameters)
    {
        for (UsersParametersEntity param : _parameters)
        {
            if (!this.usersParametersEntities.contains(param))
            {
                this.usersParametersEntities.add(param);
            }
        }
    }

    public void AddProgram(ProgramsEntity _program)
    {
        if (!this.userProgramesEntity.contains(_program))
        {
            this.userProgramesEntity.add(_program);
        }
    }

    public void AddProgram(List<ProgramsEntity> _programs)
    {
        for(ProgramsEntity prog : _programs)
        {
            if (!this.userProgramesEntity.contains(prog))
            {
                this.userProgramesEntity.add(prog);
            }
        }
    }

    public void AddProgram(String _programName)
    {
        ProgramsEntity temp = new ProgramsEntity(_programName);
        if(!this.userProgramesEntity.contains(temp))
        {
            this.userProgramesEntity.add(temp);
        }
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
        return id == that.id &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }
}
