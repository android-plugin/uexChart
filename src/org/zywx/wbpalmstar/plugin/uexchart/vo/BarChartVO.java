package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;
import java.util.List;

public class BarChartVO extends BaseChart implements Serializable{
    private static final long serialVersionUID = 6054074786849266338L;
    private List<BarUnitData> bars;

    public List<BarUnitData> getBars() {
        return bars;
    }

    public void setBars(List<BarUnitData> bars) {
        this.bars = bars;
    }
}
