package com.assignment.monitoring;

import org.unix4j.Unix4j;
import org.unix4j.line.Line;
import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class QueryTool {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Any of the below Commands: ");
        System.out.println("1. Enter Query in Format : QUERY <I-P Address> <CPU-ID> <Start_date(MM-DD-YYYY HH:MM)> <End_date(MM-DD-YYYY HH:MM)>");
        System.out.println("2. exit : To exit from the application");
        String input = sc.nextLine();
        while(true)
        {
            if(input.toLowerCase().equals("exit"))
            {
                break;
            }
            else if(args.length == 6)
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
            else{
                System.out.println("Incorrect Commands : <Enter Any of the below Commands>: ");
                System.out.println("1. Enter Query in Format : QUERY <I-P Address> <CPU-ID> <Start_date(MM-DD-YYYY HH:MM)> <End_date(MM-DD-YYYY HH:MM)>");
                System.out.println("2. Enter exit to exit from the application");
            }
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
