package model;

import java.io.Serializable;
import java.util.Date;

public class employee implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String phone;
    private String dateJoined;
    private Integer salary;

    public employee() {

    }

    public employee(Integer id) {
        this.id = id;
    }

    public employee(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public employee(String name) {
        this.name = name;
    }

    public employee(Integer id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public employee(Integer id, String name, String phone, String dateJoined, Integer salary) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.dateJoined = dateJoined;
        this.salary = salary;

    }

    @Override
    public String toString() {
        return "employee [id=" + id + ", name=" + name + ", phone=" + phone + ", date joined="+dateJoined+", salary="+salary+ "]";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public String getPhone() {
        return this.phone;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getDateJoined() {
        return dateJoined;
    }
}