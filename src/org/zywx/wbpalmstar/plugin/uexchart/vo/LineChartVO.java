package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;
import java.util.List;

public class LineChartVO extends BaseChart implements Serializable{
    private static final long serialVersionUID = 4347585990596017640L;
    private OptionVO option;
    private List<String> xData;
    private List<LineUnitData> lines;

    public List<LineUnitData> getLines() {
        return lines;
    }

    public void setLines(List<LineUnitData> lines) {
        this.lines = lines;
    }

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public OptionVO getOption() {
        return option;
    }

    public void setOption(OptionVO option) {
        this.option = option;
    }
}
