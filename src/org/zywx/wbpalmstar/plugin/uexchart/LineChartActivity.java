package org.zywx.wbpalmstar.plugin.uexchart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Legend;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexchart.EUExChart.OnValueSelectedListener;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BaseUnit;
import org.zywx.wbpalmstar.plugin.uexchart.vo.LineChartVO;
import org.zywx.wbpalmstar.plugin.uexchart.vo.LineUnitData;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends Activity implements OnChartValueSelectedListener{

    public static final String TAG = "LineChartActivity";
    private LineChart mChart;
    private OnValueSelectedListener mListener;
    private LineChartVO mData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(EUExUtil.getResLayoutID("plugin_uexchart_linechart_layout"));
        mChart = (LineChart) findViewById(EUExUtil.getResIdID("plugin_uexchart_linechart"));
        mChart.setOnChartValueSelectedListener(this);
        if (getIntent() != null){
            mListener = (OnValueSelectedListener) getIntent()
                    .getSerializableExtra(JsConst.PARAMS_LISTENER);
            mData = (LineChartVO) getIntent().getSerializableExtra(JsConst.PARAMS_DATA_VO);
        }
        if (mListener == null || mData == null){
            return;
        }
        initChartView(mData);
    }

    private void setData(List<LineUnitData> list) {
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int j = 0; j < list.size(); j++) {
            LineUnitData line = list.get(j);
            List<BaseUnit> datas = line.getData();
            for (int i = 0; i < datas.size(); i++) {
                String title = datas.get(i).getxValue();
                if(!xVals.contains(title)){
                    xVals.add(title);
                }
            }

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            for (int i = 0; i < datas.size(); i++) {
                yVals.add(new Entry(datas.get(i).getyValue(), i));
            }

            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(yVals, line.getLineName());

            float solid = 10f;
            if(line.isSolid()) solid = 0f;
            set1.enableDashedLine(10f, solid, 10f);
            set1.setColor(line.getLineColor());
            set1.setCircleColor(line.getCircleColor());
            set1.setLineWidth(line.getLineWidth());
            set1.setCircleSize(line.getCircleSize());
            set1.setFillAlpha(65);
            set1.setFillColor(Color.BLACK);
            dataSets.add(set1);
        }

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
    }
    
    @Override
    public void onNothingSelected() {
        
    }

    @Override
    public void onValueSelected(Entry arg0, int arg1) {
        if(mListener != null){
            mListener.onValueSelected(arg0.getVal());
        }
    }

    private void initChartView(LineChartVO chart){
        mChart.setDrawYValues(chart.isShowValue());
        mChart.setDrawGridBackground(false);
        mChart.setDescription(chart.getDesc());

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.animateX(chart.getDuration());
        mChart.setDrawUnitsInChart(chart.isShowUint());
        mChart.setUnit(chart.getUnit());
        mChart.setBackgroundColor(chart.getBgColor());
        mChart.setDrawLegend(chart.isShowLegend());//is show legend(tuli)
        mChart.setValueTextColor(chart.getValueTextColor());
        mChart.setDescriptionTextColor(chart.getDescTextColor());
        mChart.setDescriptionTextSize(chart.getDescTextSize());
        mChart.setValueTextSize(chart.getValueTextSize());
        setData(chart.getLines());
        Legend l = mChart.getLegend();
        l.setTextColor(chart.getDescTextColor());
        l.setTextSize(chart.getDescTextSize());
        l.setPosition(chart.getLegendPosition());
    }
    
}
