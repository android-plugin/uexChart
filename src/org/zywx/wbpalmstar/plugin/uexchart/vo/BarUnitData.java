package org.zywx.wbpalmstar.plugin.uexchart.vo;

import org.zywx.wbpalmstar.base.BUtility;

import java.io.Serializable;
import java.util.List;

public class BarUnitData implements Serializable{
    private static final long serialVersionUID = 4346375496675401659L;
    private String barName;
    private String barColor;
    private List<BaseUnit> data;

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public int getBarColor() {
        return BUtility.parseColor(barColor);
    }

    public void setBarColor(String barColor) {
        this.barColor = barColor;
    }

    public List<BaseUnit> getData() {
        return data;
    }

    public void setData(List<BaseUnit> data) {
        this.data = data;
    }
}
