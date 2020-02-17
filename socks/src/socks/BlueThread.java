package socks;

import java.util.Random; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

	public class BlueThread implements Runnable{
		public void run() {
	}
	/*
	//matching thread. this will return a string of the color of the pair it matched
	public static class matchThread implements Callable{
		//constructor
		public matchThread() {
			
		}
		public String call() {
			return matching();
		}
		public String matching() {
			String 
		}
	}*/
	
		
	//threads to produce socks
	public static class bThread implements Callable{
		private String color;
		private int total;
		//constructor
		public bThread(String c) {
			this.color = c;
		}
		public String call() {
			return printSocks();
		}
		//prints sock production
		public String printSocks() {
			Random rand = new Random();
			this.total = rand.nextInt(100); 
			int counter = 1; 
			while (counter <= this.total) {
				System.out.println(this.color + " socks: Produced " + counter + " out of " + this.total + " socks.");
				counter++;
			}
			return this.color;
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException{
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		//start first four threads, and they all return a string of the color they were in charge of
		Future<String> thread1 = executor.submit(new bThread("Blue"));
		Future<String> thread2 = executor.submit(new bThread("Red"));
		Future<String> thread3 = executor.submit(new bThread("Green"));
		Future<String> thread4 = executor.submit(new bThread("Orange"));
		
		//test future object
		//String firstColor = thread1.get();
		//System.out.println(firstColor);
		executor.shutdown();
		System.out.println("Program has terminated.");
	}

}


