package com.minis.test.bean;

import com.minis.beans.factory.annotation.Autowired;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/15 10:44
 */
@Setter
@Getter
public class Desk {
    private String color;
    private String length;
    private String width;
    private String height;
    @Autowired
    private Stool stool;

    public Desk(String color) {
        this.color = color;
    }

}
