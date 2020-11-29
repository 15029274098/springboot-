package org.example.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.csvreader.CsvReader;

/*
 *包名:com.kdzwy.cases
 *作者:Adien_cui
 *时间:2017-9-25  下午4:36:29
 *描述:读取csv文件
 **/
public class ReadCsvFile {
    public static void readCsvFile(String filePath){
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath,',',Charset.forName("UTF8"));
          //  reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues());
                //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            System.out.println("读取的行数："+csvList.size());

            for(int row = 0; row < csvList.size(); row++){
                System.out.println("-----------------");
                System.out.println();
                //打印每一行的数据

                for (int i = 0; i < 10; i++) {

                    if (csvList.get(row)[i] == null || csvList.get(row)[i].equals("")) {
                        System.out.print(0);
                    } else {
                        System.out.print(csvList.get(row)[i]);
                    }
                    System.out.println();
                }
              /*  System.out.print(csvList.get(row)[1]+",");
                System.out.print(csvList.get(row)[2]+",");
                System.out.print(csvList.get(row)[3]+",");
                System.out.print(csvList.get(row)[4]+",");*/
                System.out.println("-----------------");
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Administrator\\Desktop\\sys_user.csv";
        readCsvFile(filePath);
    }
}