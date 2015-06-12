package org.zywx.wbpalmstar.plugin.uexchart;

import com.github.mikephil.charting.utils.ValueFormatter;

public class ValueUnitFormatter implements ValueFormatter{

    private String unit;

    public ValueUnitFormatter(String unit) {
        this.unit = unit;
    }

    @Override
    public String getFormattedValue(float v) {
        return v + unit;
    }
}
