package org.zywx.wbpalmstar.plugin.uexchart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Legend;

import org.zywx.wbpalmstar.base.BUtility;
import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexchart.vo.PieChartVO;
import org.zywx.wbpalmstar.plugin.uexchart.vo.PieUnit;
import org.zywx.wbpalmstar.plugin.uexchart.EUExChart.OnValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends Activity implements OnChartValueSelectedListener{

    public static final String TAG = "PieChartActivity";
    private PieChart mChart;
    private OnValueSelectedListener mListener;
    private PieChartVO mData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(EUExUtil.getResLayoutID("plugin_uexchart_piechart_layout"));
        mChart = (PieChart) findViewById(EUExUtil.getResIdID("plugin_uexchart_piechart"));
        if (getIntent() != null){
            mListener = (OnValueSelectedListener) getIntent()
                    .getSerializableExtra(JsConst.PARAMS_LISTENER);
            mData = (PieChartVO) getIntent().getSerializableExtra(JsConst.PARAMS_DATA_VO);
        }
        if (mListener == null || mData == null){
            return;
        }
        initPieChartView(mData);
    }

    private void setData(List<PieUnit> list) {
        if(list == null || list.size() == 0){
            return;
        }
        int lenght = list.size();
        String[] titles = new String[lenght];
        ArrayList<Entry> entrys = new ArrayList<Entry>();
        int[] colors = new int[lenght];
        for (int i = 0; i < lenght; i++) {
            PieUnit bean = list.get(i);
            titles[i] = bean.getTitle();
            entrys.add(new Entry(bean.getValue(), i));
            colors[i] = BUtility.parseColor(bean.getColor());
        }
        PieDataSet set1 = new PieDataSet(entrys, "");
        set1.setSliceSpace(3f);
        set1.setColors(colors);
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
        mChart.setDrawLegend(chart.isShowLegend());//is show legend(tuli)
        mChart.setDrawHoleEnabled(chart.isShowCenter());//center circle part
        mChart.setDrawCenterText(chart.isShowCenter());
        mChart.setDrawXValues(chart.isShowTitle());//is show title
        mChart.setDrawYValues(chart.isShowValue());//is show value
        mChart.setDrawUnitsInChart(chart.isShowUint());
        mChart.setUnit(chart.getUnit());
        mChart.setBackgroundColor(chart.getBgColor());
        mChart.setHoleRadius(chart.getCenterRadius());
        mChart.setTransparentCircleRadius(chart.getCenterTransRadius());
        
        if(BUtility.parseColor(chart.getCenterColor()) == Color.TRANSPARENT){
            mChart.setHoleColorTransparent(true);
        }else{
            mChart.setHoleColor(BUtility.parseColor(chart.getCenterColor()));
        }
        mChart.setUsePercentValues(chart.isShowPercent());

        mChart.setValueTextColor(chart.getValueTextColor());
        mChart.setValueTextSize(chart.getValueTextSize());
        
        mChart.setDescriptionTextColor(chart.getDescTextColor());
        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        mChart.setRotationAngle(0);
        setData(chart.getData());
        Legend l = mChart.getLegend();
        l.setTextColor(chart.getDescTextColor());
        l.setPosition(chart.getLegendPosition());
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
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
