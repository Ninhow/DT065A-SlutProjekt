package com.labb_1.classes;

public class KernelArray {
    public final float[] dataArray;
    public final int width;
    public final int height;

    public KernelArray(int width, int height){

        this.dataArray = new float[width * height];
        this.width = width;
        this.height = height;

    }
 
    public KernelArray(float[] dataArray, int width, int height){

      this.dataArray = dataArray;
      this.width = width;
      this.height = height;

    }
 
    public float getDataArray(int x, int y){

        return dataArray[y * width + x];

    }
 
    public void setDataArray(int x, int y, float value){

        dataArray[y * width + x] = value;
        
    }

    public float[] getData(){
        return dataArray;
    }

}
