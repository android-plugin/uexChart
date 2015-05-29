package org.zywx.wbpalmstar.plugin.uexchart.vo;

import org.zywx.wbpalmstar.base.BUtility;

import java.io.Serializable;
import java.util.List;

public class LineUnitData implements Serializable{
    private static final long serialVersionUID = -7480730196797065030L;
    private String lineColor = "#000000";
    private int lineWidth = 2;
    private String circleColor = "#000000";
    private int circleSize = 4;
    private String lineName="";
    private boolean isSolid = true;
    private List<BaseUnit> data;

    public int getLineColor() {
        return BUtility.parseColor(lineColor);
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getCircleColor() {
        return BUtility.parseColor(circleColor);
    }

    public void setCircleColor(String circleColor) {
        this.circleColor = circleColor;
    }

    public int getCircleSize() {
        return circleSize;
    }

    public void setCircleSize(int circleSize) {
        this.circleSize = circleSize;
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

    public List<BaseUnit> getData() {
        return data;
    }

    public void setData(List<BaseUnit> data) {
        this.data = data;
    }
}
