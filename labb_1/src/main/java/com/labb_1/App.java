package com.labb_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.labb_1.classes.UDPClient;
import com.labb_1.models.Message;
import com.labb_1.models.OptionCode;
import com.labb_1.models.ResponseCode;
import com.labb_1.models.TypeCode;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
       UDPClient client =  new UDPClient();

       Boolean running = true;
       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

       while (running) {
           Map<OptionCode, String> options = new HashMap<OptionCode, String>();
           System.out.println("CoAP Client - Choose command");
           System.out.println("----------------------" + "\n");
           System.out.println("1. GET");
           System.out.println("2. POST");
           System.out.println("3. PUT");
           System.out.println("4. DELETE");
           System.out.println("5. EXIT");
           String input = reader.readLine();
           switch (input) {
               case "1":
                    System.out.println("Enter path to the resource you whant to get:");
                    String getPath = reader.readLine();
                    options.put(OptionCode.URI_PATH, getPath); 
                    Message get = new Message(1, TypeCode.CON, 0, ResponseCode.GET, 1, options, "");
                    //byte [] get = {(byte)0x40 , (byte)0x01, (byte)0x00, (byte)0x01, (byte)0xb4, (byte)0x74, (byte)0x65, (byte)0x73, (byte)0x74};
                    client.sendMessage(get);
                    break;
               case "2":
                    System.out.println("Enter path to be posted:");
                    String postPath = reader.readLine();
                    System.out.println("Enter value to be posted:");
                    String postValue = reader.readLine();
                    options.put(OptionCode.URI_PATH, postPath); 
                    Message post = new Message(1, TypeCode.CON, 0, ResponseCode.POST, 2, options, postValue);
                    //byte [] post = {(byte)0x40 , (byte)0x02, (byte)0x00, (byte)0x02, (byte)0xb4, (byte)0x73, (byte)0x69, (byte)0x6E, (byte)0x6B, (byte) 0xFF, (byte) 0x68, (byte) 0x65, (byte) 0x6A};
                    client.sendMessage(post);
                    break;
               case "3":
                    System.out.println("Enter path to be put:");
                    String putPath = reader.readLine();
                    System.out.println("Enter value to be put:");
                    String putValue = reader.readLine();
                    options.put(OptionCode.URI_PATH, putPath); 
                    Message put = new Message(1, TypeCode.CON, 0, ResponseCode.PUT, 3, options, putValue);
                    //byte [] put = {(byte)0x40 , (byte)0x03, (byte)0x00, (byte)0x03, (byte)0xb4, (byte)0x73, (byte)0x69, (byte)0x6E, (byte)0x6B, (byte) 0xFF, (byte) 0x68, (byte) 0x65, (byte) 0x6A};
                    client.sendMessage(put);
                    break;
               case "4":
                    System.out.println("Enter path to be deleted:");
                    String deletePath = reader.readLine();
                    System.out.println("Enter value to be deleted:");
                    String deleteValue = reader.readLine();
                    options.put(OptionCode.URI_PATH, deletePath); 
                    Message delete = new Message(1, TypeCode.CON, 0, ResponseCode.DELETE, 4, options, deleteValue);
                    //byte [] delete = {(byte)0x40 , (byte)0x04, (byte)0x00, (byte)0x04, (byte)0xb4, (byte)0x73, (byte)0x69, (byte)0x6E, (byte)0x6B};
                    client.sendMessage(delete);
                    break;
               case "5":
                    running = false;
                    break;
               default:
                    System.out.println("Enter valid option" + "\n");
           }
       }
       reader.close();
    }
}
