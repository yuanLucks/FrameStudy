package com.bean;

import com.annocation.Column;
import com.annocation.Table;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/29 13:23
 * @description：User实体类
 * @modified By：
 */
@Table("tb_user")
public class User {
    @Column(value = "tb_id" , isId = true)
    private String id;
    @Column("tb_name")
    private String username;
    @Column("tb_nickname")
    private String nickname;
    @Column("tb_pw")
    private String password;
    @Column("tb_phone")
    private String phone;

    public User() {
    }

    public User(String id, String username, String nickname, String password, String phone) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
