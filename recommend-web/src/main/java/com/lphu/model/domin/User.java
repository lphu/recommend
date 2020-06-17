package com.lphu.model.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * @author hupeilei
 * @create 2020/3/10 2:55 下午
 */
@Document(collection = "user")
public class User {

    @Id
    @JsonIgnore
    private String id;
    private Integer userId;
    @NotNull
    private String userName;
    @NotNull
    private String password;

    public User() {}

    public User(String userName, String password) {
        this.userId = userName.hashCode();
        this.userName = userName;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setName(String userName) {
        this.userId = userName.hashCode();
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
