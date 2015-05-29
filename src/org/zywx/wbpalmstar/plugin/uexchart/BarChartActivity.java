package org.zywx.wbpalmstar.plugin.uexchart;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.LargeValueFormatter;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.XLabels;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexchart.EUExChart.OnValueSelectedListener;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BarChartVO;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BarUnitData;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BaseUnit;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends Activity implements OnChartValueSelectedListener {
    public static final String TAG = "BarChartActivity";
    private BarChart mChart;
    private OnValueSelectedListener mListener;
    private BarChartVO mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(EUExUtil.getResLayoutID("plugin_uexchart_barchart_layout"));
        mChart = (BarChart) findViewById(EUExUtil.getResIdID("plugin_uexchart_barchart"));
        mChart.setOnChartValueSelectedListener(this);
        if (getIntent() != null){
            mListener = (OnValueSelectedListener) getIntent()
                    .getSerializableExtra(JsConst.PARAMS_LISTENER);
            mData = (BarChartVO) getIntent().getSerializableExtra(JsConst.PARAMS_DATA_VO);
        }
        if (mListener == null || mData == null){
            return;
        }
        initChartView(mData);
    }

    private void initChartView(BarChartVO chart){
        // disable the drawing of values
        mChart.setDrawYValues(chart.isShowValue());

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setValueFormatter(new LargeValueFormatter());

        mChart.setDrawBarShadow(false);
        
        mChart.setDrawHorizontalGrid(false);
        
        
        mChart.setDrawGridBackground(false);
        mChart.setDescription(chart.getDesc());

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.animateY(chart.getDuration());
        
        //djf
        mChart.setDrawUnitsInChart(chart.isShowUint());
        mChart.setUnit(chart.getUnit());
        mChart.setBackgroundColor(chart.getBgColor());
        mChart.setDrawLegend(chart.isShowLegend());//is show legend(tuli)
        mChart.setValueTextColor(chart.getValueTextColor());
        mChart.setDescriptionTextColor(chart.getDescTextColor());
        mChart.setDescriptionTextSize(chart.getDescTextSize());
        mChart.setValueTextSize(chart.getValueTextSize());
        setData(chart.getBars());
        Legend l = mChart.getLegend();
        l.setTextColor(chart.getDescTextColor());
        l.setTextSize(chart.getDescTextSize());
        l.setPosition(chart.getLegendPosition());
        
        XLabels xl  = mChart.getXLabels();
        xl.setCenterXLabelText(true);
    }

    private void setData(List<BarUnitData> list) {
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
    public void onNothingSelected() {
        
    }

    @Override
    public void onValueSelected(Entry arg0, int arg1) {
        if(mListener != null){
            mListener.onValueSelected(arg0.getVal());
        }
    }

}
