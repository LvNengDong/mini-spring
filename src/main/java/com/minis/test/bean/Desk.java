package com.minis.test.bean;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/15 10:44
 */
public class Desk {
    private String color;
    private String length;
    private String width;
    private String height;
    private Stool ref1;

    public Desk(String color) {
        this.color = color;
    }


    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Stool getRef1() {
        return ref1;
    }

    public void setRef1(Stool ref1) {
        this.ref1 = ref1;
    }
}
