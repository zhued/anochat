package com.anochat.concurrent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ThreadPool extends Task {
	
	private final int numWorkers;
	private final ArrayList<Worker> workers = new ArrayList<Worker>();
	private final Queue<Task> tasks = new LinkedList<Task>();
	
	public ThreadPool(int numWorkers) {
		this.numWorkers = numWorkers;
		
		for(int i = 0; i < numWorkers; i++) {
			Worker w = new Worker(this, "Idle-" + i); // can leak this reference because 
										   // im guaranteed no work being done by workers
										   // until initialization of thread pool is complete
			w.start(); // blocks until real work can be done 
			workers.add(w);
		}
	}
	
	public void start() {
		synchronized(workers) {
			if(workers.size() > 0) {
				workers.remove(0).runTask(this);
			} else {
				System.out.println("Cannot start thread pool! Is the number of threads 0?");
			}
		}
	}
	
	public void run() {
		while(true) {
			try {
				Worker w;
				synchronized(workers) {
					while(workers.size() < 1)
						workers.wait();
					w = workers.get(0);
				}
				synchronized(tasks) {
					while(tasks.size() < 1)
						tasks.wait();
					w.runTask(tasks.remove());
				}
			} catch(InterruptedException ie) {
				System.out.println("ThreadPool runTask wait interrupted!");
			}
		}
	}
	
	public void workerFinished(Worker worker) {
		synchronized(workers) {
			workers.add(worker);
			workers.notify();
		}
	}
	
	public void queueTask(Task task) {
		synchronized(tasks) {
			tasks.add(task);
			tasks.notify();
		}
	}
}
