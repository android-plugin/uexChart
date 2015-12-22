package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;
import java.util.List;

public class BarChartVO extends BaseChart implements Serializable{
    private static final long serialVersionUID = 6054074786849266338L;
    private List<String> xData;
    private List<BarUnitData> bars;
    private OptionVO option;

    public List<BarUnitData> getBars() {
        return bars;
    }

    public void setBars(List<BarUnitData> bars) {
        this.bars = bars;
    }

    public OptionVO getOption() {
        return option;
    }

    public void setOption(OptionVO option) {
        this.option = option;
    }

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }
}
