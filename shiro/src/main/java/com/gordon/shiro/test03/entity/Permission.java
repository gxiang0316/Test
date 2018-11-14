package com.gordon.shiro.test03.entity;

import java.io.Serializable;

/**
 * Created by gordon on 2018/9/11.
 */
public class Permission implements Serializable {

    private Long id;
    private String promission;//权限标识 程序中判断使用,如"user:create"
    private String description;//权限描述,UI界面显示使用
    private Boolean available = Boolean.FALSE;//是否可用,如果不可用将不会添加给用户

    public Permission(){}

    public Permission(String promission, String description, Boolean available) {
        this.promission = promission;
        this.description = description;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromission() {
        return promission;
    }

    public void setPromission(String promission) {
        this.promission = promission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", promission='" + promission + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                '}';
    }
}
