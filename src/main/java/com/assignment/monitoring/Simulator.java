package com.assignment.monitoring;

import picocli.CommandLine;

@CommandLine.Command
 class Simulator implements Runnable {
    public static void main(String[] args)
    {
        CommandLine.run(new Simulator(),args);
    }

     @Override
     public void run() {
        System.out.print("Simulator command");
     }

     @CommandLine.Command(name = "query")
    public void QueryLog()
     {
         System.out.println("Enter Query");
     }

     @CommandLine.Command(name = "exit")
    public void ExitQuery()
     {
         System.out.println("Exit Query");
     }
 }
