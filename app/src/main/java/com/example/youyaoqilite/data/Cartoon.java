package com.example.youyaoqilite.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Cartoon {
    @Id
    private String name;
    private String cover;
    private String tags;
    private String description;
    private String comicid;

    @Generated(hash = 1659972754)
    public Cartoon(String name, String cover, String tags, String description, String comicid) {
        this.name = name;
        this.cover = cover;
        this.tags = tags;
        this.description = description;
        this.comicid = comicid;
    }

    @Generated(hash = 1288418135)
    public Cartoon() {
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComicid() {
        return comicid;
    }

    public void setComicid(String comicid) {
        this.comicid = comicid;
    }
}
