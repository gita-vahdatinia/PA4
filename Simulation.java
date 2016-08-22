//---------------------------------------------------------------
// Guita Vahdatinia
// gvahdati
// 12B
// 5/13/16
// takes in jobs and processors and determines the time and order to complete 
// Simulation.java
// ----------------------------------------------------------------
import java.io.*;
import java.util.Scanner;

public class Simulation{


 public static Job getJob(Scanner in){
      String[] s = in.nextLine().split(" ");
      int a = Integer.parseInt(s[0]);
      int d = Integer.parseInt(s[1]);
      return new Job(a,d);
   }

   public static void sort(int[] tim){
      for(int k = tim.length-1; k>0;k--){
         for(int j = 1; j<=k; j++){
            if(tim[j]<tim[j-1]){
               int temp = tim[j];
               tim[j] = tim[j-1];
               tim[j-1] = temp;
            }
         }
      }
   }

   public static void procJob(Queue[] proc, int t){
      if(proc.length-1==1){
         if(!proc[0].isEmpty()){
            Job J = ((Job)proc[0].peek());
            if(J.getArrival()==t){
               proc[1].enqueue(proc[0].dequeue());
            }
         }
      }else{
         if(!proc[0].isEmpty()){
            Job J = ((Job)proc[0].peek());
            if(J.getArrival()==t){
               int[] lengths = new int[proc.length-1];
               for(int i = 0; i<lengths.length;i++){
                  lengths[i] = proc[i+1].length();
               }
               int index = 0;
               int min = lengths[0];
               for(int i = 1; i<lengths.length; i++){
                  if(lengths[i]<min){
                     min = lengths[i];
                     index = i;
                  }
               }
               proc[index+1].enqueue(proc[0].dequeue());
            }
         }
      }
   }      

   public static int nextT(Queue[] proc, int t){
      int[] times= new int[proc.length];
      if(t==0 && !proc[0].isEmpty()){
         t = ((Job)proc[0].peek()).getArrival();
         return t;
      }else if(!proc[0].isEmpty()){
         Job J = ((Job)proc[0].peek());
         if(J.getFinish()==-1){
            times[0] = J.getArrival();
         }
      }
      for(int i = 1; i<proc.length; i++){
         if(!proc[i].isEmpty()){
            Job P = ((Job)proc[i].peek());
            times[i] = P.getFinish();
         }
      }
   
      sort(times);

      for(int i = 0; i<times.length; i++){
         if(times[i]==0) continue;
         if(times[i]!=0){
            t = times[i];
            break;
         }
      }
      return t;
   }


    public static void main(String[] args) throws IOException{
    // check command line 
    if(args.length != 1){
        System.err.println("Usage: Simulation file");
        System.exit(1);
    }

    //open in and out files 
    Scanner in = new Scanner(new File(args[0]));
    PrintWriter rpt = new PrintWriter(new FileWriter(args[0]+".rpt"));
    PrintWriter trc = new PrintWriter(new FileWriter(args[0]+".trc"));
    //read in m jobs
    String s = in.nextLine();
    int m = Integer.parseInt(s);
    String star = "*****************************";
    //time variables for output
    int totalT, maxT;
    double averageT;
    // make storage Queue
    Queue storage = new Queue();
    while(in.hasNext()){
        storage.enqueue((Job)getJob(in));
    }

    // heading of files 
    trc.println("Trace file: " + args[0] + ".trc");
    trc.println(m + " Jobs:");
    trc.println(storage.toString());
    trc.println();

    rpt.println("Report file: " + args[0] + ".rpt");
    rpt.println(m + " Jobs:");
    rpt.println(storage.toString());
    rpt.println();
    rpt.println("***********************************************************");
    //from n to m processors
    for(int n = 1; n<m; n++){
        // print out the number of processors 
       trc.println("*****************************");
         trc.println(n==1? n+" processor:": n+" processors:");
         trc.println("*****************************");  
        //array of processor queues 
        Queue[] proc = new Queue[n+1];
        for(int j = 0; j<=n; j++){
            proc[j] = new Queue();
        }
          
        // copy storage into storage array
        for(int j = 0; j<m; j++){
            Job J = (Job)storage.dequeue();
            J.resetFinishTime();
            proc[0].enqueue(J);
            storage.enqueue(J);
        }
    int time = 0;

    //checking while there are still unfinished jobs
    while(proc[0].isEmpty() || ((Job)proc[0].peek()).getFinish()==-1 
        || proc[0].length()!=m){

        //prints out for when time is equal to 0
        if(time==0){
            trc.println("time=0");
                for(int j = 0; j<=n; j++){
                    trc.println(j+": " + proc[j].toString());
            //        System.out.println(n+"     " +proc[j].toString());
                }
                trc.println();
        }   
           
        //find the next time 
        time = nextT(proc,time);
        
        // finsh upcoming processor 
        for(int j = 1; j<=n; j++){
            if(!proc[j].isEmpty() && ((Job)proc[j].peek()).getFinish()==time){
                proc[0].enqueue(proc[j].dequeue());
                if(proc[j].length()>0){
                    if(((Job)proc[j].peek()).getFinish()==-1){
                        Job top = ((Job)proc[j].peek());
                            top.computeFinishTime(time);
                    }
                }
            }
        }
  
            // put job in a procesor
            procJob(proc, time);
            for(int j = 1; j<proc.length; j++){
               if(!proc[j].isEmpty()){
                  Job top = ((Job)proc[j].peek());
                  if(top.getFinish()==-1) top.computeFinishTime(time);
               }
            }
            
            // printing out for the trace file 
            trc.println("time=" +time);
            for(int j = 0; j<=n; j++){
               trc.println(j+": " + proc[j].toString());
            }
            trc.println();
            
         }
         //place times in an array 
         int[] times = new int[proc[0].length()];
         totalT = 0;
         for(int j = 0; j<times.length; j++){
            Job temp1 = ((Job)proc[0].peek());
            times[j] = temp1.getWaitTime();
            totalT = totalT + times[j];
            proc[0].enqueue(proc[0].dequeue());
         }
         sort(times);
         maxT= times[times.length-1];
         averageT= (double)totalT/(double)times.length;
         
 
         // print info to report file
         rpt.print(n==1? n+" processor: " : n+" processors: ");
         rpt.print("totalWait="+totalT+", maxWait="+maxT);
         rpt.println(", averageWait="+String.format("%.2f", averageT));
    } 

    trc.close();
    rpt.close();
    in.close();  
    
    }   
    
}
