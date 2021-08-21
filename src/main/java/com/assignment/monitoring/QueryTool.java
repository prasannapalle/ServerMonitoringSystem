package com.assignment.monitoring;

import org.unix4j.Unix4j;
import org.unix4j.line.Line;
import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryTool {
    public static void main(String[] args)
    {
        String path = args[0];
        String ipaddress = args[1];
        String cpuid = args[2];
        String time_start = args[3]+" "+args[4];
        String time_end = args[5]+" "+args[6];

        ArrayList<String> solutionSet = searchLogs(path,ipaddress,cpuid,time_start,time_end);
        System.out.println("CPU"+cpuid+ " " + "usage on "+ ipaddress + " :");
        for(String str : solutionSet)
        {
            System.out.print(str + "\t");
        }
    }

    static ArrayList<String> searchLogs(String path, String ipaddress, String cpuid, String time_start, String time_end) {
        ArrayList<String> logArray = new ArrayList<String>();
        try {
            long time_start_in_long = getTimestampLong(time_start);
            long time_end_in_long = getTimestampLong(time_end);
            String filename = getFilename(ipaddress);
            File file = new File(path + "/" + filename);

            if (file.exists()) {
                List<Line> lines = Unix4j.grep(String.valueOf(time_start_in_long), file).toLineList();
                for(Line line:lines)
                {
                    System.out.println("Line is "+line.getContent());
                    String[] strings = line.getContent().split("\t");
                    String log = "("+time_start+","+" "+strings[3]+"%"+")";
                    logArray.add(log);
                }

            }
        }
        catch (Exception exception){
           exception.printStackTrace();
        }
        return logArray;
    }

    private static long getTimestampLong(String timeval)
    {
        long timestamp_long;
        try {
            SimpleDateFormat smp = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            Date dt = smp.parse(timeval);
            timestamp_long = new Timestamp(dt.getTime()).getTime();
        }
        catch(ParseException parseException)
        {
            parseException.printStackTrace();
            timestamp_long = 0;
        }
        return timestamp_long;
    }

    private static String getFilename(String ipaddr)
    {
        return ipaddr.replace(".","_")+"_LOG";
    }
}
