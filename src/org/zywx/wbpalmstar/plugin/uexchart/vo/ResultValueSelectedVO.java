package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;

public class ResultValueSelectedVO implements Serializable{
    private static final long serialVersionUID = 6113173964948826835L;
    private String value;
    private String dataSetIndex;
    private String xIndex;
    private String id;// 图表id change by waka 2016/01/22

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDataSetIndex() {
        return dataSetIndex;
    }

    public void setDataSetIndex(String dataSetIndex) {
        this.dataSetIndex = dataSetIndex;
    }

    public String getxIndex() {
        return xIndex;
    }

    public void setxIndex(String xIndex) {
        this.xIndex = xIndex;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
