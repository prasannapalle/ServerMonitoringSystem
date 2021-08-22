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
        try {
            Scanner sc = new Scanner(System.in);


            while (true) {
                System.out.println("Enter Any of the below Commands: ");
                System.out.println("1. Enter Query in Format : QUERY <I-P Address> <CPU-ID> <Start_date(MM-DD-YYYY HH:MM)> <End_date(MM-DD-YYYY HH:MM)>");
                System.out.println("2. exit : To exit from the application");
                String input = sc.nextLine();
                if (input.toLowerCase().equals("exit")) {
                    break;
                } else if (input.split(" ").length == 7) {

                    String[] inputarr = input.split(" ");
                    String path = args[0];
                    String ipaddress = inputarr[1];
                    String cpuid = inputarr[2];
                    String time_start = inputarr[3] + " " + inputarr[4];
                    String time_end = inputarr[5] + " " + inputarr[6];

                    ArrayList<String> solutionSet = searchLogs(path, ipaddress, cpuid, time_start, time_end);
                    System.out.println("CPU" + cpuid + " " + "usage on " + ipaddress + " :");
                    for (String str : solutionSet) {
                        System.out.print(str + "\t");
                        System.out.println();
                    }
                    continue;
                } else {
//                    System.out.println(input.split(" ").length);
                    System.out.println("Incorrect Commands :");

                    continue;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    static ArrayList<String> searchLogs(String path, String ipaddress, String cpuid, String time_start, String time_end) {
        ArrayList<String> logArray = new ArrayList<String>();
        try {
            long time_start_in_long = getTimestampLong(time_start);
            SimpleDateFormat smp = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            Date dt1 = smp.parse(time_start);
            Date dt2 = smp.parse(time_end);
            long diffMs = dt2.getTime() - dt1.getTime();
            long diffSec = diffMs / 1000;
            long mindifference = diffSec / 60;
//            System.out.println(mindifference);
            long time_end_in_long = getTimestampLong(time_end);
            String filename = getFilename(ipaddress);
            File file = new File(path + "/" + filename);

            if (file.exists()) {
                for(int i=0;i<mindifference;i++)
                {
                    Date dt = new Date(dt1.getTime() + i * 60000);
                    String timeval = smp.format(dt1.getTime()+i*6000);
                    long  ts = new Timestamp(dt.getTime()).getTime();
                    List<Line> linestart = Unix4j.grep(String.valueOf(ts), file).toLineList();
                    logArray.add(extract(linestart,cpuid,timeval));
                }


//               logArray.add(extract(linesend,cpuid,time_end));


            }
        }
        catch (Exception exception){
           exception.printStackTrace();
        }
        return logArray;
    }

    private static String extract(List<Line> lines,String  cpuid,String time_start) {
        String log = "";
        for (Line line : lines) {

//            System.out.println("Line is " + line.getContent());
            String[] strings = line.getContent().split("\t");
//            System.out.println(strings[2] + " " + cpuid);
            if (strings[2].equals(cpuid)) {
                log = "(" + time_start + "," + " " + strings[3] + "%" + ")";
            }
        }
        return log;

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
