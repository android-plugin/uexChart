package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;

public class BaseUnit implements Serializable{
    private static final long serialVersionUID = 274709251317945518L;
    private String xValue;
    private float yValue;

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
