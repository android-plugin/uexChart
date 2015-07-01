package org.zywx.wbpalmstar.plugin.uexchart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;

import org.zywx.wbpalmstar.base.BUtility;
import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexchart.EUExChart.OnValueSelectedListener;
import org.zywx.wbpalmstar.plugin.uexchart.vo.PieChartVO;
import org.zywx.wbpalmstar.plugin.uexchart.vo.PieUnit;

import java.util.ArrayList;
import java.util.List;

public class PieChartView extends FrameLayout implements OnChartValueSelectedListener {

    public static final String TAG = "PieChartView";
    private PieChart mChart;
    private OnValueSelectedListener mListener;
    private PieChartVO mData;

    public PieChartView(Context context, PieChartVO chartVO) {
        super(context);
        LayoutInflater.from(context).inflate(EUExUtil.getResLayoutID("plugin_uexchart_piechart_layout"),this,true);
        mChart = (PieChart) findViewById(EUExUtil.getResIdID("plugin_uexchart_piechart"));
        this.mData=chartVO;
        if (mData == null){
            return;
        }
        initPieChartView(mData);
    }

    private void setData(PieChartVO chart) {
        List<PieUnit> list = chart.getData();
        if(list == null || list.size() == 0){
            return;
        }
        int length = list.size();
        String[] titles = new String[length];
        ArrayList<Entry> entrys = new ArrayList<Entry>();
        int[] colors = new int[length];
        for (int i = 0; i < length; i++) {
            PieUnit bean = list.get(i);
            titles[i] = bean.getTitle();
            entrys.add(new Entry(bean.getValue(), i));
            colors[i] = BUtility.parseColor(bean.getColor());
        }
        PieDataSet set1 = new PieDataSet(entrys, "");
        set1.setSliceSpace(3f);
        set1.setDrawValues(chart.isShowValue());
        set1.setValueTextColor(chart.getValueTextColor());
        set1.setValueTextSize(chart.getValueTextSize());
        set1.setColors(colors);
        if (chart.isShowPercent()){
            set1.setValueFormatter(new PercentFormatter());
        }else{
            if (chart.isShowUnit()){
                set1.setValueFormatter(new ValueUnitFormatter(chart.getUnit()));
            }
        }
        PieData data = new PieData(titles, set1);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    private void initPieChartView(PieChartVO chart) {
        mChart.animateXY(chart.getDuration(), chart.getDuration());
        String centerText = chart.getCenterTitle()+"\n" + chart.getCenterSummary();
        if(!centerText.equals("\n")) mChart.setCenterText(centerText);
        mChart.setCenterTextSize(chart.getDescTextSize());
        mChart.setCenterTextColor(chart.getDescTextColor());
        mChart.setDescription(chart.getDesc());
        mChart.setDescriptionColor(chart.getDescTextColor());
        mChart.setDescriptionTextSize(chart.getDescTextSize());
        mChart.setDrawHoleEnabled(chart.isShowCenter());//center circle part
        mChart.setDrawCenterText(chart.isShowCenter());
        mChart.setDrawSliceText(chart.isShowTitle());//is show title
        //mChart.setDrawUnitsInChart(chart.isShowUint());
        //mChart.setUnit(chart.getUnit());
        mChart.setBackgroundColor(chart.getBgColor());
        mChart.setHoleRadius(chart.getCenterRadius());
        mChart.setTransparentCircleRadius(chart.getCenterTransRadius());
        
        if(BUtility.parseColor(chart.getCenterColor()) == Color.TRANSPARENT){
            mChart.setHoleColorTransparent(true);
        }else{
            mChart.setHoleColor(BUtility.parseColor(chart.getCenterColor()));
        }
        mChart.setUsePercentValues(chart.isShowPercent());
        
        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        mChart.setRotationAngle(0);
        setData(chart);
        Legend l = mChart.getLegend();
        l.setEnabled(chart.isShowLegend());//is show legend(tuli)
        l.setTextColor(chart.getDescTextColor());
        l.setTextSize(chart.getDescTextSize());
        l.setPosition(chart.getLegendPosition());
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        if(mListener != null){
            entry.getXIndex();
            mListener.onValueSelected(entry.getVal());
        }
    }

    @Override
    public void onNothingSelected() {
        
    }

    public void setmListener(OnValueSelectedListener mListener) {
        this.mListener = mListener;
    }
}
