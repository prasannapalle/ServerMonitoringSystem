package com.assignment.monitoring;

import java.io.File;  // Import the File class
import java.io.IOException;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
public class LogGenerator {
public static void main(String[] args)
    {
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
//        System.out.println(ts.getTime());
        System.out.println(args[0]);
        for(int i=0;i<1;i++)
        {
            generateLog(1,args[0]);
        }
    }

    static void generateLog(int val,String dirpath) {
        try {
            String ipaddress = generateipaddress(val);
            File f = new File(dirpath + "/" + ipaddress.replace(".","_")+"_LOG");
            FileWriter fw = new FileWriter(f);
            fw.write("timestamp"+"\t"+"ip"+"\t"+"cpu_id"+"\t"+"usage"+"\n");
            for(int i=0;i<10;i++)
            {
                long timeinlong =  getTimestamp(i);
                fw.write(timeinlong+"\t"+ipaddress+"\t"+0+"\t"+generaterandomnumber()+"\n");
                fw.write(timeinlong+"\t"+ipaddress+"\t"+1+"\t"+generaterandomnumber()+"\n");
            }
            fw.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static long getTimestamp(int num)
    {
        Date futureDate = new Date();
        try {
            Date date = new Date();
//            System.out.println(date);

            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd/MM/yyyy");
            Date dt = formatter.parse(formatter.format(date));
            futureDate = new Date(dt.getTime() + num * 60000);
            System.out.print(futureDate);
        }
        catch (ParseException parseException)
        {
           parseException.printStackTrace();

        }
        return new Timestamp(futureDate.getTime()).getTime();
    }

    private static String generateipaddress(int num)
    {
    return "192.115"+"."+String.valueOf(num/256)+"."+String.valueOf(num%256);
    }

    private static int generaterandomnumber()
    {
        Random random = new Random();
        int low = 0;
        int high = 101;
        return random.nextInt(high-low)+low;
    }

}
