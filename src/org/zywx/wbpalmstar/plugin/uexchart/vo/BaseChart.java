package org.zywx.wbpalmstar.plugin.uexchart.vo;

import com.github.mikephil.charting.components.Legend.LegendPosition;

import org.zywx.wbpalmstar.base.BUtility;
import org.zywx.wbpalmstar.plugin.uexchart.JsConst;

import java.io.Serializable;
import java.util.List;

public class BaseChart implements Serializable {
    private static final long serialVersionUID = 7188055708964543242L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private double left = 0;
    private double top = 200;
    private double width = 1000;
    private double height = 1000;
    private String desc = "";
    private String descTextColor = "#000000";
    private int descTextSize = 12;
    private boolean showLegend = false;
    private String legendPosition = "bottom";
    private int duration = 1000;
    private boolean showUnit = false;
    private String unit = "";
    private boolean showValue = true;
    private String bgColor = "#00000000";
    private String valueTextColor = "#ffffff";
    private int valueTextSize = 13;
    private boolean isScrollWithWeb = false;
    private String borderColor = "#000000";
    private boolean isHasMax = false;
    private boolean isHasMin = false;
    private float maxValue;
    private float minValue;
    private List<ExtraLine> extraLines;

    public int getLeft() {
        return (int) left;
    }
    public void setLeft(double left) {
        this.left = left;
    }
    public int getTop() {
        return (int) top;
    }
    public void setTop(double top) {
        this.top = top;
    }
    public int getWidth() {
        return (int) width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return (int) height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDescTextColor() {
        return BUtility.parseColor(descTextColor);
    }

    public void setDescTextColor(String descTextColor) {
        this.descTextColor = descTextColor;
    }

    public int getDescTextSize() {
        return descTextSize;
    }
    public void setDescTextSize(int descTextSize) {
        this.descTextSize = descTextSize;
    }
    public boolean isShowLegend() {
        return showLegend;
    }
    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
    }
    public LegendPosition getLegendPosition() {
        if(legendPosition.equals(JsConst.LEGEND_BOTTOM)){
            return LegendPosition.BELOW_CHART_LEFT;
        }else if(legendPosition.equals(JsConst.LEGEND_RIGHT)){
            return LegendPosition.RIGHT_OF_CHART;
        }else{
            return LegendPosition.BELOW_CHART_LEFT;
        }
    }

    public void setLegendPosition(String legendPosition) {
        this.legendPosition = legendPosition;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getBgColor() {
        return BUtility.parseColor(bgColor);
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public int getValueTextColor() {
        return BUtility.parseColor(valueTextColor);
    }

    public void setValueTextColor(String valueTextColor) {
        this.valueTextColor = valueTextColor;
    }

    public int getValueTextSize() {
        return valueTextSize;
    }
    public void setValueTextSize(int valueTextSize) {
        this.valueTextSize = valueTextSize;
    }
    public boolean isShowValue() {
        return showValue;
    }
    public void setShowValue(boolean showValue) {
        this.showValue = showValue;
    }

    public boolean isScrollWithWeb() {
        return isScrollWithWeb;
    }

    public void setIsScrollWithWeb(boolean isScrollWithWeb) {
        this.isScrollWithWeb = isScrollWithWeb;
    }

    public int getBorderColor() {
        return BUtility.parseColor(borderColor);
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public boolean isShowUnit() {
        return showUnit;
    }

    public void setShowUnit(boolean showUnit) {
        this.showUnit = showUnit;
    }

    public boolean isHasMax() {
        return isHasMax;
    }

    public void setIsHasMax(boolean isHasMax) {
        this.isHasMax = isHasMax;
    }

    public boolean isHasMin() {
        return isHasMin;
    }

    public void setIsHasMin(boolean isHasMin) {
        this.isHasMin = isHasMin;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public List<ExtraLine> getExtraLines() {
        return extraLines;
    }

    public void setExtraLines(List<ExtraLine> extraLines) {
        this.extraLines = extraLines;
    }
}
