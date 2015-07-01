package org.zywx.wbpalmstar.plugin.uexchart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.LargeValueFormatter;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexchart.EUExChart.OnValueSelectedListener;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BarChartVO;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BarUnitData;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BaseUnit;
import org.zywx.wbpalmstar.plugin.uexchart.vo.ExtraLine;

import java.util.ArrayList;
import java.util.List;

public class BarChartView extends FrameLayout implements OnChartValueSelectedListener {
    public static final String TAG = "BarChartView";
    private BarChart mChart;
    private OnValueSelectedListener mListener;
    private BarChartVO mData;

    public BarChartView(Context context, BarChartVO chartVO) {
        super(context);
        LayoutInflater.from(context).inflate(EUExUtil.getResLayoutID("plugin_uexchart_barchart_layout"),this,true);
        mChart = (BarChart) findViewById(EUExUtil.getResIdID("plugin_uexchart_barchart"));
        mChart.setOnChartValueSelectedListener(this);
        mData=chartVO;
        if (mData == null){
            return;
        }
        initChartView(mData);
    }

    public void setmListener(OnValueSelectedListener mListener) {
        this.mListener = mListener;
    }


    private void initChartView(BarChartVO chart){
        mChart.setDrawValuesForWholeStack(true);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(true);
        mChart.setGridBackgroundColor(Color.TRANSPARENT);


        mChart.setDescription(chart.getDesc());
        mChart.setDescriptionColor(chart.getDescTextColor());
        mChart.setDescriptionTextSize(chart.getDescTextSize());
        mChart.setBackgroundColor(chart.getBgColor());
        mChart.setDrawBorders(true);
        mChart.setBorderColor(chart.getBorderColor());
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.animateY(chart.getDuration());

        //djf
        //mChart.setDrawUnitsInChart(chart.isShowUint());
        //mChart.setUnit(chart.getUnit());
        setData(chart);
        Legend l = mChart.getLegend();
        l.setEnabled(chart.isShowLegend());
        l.setTextColor(chart.getDescTextColor());
        l.setTextSize(chart.getDescTextSize());
        l.setPosition(chart.getLegendPosition());

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(chart.getDescTextSize());
        xAxis.setTextColor(chart.getDescTextColor());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(chart.getBorderColor());

        YAxis yAxisL = mChart.getAxisLeft();
        yAxisL.setTextColor(chart.getDescTextColor());
        yAxisL.setTextSize(chart.getDescTextSize());
        yAxisL.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxisL.setDrawGridLines(true);
        yAxisL.setGridColor(chart.getBorderColor());
        if (chart.isHasMax()) yAxisL.setAxisMaxValue(chart.getMaxValue());
        if (chart.isHasMin()) yAxisL.setAxisMinValue(chart.getMinValue());
        yAxisL.setDrawAxisLine(true);
        yAxisL.setStartAtZero(false);
        if (chart.isShowUnit()){
            yAxisL.setValueFormatter(new LargeValueFormatter(chart.getUnit()));
        }

        YAxis yAxisR = mChart.getAxisRight();
        yAxisR.setTextColor(chart.getDescTextColor());
        yAxisR.setTextSize(chart.getDescTextSize());
        yAxisR.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        mChart.getAxisRight().setEnabled(false);
        //XLabels xl  = mChart.getXLabels();
        //xl.setCenterXLabelText(true);
        setExtraLines(chart, yAxisL);
    }

    private void setData(BarChartVO chart) {
        List<BarUnitData> list = chart.getBars();
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int j = 0; j < list.size(); j++) {
            BarUnitData bar = list.get(j);
            List<BaseUnit> datas = bar.getData();
            for (int i = 0; i < datas.size(); i++) {
                String title = datas.get(i).getxValue();
                if(!xVals.contains(title)){
                    xVals.add(title);
                }
            }

            ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

            for (int i = 0; i < datas.size(); i++) {
                yVals.add(new BarEntry(datas.get(i).getyValue(), i));
            }

            // create a dataset and give it a type
            BarDataSet set1 = new BarDataSet(yVals, bar.getBarName());
            set1.setColor(bar.getBarColor());
            set1.setValueTextSize(chart.getValueTextSize());
            set1.setValueTextColor(chart.getValueTextColor());
            // disable the drawing of values
            set1.setDrawValues(chart.isShowValue());
            dataSets.add(set1);
        }

        // create a data object with the datasets
        BarData data = new BarData(xVals, dataSets);
        // add space between the dataset groups in percent of bar-width
        data.setGroupSpace(110f);
        // set data
        mChart.setData(data);
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        if(mListener != null){
            mListener.onValueSelected(entry.getVal());
        }
    }

    @Override
    public void onNothingSelected() {
        
    }
    private void setExtraLines(BarChartVO chart, YAxis yAxis) {
        List<ExtraLine> list = chart.getExtraLines();
        if (list == null || list.size() < 1) return;
        for (int i = 0; i < list.size(); i++){
            ExtraLine line = list.get(i);
            LimitLine limitLine = new LimitLine(line.getyValue(), line.getLineName());
            limitLine.setLineWidth(line.getLineWidth());
            float solid = 10f;
            if(line.isSolid()) solid = 0f;
            limitLine.enableDashedLine(10f, solid, 0f);
            limitLine.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
            limitLine.setTextColor(line.getTextColor());
            limitLine.setTextSize(line.getTextSize());
            yAxis.addLimitLine(limitLine);
        }
    }
}
