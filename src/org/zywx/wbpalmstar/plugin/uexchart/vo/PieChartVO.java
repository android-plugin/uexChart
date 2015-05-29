package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;
import java.util.List;

public class PieChartVO extends BaseChart implements Serializable{
    private static final long serialVersionUID = 2441298795061190419L;
    private boolean showCenter = true;
    private String centerColor = "#00000000";
    private String centerTitle = "";
    private String centerSummary = "";
    private float centerRadius = 40f;
    private float centerTransRadius = 42f;

    private boolean showTitle = true;

    private boolean showPercent = true;

    private List<PieUnit> data;

    public List<PieUnit> getData() {
        return data;
    }

    public void setData(List<PieUnit> data) {
        this.data = data;
    }

    public boolean isShowCenter() {
        return showCenter;
    }

    public void setShowCenter(boolean showCenter) {
        this.showCenter = showCenter;
    }

    public String getCenterColor() {
        return centerColor;
    }

    public void setCenterColor(String centerColor) {
        this.centerColor = centerColor;
    }

    public String getCenterTitle() {
        return centerTitle;
    }

    public void setCenterTitle(String centerTitle) {
        this.centerTitle = centerTitle;
    }

    public String getCenterSummary() {
        return centerSummary;
    }

    public void setCenterSummary(String centerSummary) {
        this.centerSummary = centerSummary;
    }

    public float getCenterRadius() {
        return centerRadius;
    }

    public void setCenterRadius(float centerRadius) {
        this.centerRadius = centerRadius;
    }

    public float getCenterTransRadius() {
        return centerTransRadius;
    }

    public void setCenterTransRadius(float centerTransRadius) {
        this.centerTransRadius = centerTransRadius;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public boolean isShowPercent() {
        return showPercent;
    }

    public void setShowPercent(boolean showPercent) {
        this.showPercent = showPercent;
    }
}
