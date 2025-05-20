package com.minis.test.bean;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/15 10:44
 */
public class Desk {
    private String color;
    private int length;
    private int width;
    private int height;

    public Desk(String color) {
        this.color = color;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
