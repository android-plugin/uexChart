package org.zywx.wbpalmstar.plugin.uexchart;

public class ChartUtils {
    public static int getAlpha(String inColor){
        int reColor = 0;
        try{
            if(inColor != null && inColor.length() != 0){
                inColor = inColor.replace(" ", "");
                if(inColor.toLowerCase().startsWith("rgba")){ //rgba
                    int start = inColor.indexOf('(') + 1;
                    int off = inColor.indexOf(')');
                    inColor = inColor.substring(start, off);
                    String[] rgba = inColor.split(",");
                    int a = Integer.parseInt(rgba[3]);
                    reColor = a;
                }else if(inColor.startsWith("#")){ // #
                    String tmpColor = inColor.substring(1);
                    char[] t = new char[2];
                    if(8 == tmpColor.length()){
                        t[0] = tmpColor.charAt(0);
                        t[1] = tmpColor.charAt(1);
                    }
                    reColor = Integer.parseInt(String.valueOf(t), 16);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            reColor = 0;
        }
        return reColor;
    }
}
