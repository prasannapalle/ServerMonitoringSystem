package com.assignment.monitoring;

import picocli.CommandLine;

@CommandLine.Command
 public class Simulator implements Runnable {
    public static void main(String[] args)
    {
        CommandLine.run(new Simulator(),args);
    }

     @Override
     public void run() {
        System.out.print("Simulator command");
     }

     @CommandLine.Command(name = "query")
    public static void QueryLog()
     {
         System.out.println("Enter Query in Format (YYYY-MM-DD HH:MM)");
     }

     @CommandLine.Command(name = "exit")
    public static void ExitQuery()
     {
         System.out.println("Exit Query");
     }
 }
