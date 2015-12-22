package org.zywx.wbpalmstar.plugin.uexchart.vo;

import java.io.Serializable;

public class OptionVO implements Serializable{
    private static final long serialVersionUID = 6460834326238376071L;
    private float initZoomX = 1;
    private float initZoomY = 1;
    private float initPositionX = 0;
    private float initPositionY = 0;
    private boolean isSupportDrag = true;
    private boolean isSupportZoomX = true;
    private boolean isSupportZoomY = true;

    public boolean isSupportDrag() {
        return isSupportDrag;
    }

    public void setIsSupportDrag(boolean isSupportDrag) {
        this.isSupportDrag = isSupportDrag;
    }

    public boolean isSupportZoomX() {
        return isSupportZoomX;
    }

    public void setIsSupportZoomX(boolean isSupportZoomX) {
        this.isSupportZoomX = isSupportZoomX;
    }

    public boolean isSupportZoomY() {
        return isSupportZoomY;
    }

    public void setIsSupportZoomY(boolean isSupportZoomY) {
        this.isSupportZoomY = isSupportZoomY;
    }

    public float getInitZoomX() {
        return initZoomX;
    }

    public void setInitZoomX(float initZoomX) {
        this.initZoomX = initZoomX;
    }

    public float getInitZoomY() {
        return initZoomY;
    }

    public void setInitZoomY(float initZoomY) {
        this.initZoomY = initZoomY;
    }

    public float getInitPositionX() {
        return initPositionX;
    }

    public void setInitPositionX(float initPositionX) {
        this.initPositionX = initPositionX;
    }

    public float getInitPositionY() {
        return initPositionY;
    }

    public void setInitPositionY(float initPositionY) {
        this.initPositionY = initPositionY;
    }
}
