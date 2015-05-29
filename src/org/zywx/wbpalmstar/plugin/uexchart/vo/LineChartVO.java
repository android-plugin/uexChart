package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;
import java.util.List;

public class LineChartVO extends BaseChart implements Serializable{
    private static final long serialVersionUID = 4347585990596017640L;
    private List<LineUnitData> lines;

    public List<LineUnitData> getLines() {
        return lines;
    }

    public void setLines(List<LineUnitData> lines) {
        this.lines = lines;
    }
}
