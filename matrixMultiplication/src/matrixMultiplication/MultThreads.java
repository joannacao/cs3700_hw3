package matrixMultiplication;

import java.util.*;
import java.util.Random; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

//maybe get thread to return a long value of how long it took to complete the 
//create single threads that will complete separate tasks (they will all have access to complete matrix but only perform operations on their part
//need a signal from start of first thread to end of last thread in order to obtain time. we don't need the actual matrix answer, but store it
//in matrix C anyways

public class MultThreads implements Runnable{
	public void run() {
		//start recording start time
		//run() will execute matMult(), which will have all the working 
		//record end time
	}
	
	public static void matMult(float[][] a, float[][] b, float[][] c, int m, int n, int p) throws ExecutionException, InterruptedException{
		//basic example
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		Set<Callable<String>> callables = new HashSet<Callable<String>>();
				
		//have each thread/callable compute values for certain section of matrix and then store in c. 

		//record start time
		long startTime = System.currentTimeMillis();
		//thread 1
		callables.add(new Callable<String>() {
		    public String call() throws Exception {
		    	//matrix multiplication for 1 thread
		    	/*
		    	for (int first = 0; first < m; first++) {
		    		for (int second = 0; second < p; second++) {
		    			//c[first][second] = 0;
		    			for (int third = 0; third < n; third++) {
		    				c[first][second] += a[first][third] * b[third][second];
		    			}
		    		}
		    	} */
		    	//matrix multiplication for 2 threads (compute half of matrix)
		    	for (int first = 0; first < m; first++) {
		    		for (int third = 0; third < p; third++) {
		    			//c[first][third] = 0;
		    			for (int second = 0; second < (n/2); second++) {
		    				c[first][third] += a[first][second] * b[second][third];
		    			}
		    		}
		    	}
		        return "Task 1";			    
		    }
		});
		
		//thread 2
		callables.add(new Callable<String>() {
		    public String call() throws Exception {
		    	//comment everything below (in this function) out for running one thread
		    	for (int first = 0; first < m; first++) {
		    		for (int third = 0; third < p; third++) {
		    			//c[first][third] = 0;
		    			for (int second = ((n/2) +1); second < n; second++) {
		    				c[first][third] = a[first][second] + b[second][third];
		    			}
		    		}
		    	}
		        return "Task 2";
		    }
		});
				
		//list of results
		List<Future<String>> futures = executorService.invokeAll(callables);

		/*
		for(Future<String> future : futures){
		    System.out.println("future.get = " + future.get());
		} */

		//record endtime
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime; 
		//System.out.println("Time for 1 thread: " + time + " ms");
		System.out.println("Time for 2 threads: " + time + " ms");
		executorService.shutdown();
	}
	
	public static void main(String[] args) throws ExecutionException, InterruptedException{
		//create matrices, then call matMult. matMult will have thread creation inside.
		Random rand = new Random(); 
		float[][] a = new float[15][10]; 
		float[][] b = new float[10][17];
		float[][] c = new float[15][17];
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 10; col++) {
				a[row][col] = rand.nextFloat();
			}
		}
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 17; col++) {
				b[row][col] = rand.nextFloat();
			}
		}
		//initialize c to be zero
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 17; col++) {
				c[row][col] = 0;
			}
		}
		matMult(a,b,c,15,10,17);
	}

}
