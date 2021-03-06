package com.lookingglass.model;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lookingglass.utils.Utils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users", schema = "datatable")
public class UsersEntity {
    @Expose
    private Integer id;
    @Expose
    private String label;
    private Collection<PicturesEntity> picturesById = new HashSet<>();
    @Expose
    private Collection<ProgramsEntity> programsById = new HashSet<>();
    @Expose
    private Collection<UsersParametersEntity> usersParametersById = new HashSet<>();

    private Collection<UpdateQueueEntity> usersUpdateQueueById = new HashSet<>();
    private Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .disableHtmlEscaping()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public UsersEntity()
    {

    }

    public UsersEntity(String _email)
    {
        this.label = String.valueOf(Math.abs(_email.hashCode()));
        UsersParametersEntity temp = new UsersParametersEntity("E-mail",_email);
        temp.setUsersByUserId(this);
        this.usersParametersById.add(temp);
    }

    public void addNewPicutre()
    {

    }

    public void addPicture(PicturesEntity _picture)
    {
        if (!this.picturesById.contains(_picture))
        {
            _picture.setUsersByUserId(this);
            this.picturesById.add(_picture);
        }
    }

    public void addPictures(List<PicturesEntity> _pictures)
    {
        for(PicturesEntity pic : _pictures)
        {
            if (this.picturesById.contains(pic))
            {
                pic.setUsersByUserId(this);
                this.picturesById.add(pic);
            }
        }
    }

    public void addPictures( String _pathOfFolder)
    {
        try (Stream<Path> walk = Files.walk(Paths.get(_pathOfFolder)))
        {
            List<String> result = walk.map(Path::toString).filter(f -> f.endsWith(".jpg")).collect(Collectors.toList());
            for(String str : result)
            {
                PicturesEntity temp = new PicturesEntity(Utils.loadImgToByteArray(_pathOfFolder.concat("//").concat(str)));
                if(!this.picturesById.contains(temp))
                {
                    temp.setUsersByUserId(this);
                    this.picturesById.add(temp);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addUpdateQueueItem()
    {
        UpdateQueueEntity temp = new UpdateQueueEntity();
        temp.setUsersByUserId(this);
        this.usersUpdateQueueById.add(temp);
    }

    public void addParameter(UsersParametersEntity _parameter)
    {
        if (!this.usersParametersById.contains(_parameter))
        {
            _parameter.setUsersByUserId(this);
            this.usersParametersById.add(_parameter);
        }
    }

    public void addParameter(String _paramName, String _paramValue)
    {
        UsersParametersEntity temp = new UsersParametersEntity(_paramName,_paramValue);
        if (!this.usersParametersById.contains(temp))
        {
            temp.setUsersByUserId(this);
            this.usersParametersById.add(temp);
        }
    }

    public void addParameters(List<UsersParametersEntity> _parameters)
    {
        for (UsersParametersEntity param : _parameters)
        {
            if (!this.usersParametersById.contains(param))
            {
                param.setUsersByUserId(this);
                this.usersParametersById.add(param);
            }
        }
    }

    public UsersParametersEntity getParameterByName(String _paramName)
    {
        UsersParametersEntity temp = new UsersParametersEntity();
        temp = this.usersParametersById.stream().filter( param -> _paramName.equals(param.getParameterName())).findAny().orElse(null);
        return temp;
    }

    public UsersParametersEntity getParameterById(Integer id)
    {
        UsersParametersEntity temp = new UsersParametersEntity();
        temp = this.usersParametersById.stream().filter( param -> id.equals(param.getId())).findAny().orElse(null);
        return temp;
    }

    public String returnUsersPassword()
    {
        return getParameterByName("password").getParameterValue();
    }



    public void addProgram(ProgramsEntity _program)
    {
        if (!this.programsById.contains(_program))
        {
            _program.setUsersByUserId(this);
            this.programsById.add(_program);
        }
    }

    public void addProgram(List<ProgramsEntity> _programs)
    {
        for(ProgramsEntity prog : _programs)
        {
            if (!this.programsById.contains(prog))
            {
                prog.setUsersByUserId(this);
                this.programsById.add(prog);
            }
        }
    }

    public void addProgram(String _programName)
    {
        ProgramsEntity temp = new ProgramsEntity(_programName);
        if(!this.programsById.contains(temp))
        {
            temp.setUsersByUserId(this);
            this.programsById.add(temp);
        }
    }

    public String userAsJson()
    {
        return gson.toJson(this);
    }

    public String usersParametersAsJson()
    {
        return gson.toJson(this.usersParametersById);
    }

    public String usersProgramsAsJson()
    {
        return gson.toJson(this.programsById);
    }

    public String usersPicturseAsJson(){return gson.toJson(this.picturesById);}



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
    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UpdateQueueEntity> getUsersUpdateQueueById() {
        return usersUpdateQueueById;
    }
    public void setUsersUpdateQueueById(Collection<UpdateQueueEntity> usersUpdateQueueById)
    {
        this.usersUpdateQueueById = usersUpdateQueueById;
    }
}
