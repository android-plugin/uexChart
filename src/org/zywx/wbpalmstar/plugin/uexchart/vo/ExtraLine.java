package org.zywx.wbpalmstar.plugin.uexchart.vo;

import org.zywx.wbpalmstar.base.BUtility;

import java.io.Serializable;

public class ExtraLine implements Serializable{
    private static final long serialVersionUID = 282187045620091795L;
    private float yValue;
    private String lineName;
    private boolean isSolid = false;
    private String lineColor = "#f00";
    private float lineWidth = 4f;
    private String textColor = "#f00";
    private int textSize = 9;

    public float getyValue() {
        return yValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setIsSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    public int getLineColor() {
        return BUtility.parseColor(lineColor);
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public int getTextColor() {
        return BUtility.parseColor(textColor);
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
}
