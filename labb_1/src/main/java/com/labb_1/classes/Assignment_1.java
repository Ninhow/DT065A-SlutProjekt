package com.labb_1.classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Assignment_1 {

    public Assignment_1(){
        
    }
    
    public static DataArray[] getRGBFromImage(String filenName) throws IOException{
        BufferedImage inputImage = ImageIO.read(new FileInputStream(filenName));
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        int[] rgbData = inputImage.getRGB(0, 0, width, height, null, 0, width);

        DataArray reds = new DataArray(width, height);
        DataArray greens = new DataArray(width, height);
        DataArray blues = new DataArray(width, height);

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                int rgbValue = rgbData[y * width + x];
                reds.setDataArray(x, y, (rgbValue >>> 16) & 0xFF);
                greens.setDataArray(x, y, (rgbValue >>> 8) & 0xFF);
                blues.setDataArray(x, y, rgbValue & 0xFF);
            }
        }
        return new DataArray[] { reds, greens, blues };
    }


    private static int bound(int value, int endIndex)
    {
      if (value < 0)
        return 0;
      if (value < endIndex)
        return value;
      return endIndex - 1;
    }

    public static DataArray convolution(DataArray inputData, KernelArray kernel, int kernelSum){
        int inputWidth = inputData.width;
        int inputHeight = inputData.height;
        int kernelWidth = kernel.width;
        int kernelHeight = kernel.height;

        if ((kernelWidth <= 0) || ((kernelWidth & 1) != 1))
        throw new IllegalArgumentException("Kernel must have odd width");

        if ((kernelHeight <= 0) || ((kernelHeight & 1) != 1))
        throw new IllegalArgumentException("Kernel must have odd height");

        int kernelWidthRadius = kernelWidth >>> 1;
        
        int kernelHeightRadius = kernelHeight >>> 1;
        
    
        DataArray outputData = new DataArray(inputWidth, inputHeight);
        for (int i = inputWidth - 1; i >= 0; i--){
            for (int j = inputHeight - 1; j >= 0; j--)
            {
                double newValue = 0.0;
                for (int kw = kernelWidth - 1; kw >= 0; kw--){
                    for (int kh = kernelHeight - 1; kh >= 0; kh--){
                        float test = kernel.getDataArray(kw, kh);
                        int test2 = inputData.getDataArray(bound(i + kw - kernelWidthRadius, inputWidth), bound(j + kh - kernelHeightRadius, inputHeight));
                        float smthElse = (test * test2);
                        newValue += smthElse;
                    }
                }
                
                outputData.setDataArray(i, j, (int)Math.round(newValue / kernelSum));
            }
        }
        return outputData;
    }

    public static void writeOutputImage(String filename, DataArray[] redGreenBlue) throws IOException
    {
    DataArray reds = redGreenBlue[0];
    DataArray greens = redGreenBlue[1];
    DataArray blues = redGreenBlue[2];
      BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
                                                    BufferedImage.TYPE_INT_ARGB);
      for (int y = 0; y < reds.height; y++)
      {
        for (int x = 0; x < reds.width; x++)
        {
          int red = bound(reds.getDataArray(x, y), 256);
          int green = bound(greens.getDataArray(x, y), 256);
          int blue = bound(blues.getDataArray(x, y), 256);
          outputImage.setRGB(x, y, (red << 16) | (green << 8) | blue | -0x01000000);
        }
      }
      ImageIO.write(outputImage, "png", new File(filename));
      return;
    }
    public static void main(String[] args) throws IOException
    {
        int kernelWidth = 3;
        int kernelHeight = 3;
        int kernelDivisor = 9;
        System.out.println("Kernel size: " + kernelWidth + "x" + kernelHeight + ", divisor=" + kernelDivisor);

        KernelArray kernel = new KernelArray(kernelWidth, kernelHeight);
        float number = (float) (1);
        System.out.println(number);
        int[][] kernelArr= {{0, -1, 0},{-1, 5, -1}, {0, -1, 0}};
        boolean blur = true;
        for (int i = 0; i < kernelHeight; i++)
        {
            System.out.print("[");
            for (int j = 0; j < kernelWidth; j++)
            {
            if(blur){
                kernel.setDataArray(j, i, (float) (kernelArr[j][i] * (1.0/16.0)));
            }else{
                kernel.setDataArray(j, i, (float) (kernelArr[j][i]));
            }
            
            System.out.print(" " + kernel.getDataArray(j, i) + " ");
            }
            System.out.println("]");
        }

        float DivisorKernel = 0;
        for(float kernelValue :kernel.getData()){
            DivisorKernel += kernelValue;
        }
        
        DataArray[] dataArrays = getRGBFromImage("/home/ninhow/DT065A-IoT_Protocols/labb_1/src/main/java/com/labb_1/classes/test.png");
        for (int i = 0; i < dataArrays.length; i++){
            dataArrays[i] = convolution(dataArrays[i], kernel, (int)DivisorKernel);
        }
        
        writeOutputImage("/home/ninhow/DT065A-IoT_Protocols/labb_1/src/main/java/com/labb_1/classes/result2.jpg", dataArrays);
      return;
    }
}
