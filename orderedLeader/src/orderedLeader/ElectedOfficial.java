package orderedLeader;

import java.util.*;
import java.util.Random; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class ElectedOfficial {
	//maybe have a global list containing ranks? in this way, the elected officials can access the list once notified
	HashMap<String,Integer> hm=new HashMap<String,Integer>(); 
	
	//don't think you need to return a future object, so keep it a Runnable for now.
	public static class eOfficial implements Runnable{
		//store identifying name and integer rank value
		private String id;
		private int rankValue;
		private String leader;
		//might need to include rankThread as input so that you can notify it.
		rankThread rThread;
		
		public eOfficial(String name, rankThread r) {
			this.id = name;
			Random rand = new Random();
			int rank = rand.nextInt();
			this.rankValue = rank;
			this.rThread = r;
			this.leader = name;
		}
		public void run() {
			//print name, rank and who they think is the leader (initially they think they are the leader)
			System.out.println("ID: " + this.id + ", Rank: " + this.rankValue + ", Leader?: " + this.leader);
			//notify rank thread that a new official has been created using an interrupt
			/*
			this.rThread.notify();
			//wait for notifyAll() from rank thread
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//once notified
			*/
		}
	}
	
	public static class rankThread implements Runnable{
		//rank thread needs to know how many elected officials? 
		private int n;
		public rankThread(int num) {
			this.n = num;
		}
		public void run() {
			int i = 0;
			while (i < this.n) {
				//test to see if we can access rankThread run()
				System.out.println("test");
				i++;
				/*
				try {
					//wait for notify from other threads
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//obtain name and rank from thread, store in hashmap
				//search through hashmap for highest rank
				//notifyAll threads */
			}
		}
	}
	
	public static void main(String[] args) throws ExecutionException, InterruptedException{
		System.out.println("How many elected officials do you want?");
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		int i = 1;
		//start rank thread
		rankThread r = new rankThread(n);
		executor.execute(r);
		//start elected officials threads
		while (i <= n) {
			executor.execute(new eOfficial(Integer.toString(i), r));
			i++;
			Thread.sleep(1000);
		}
		
		executor.shutdown();
		scan.close();
	}
	

}
