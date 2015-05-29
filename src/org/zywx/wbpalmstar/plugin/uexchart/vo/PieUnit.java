package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;

public class PieUnit implements Serializable{
    private static final long serialVersionUID = -2260672993888143132L;
    private String color;
    private String title;
    private float value;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
