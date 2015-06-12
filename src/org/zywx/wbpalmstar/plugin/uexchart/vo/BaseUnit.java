package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;

public class BaseUnit implements Serializable{
    private static final long serialVersionUID = 274709251317945518L;
    private int index;
    private String xValue;
    private float yValue;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getxValue() {
        return xValue;
    }

    public void setxValue(String xValue) {
        this.xValue = xValue;
    }

    public float getyValue() {
        return yValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }
}
