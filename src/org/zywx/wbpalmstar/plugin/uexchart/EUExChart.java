package org.zywx.wbpalmstar.plugin.uexchart;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.zywx.wbpalmstar.engine.DataHelper;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BarChartVO;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BaseChart;
import org.zywx.wbpalmstar.plugin.uexchart.vo.BaseUnit;
import org.zywx.wbpalmstar.plugin.uexchart.vo.LineChartVO;
import org.zywx.wbpalmstar.plugin.uexchart.vo.LineUnitData;
import org.zywx.wbpalmstar.plugin.uexchart.vo.PieChartVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EUExChart extends EUExBase {

    private static final String BUNDLE_DATA = "data";
    private static final int MSG_OPEN_PIE_CHART = 1;
    private static final int MSG_CLOSE_PIE_CHART = 2;
    private static final int MSG_OPEN_LINE_CHART = 3;
    private static final int MSG_CLOSE_LINE_CHART = 4;
    private static final int MSG_OPEN_BAR_CHART = 5;
    private static final int MSG_CLOSE_BAR_CHART = 6;
    private static final String TAG = "EUExChart";
    private static List<BaseChart> mIDs = new ArrayList<BaseChart>();

    public EUExChart(Context context, EBrowserView eBrowserView) {
        super(context, eBrowserView);
    }

    @Override
    protected boolean clean() {
        return false;
    }


    public void openPieChart(String[] params) {
        if (params == null || params.length < 1) {
            errorCallback(0, 0, "error params!");
            return;
        }
        Message msg = new Message();
        msg.obj = this;
        msg.what = MSG_OPEN_PIE_CHART;
        Bundle bd = new Bundle();
        bd.putStringArray(BUNDLE_DATA, params);
        msg.setData(bd);
        mHandler.sendMessage(msg);
    }

    private void openPieChartMsg(String[] params) {
        String json = params[0];
        PieChartVO pieChartVO = DataHelper.gson.fromJson(json, PieChartVO.class);
        if (pieChartVO == null){
            return;
        }
        if (isViewAlreadyAdded(getViewTAG(PieChartView.TAG, pieChartVO.getId()))){
            return;
        }
        PieChartView pieChartView=new PieChartView(mContext,pieChartVO);
        pieChartView.setmListener(listener);
        String id = getViewTAG(PieChartView.TAG, pieChartVO.getId());
        pieChartView.setTag(id);
        pieChartView.setId(id.hashCode());
        if (pieChartVO.isScrollWithWeb()){
            android.widget.AbsoluteLayout.LayoutParams lp = new
                    android.widget.AbsoluteLayout.LayoutParams(
                    pieChartVO.getWidth(),
                    pieChartVO.getHeight(),
                    pieChartVO.getLeft(),
                    pieChartVO.getTop());
            addViewToWebView(pieChartView, lp, id);
        }else{
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    pieChartVO.getWidth(), pieChartVO.getHeight());
            lp.leftMargin = pieChartVO.getLeft();
            lp.topMargin = pieChartVO.getTop();
            addView2CurrentWindow(pieChartView, lp);
        }
        pieChartVO.setId(id);
        mIDs.add(pieChartVO);
    }

    private void addView2CurrentWindow(View child,
                                       RelativeLayout.LayoutParams parms) {
        int l = parms.leftMargin;
        int t = parms.topMargin;
        int w = parms.width;
        int h = parms.height;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, h);
        lp.gravity = Gravity.NO_GRAVITY;
        lp.leftMargin = l;
        lp.topMargin = t;
        adptLayoutParams(parms, lp);
        mBrwView.addViewToCurrentWindow(child, lp);
    }

    public void closePieChart(String[] params) {
        Message msg = new Message();
        msg.obj = this;
        msg.what = MSG_CLOSE_PIE_CHART;
        Bundle bd = new Bundle();
        bd.putStringArray(BUNDLE_DATA, params);
        msg.setData(bd);
        mHandler.sendMessage(msg);
    }

    private void closePieChartMsg(String[] params) {
        closeChart(params, PieChartView.TAG);
    }

    private void closeChart(String[] params, String tag){
        List<String> list = new ArrayList<String>();
        List<String> tempList = new ArrayList<String>();
        if (params != null && params.length > 0){
            String json = params[0];
            tempList = DataHelper.gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
        }
        if (tempList == null || tempList.size() < 1){
            for (int i = 0; i < mIDs.size(); i++){
                if (mIDs.get(i).getId().contains(tag)){
                    list.add(mIDs.get(i).getId());
                }
            }
        }else{
            for (int j = 0; j < tempList.size(); j++){
                final String id = tempList.get(j);
                list.add(getViewTAG(tag, id));
            }
        }
        for (int i = 0; i < list.size(); i++){
            final String item = list.get(i);
            BaseChart chartVO = getChartFromIDs(item);
            if (chartVO != null){
                String id = chartVO.getId();
                if (isViewAlreadyAdded(id)){
                    if (chartVO.isScrollWithWeb()){
                        removeViewFromWebView(id);
                    }else{
                        removeChartView(id);
                    }
                    removeIdFormIDs(item);
                }
            }
        }
    }

    private void removeChartView(String id){
        if (id==null){
            return;
        }
        int childCount=mBrwView.getBrowserWindow().getChildCount();
        if (childCount>0){
            for (int i=childCount-1;i>=0;i--){
                View child=mBrwView.getBrowserWindow().getChildAt(i);
                if (child!=null&&child.getId()==id.hashCode()){
                    mBrwView.getBrowserWindow().removeViewAt(i);
                }
            }
        }
    }

    private void removeIdFormIDs(String id){
        Iterator<BaseChart> iterator = mIDs.iterator();
        while (iterator.hasNext()){
            BaseChart item = iterator.next();
            if (id.equals(item.getId())){
                iterator.remove();
            }
        }
    }

    private BaseChart getChartFromIDs(String id){
        BaseChart pieChartVO = null;
        Iterator<BaseChart> iterator = mIDs.iterator();
        while (iterator.hasNext()){
            BaseChart item = iterator.next();
            if (id.equals(item.getId())){
                pieChartVO = item;
            }
        }
        return pieChartVO;
    }

    public void openLineChart(String[] params) {
        if (params == null || params.length < 1) {
            errorCallback(0, 0, "error params!");
            return;
        }
        Message msg = new Message();
        msg.obj = this;
        msg.what = MSG_OPEN_LINE_CHART;
        Bundle bd = new Bundle();
        bd.putStringArray(BUNDLE_DATA, params);
        msg.setData(bd);
        mHandler.sendMessage(msg);
    }

    private void openLineChartMsg(String[] params) {
        String json = params[0];
        LineChartVO lineChartVO = DataHelper.gson.fromJson(json, LineChartVO.class);
        if (lineChartVO == null || lineChartVO.getxData() == null
                || lineChartVO.getxData().size() < 1
                || lineChartVO.getLines() == null
                || lineChartVO.getLines().size() < 1){
            return;
        }
        if (isViewAlreadyAdded(getViewTAG(LineChartView.TAG, lineChartVO.getId()))){
            return;
        }
        for (int i = 0; i < lineChartVO.getLines().size(); i++){
            final LineUnitData lineUnitData = lineChartVO.getLines().get(i);
            for (int j = 0; j < lineUnitData.getData().size(); j++){
                final BaseUnit unit = lineUnitData.getData().get(j);
                final String xValue = unit.getxValue();
                for (int m = 0; m < lineChartVO.getxData().size(); m++){
                    final String x = lineChartVO.getxData().get(m);
                    if (x.equals(xValue)){
                        unit.setIndex(m);
                    }
                }
            }
        }
        try {
            JSONObject object = new JSONObject(json);
            if (object.has(JsConst.MAX_VALUE)){
                lineChartVO.setIsHasMax(true);
            }
            if (object.has(JsConst.MIN_VALUE)){
                lineChartVO.setIsHasMin(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LineChartView lineChartView=new LineChartView(mContext,lineChartVO);
        lineChartView.setmListener(listener);
        String id = getViewTAG(LineChartView.TAG, lineChartVO.getId());
        lineChartView.setTag(id);
        lineChartView.setId(id.hashCode());
        if (lineChartVO.isScrollWithWeb()){
            android.widget.AbsoluteLayout.LayoutParams lp = new
                    android.widget.AbsoluteLayout.LayoutParams(
                    lineChartVO.getWidth(),
                    lineChartVO.getHeight(),
                    lineChartVO.getLeft(),
                    lineChartVO.getTop());
            addViewToWebView(lineChartView, lp, id);
        }else{
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    lineChartVO.getWidth(), lineChartVO.getHeight());
            lp.leftMargin = lineChartVO.getLeft();
            lp.topMargin = lineChartVO.getTop();
            addView2CurrentWindow(lineChartView, lp);
        }
        lineChartVO.setId(id);
        mIDs.add(lineChartVO);
    }

    public void closeLineChart(String[] params) {
        Message msg = new Message();
        msg.obj = this;
        msg.what = MSG_CLOSE_LINE_CHART;
        Bundle bd = new Bundle();
        bd.putStringArray(BUNDLE_DATA, params);
        msg.setData(bd);
        mHandler.sendMessage(msg);
    }

    private void closeLineChartMsg(String[] params) {
        closeChart(params, LineChartView.TAG);
    }

    public void openBarChart(String[] params) {
        if (params == null || params.length < 1) {
            errorCallback(0, 0, "error params!");
            return;
        }
        Message msg = new Message();
        msg.obj = this;
        msg.what = MSG_OPEN_BAR_CHART;
        Bundle bd = new Bundle();
        bd.putStringArray(BUNDLE_DATA, params);
        msg.setData(bd);
        mHandler.sendMessage(msg);
    }

    private void openBarChartMsg(String[] params) {
        String json = params[0];
        BarChartVO barChartVO = DataHelper.gson.fromJson(json, BarChartVO.class);
        if (barChartVO == null){
            return;
        }
        if (isViewAlreadyAdded(getViewTAG(BarChartView.TAG, barChartVO.getId()))){
            return;
        }
        BarChartView barChartView=new BarChartView(mContext,barChartVO);
        barChartView.setmListener(listener);
        String id = getViewTAG(BarChartView.TAG, barChartVO.getId());
        barChartView.setTag(id);
        barChartView.setId(id.hashCode());//view加到window里面时引擎使用了tag，因此用id来标识
        if (barChartVO.isScrollWithWeb()){
            android.widget.AbsoluteLayout.LayoutParams lp = new
                    android.widget.AbsoluteLayout.LayoutParams(
                    barChartVO.getWidth(),
                    barChartVO.getHeight(),
                    barChartVO.getLeft(),
                    barChartVO.getTop());
            addViewToWebView(barChartView, lp, id);
        }else{
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    barChartVO.getWidth(), barChartVO.getHeight());
            lp.leftMargin = barChartVO.getLeft();
            lp.topMargin = barChartVO.getTop();
            addView2CurrentWindow(barChartView, lp);
        }
        barChartVO.setId(id);
        mIDs.add(barChartVO);
    }

    public void closeBarChart(String[] params) {
        Message msg = new Message();
        msg.obj = this;
        msg.what = MSG_CLOSE_BAR_CHART;
        Bundle bd = new Bundle();
        bd.putStringArray(BUNDLE_DATA, params);
        msg.setData(bd);
        mHandler.sendMessage(msg);
    }

    private void closeBarChartMsg(String[] params) {
        closeChart(params, BarChartView.TAG);
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message == null){
            return;
        }
        Bundle bundle=message.getData();
        switch (message.what) {
            case MSG_OPEN_PIE_CHART:
                openPieChartMsg(bundle.getStringArray(BUNDLE_DATA));
                break;
            case MSG_CLOSE_PIE_CHART:
                closePieChartMsg(bundle.getStringArray(BUNDLE_DATA));
                break;
            case MSG_OPEN_LINE_CHART:
                openLineChartMsg(bundle.getStringArray(BUNDLE_DATA));
                break;
            case MSG_CLOSE_LINE_CHART:
                closeLineChartMsg(bundle.getStringArray(BUNDLE_DATA));
                break;
            case MSG_OPEN_BAR_CHART:
                openBarChartMsg(bundle.getStringArray(BUNDLE_DATA));
                break;
            case MSG_CLOSE_BAR_CHART:
                closeBarChartMsg(bundle.getStringArray(BUNDLE_DATA));
                break;
            default:
                super.onHandleMessage(message);
        }
    }

    private void callBackPluginJs(String methodName, String jsonData){
        String js = SCRIPT_HEADER + "if(" + methodName + "){"
                + methodName + "('" + jsonData + "');}";
        onCallback(js);
    }
    OnValueSelectedListener listener = new OnValueSelectedListener() {

        @Override
        public void onValueSelected(float value) {
            JSONObject result = new JSONObject();
            try {
                result.put(JsConst.VALUE, value + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            callBackPluginJs(JsConst.ON_VALUE_SELECTED, result.toString());
        }
    };

    public interface OnValueSelectedListener extends Serializable{
        public void onValueSelected(float value);
    }

    private String getViewTAG(String tag, String id){
        return tag+id;
    }
    private boolean isViewAlreadyAdded(String tag) {
        if (tag==null){
            return false;
        }
        boolean isAdded=false;
        int childCount=mBrwView.getBrowserWindow().getChildCount();
        if (childCount>0){
            for (int i=0;i<childCount;i++){

                if (tag.hashCode()==mBrwView.getBrowserWindow().getChildAt(i).getId()){
                    return true;
                }
            }
        }
        int webviewChildCount=mBrwView.getChildCount();
        if (webviewChildCount>0){
            for (int i = 0; i < webviewChildCount; i++) {
                if (tag.equals(mBrwView.getChildAt(i).getTag())){
                    return true;
                }
            }
        }
        return isAdded;
    }
}
