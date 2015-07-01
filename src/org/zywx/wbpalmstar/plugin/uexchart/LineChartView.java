package org.zywx.wbpalmstar.plugin.uexchart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.LargeValueFormatter;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexchart.EUExChart.OnValueSelectedListener;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BaseUnit;
import org.zywx.wbpalmstar.plugin.uexchart.vo.ExtraLine;
import org.zywx.wbpalmstar.plugin.uexchart.vo.LineChartVO;
import org.zywx.wbpalmstar.plugin.uexchart.vo.LineUnitData;

import java.util.ArrayList;
import java.util.List;

public class LineChartView extends FrameLayout implements OnChartValueSelectedListener {

    public static final String TAG = "LineChartView";
    private LineChart mChart;

    private OnValueSelectedListener mListener;
    private LineChartVO mData;

    public LineChartView(Context context, LineChartVO lineChartVO) {
        super(context);
        LayoutInflater.from(context).inflate(EUExUtil.getResLayoutID("plugin_uexchart_linechart_layout"),this,true);
        mChart = (LineChart) findViewById(EUExUtil.getResIdID("plugin_uexchart_linechart"));
        mChart.setOnChartValueSelectedListener(this);
        this.mData=lineChartVO;
        if (mData == null){
            return;
        }
        initChartView(mData);
    }

    private void setData(LineChartVO chartVO) {
        List<String> xDatas = chartVO.getxData();
        List<LineUnitData> list = chartVO.getLines();
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int j = 0; j < list.size(); j++) {
            LineUnitData line = list.get(j);
            List<BaseUnit> datas = line.getData();
            for (int i = 0; i < xDatas.size(); i++) {
                String title = xDatas.get(i);
                if(!xVals.contains(title)){
                    xVals.add(title);
                }
            }

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            for (int i = 0; i < datas.size(); i++) {
                yVals.add(new Entry(datas.get(i).getyValue(), datas.get(i).getIndex()));
            }

            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(yVals, line.getLineName());

            float solid = 10f;
            if(line.isSolid()) solid = 0f;
            set1.enableDashedLine(10f, solid, 10f);
            set1.setValueTextColor(chartVO.getValueTextColor());
            set1.setValueTextSize(chartVO.getValueTextSize());
            set1.setColor(line.getLineColor());
            set1.setCircleColor(line.getCircleColor());
            set1.setLineWidth(line.getLineWidth());
            set1.setCircleSize(line.getCircleSize());
            set1.setDrawValues(chartVO.isShowValue());
            set1.setDrawCubic(false);
            if (line.getCubicIntensity() > 0){
                set1.setDrawCubic(true);
                set1.setCubicIntensity(line.getCubicIntensity());
            }
            set1.setDrawFilled(false);
//            if (line.isHasFillColor()){
//                set1.setDrawFilled(true);
//                set1.setFillAlpha(line.getAlpha());
//                set1.setFillColor(line.getFillColor());
//            }
            dataSets.add(set1);
        }

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        // set data
        mChart.setData(data);
    }


    public void setmListener(OnValueSelectedListener mListener) {
        this.mListener = mListener;
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

    private void initChartView(LineChartVO chart) {
        mChart.setDrawGridBackground(true);
        mChart.setGridBackgroundColor(Color.TRANSPARENT);
        mChart.setDescription(chart.getDesc());
        mChart.setDescriptionColor(chart.getDescTextColor());
        mChart.setDescriptionTextSize(chart.getDescTextSize());

        mChart.animateX(chart.getDuration());
        mChart.setBackgroundColor(chart.getBgColor());
        mChart.setDrawBorders(true);
        mChart.setBorderColor(chart.getBorderColor());
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
        yAxisL.setStartAtZero(false);
        if (chart.isShowUnit()){
            yAxisL.setValueFormatter(new LargeValueFormatter(chart.getUnit()));
        }

        YAxis yAxisR = mChart.getAxisRight();
        yAxisR.setTextColor(chart.getDescTextColor());
        yAxisR.setTextSize(chart.getDescTextSize());
        yAxisR.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        mChart.getAxisRight().setEnabled(false);

        setExtraLines(chart, yAxisL);
    }

    private void setExtraLines(LineChartVO chart, YAxis yAxis) {
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
