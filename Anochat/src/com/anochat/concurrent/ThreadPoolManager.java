package com.anochat.concurrent;


public class ThreadPoolManager {

	private final ThreadPool pool;
	
	public ThreadPoolManager(int numWorkers) {
		pool = new ThreadPool(numWorkers);
		pool.start();
	}
	
	public void addTask(Task task) {
		pool.queueTask(task);
	}
}
