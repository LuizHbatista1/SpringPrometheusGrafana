package com.PrometheusGrafana.app.domain;


import com.PrometheusGrafana.app.DTOS.ClientDTO;
import jakarta.persistence.*;

@Table(name  = "clients")
@Entity(name = "clients" )
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private Integer age;
    private String gender;

    public Client (){

    }

    public Client(ClientDTO data){

        this.name = data.name();
        this.age = data.age();
        this.gender = data.gender();

    }

    public Client(Long id, String name, Integer age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
