package com.labb_1.classes;

public class DataArray {
    public final int[] dataArray;
    public final int width;
    public final int height;

    public DataArray(int width, int height){

        this.dataArray = new int[width * height];
        this.width = width;
        this.height = height;

    }
 
    public DataArray(int[] dataArray, int width, int height){

      this.dataArray = dataArray;
      this.width = width;
      this.height = height;

    }
 
    public int getDataArray(int x, int y){

        return dataArray[y * width + x];

    }
 
    public void setDataArray(int x, int y, int value){

        dataArray[y * width + x] = value;
        
    }

}
