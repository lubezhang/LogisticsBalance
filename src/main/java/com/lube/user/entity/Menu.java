package com.lube.user.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-23
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
public class Menu {
    private String code;
    private String parentCode;
    private String level;
    private String title;
    private String url;
    private List<Menu> childMenu = new ArrayList<Menu>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<Menu> childMenu) {
        this.childMenu = childMenu;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "code='" + code + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", level='" + level + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }


}
