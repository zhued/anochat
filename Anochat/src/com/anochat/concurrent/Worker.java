package com.anochat.concurrent;

public class Worker extends Thread {
	
	private final Object lock = new Object();
	private final ThreadPool pool;
	private Task task;
	private boolean isAlive = true;
	private WorkerStatus status;
	public final String defaultName;
	
	public Worker(ThreadPool pool, String defaultName) {
		super();
		this.pool = pool;
		this.defaultName = defaultName;
		this.setName(defaultName);
		this.status = WorkerStatus.IDLE;
	}
	
	@Override
	public void run() {
		try {
			while(isAlive) {
				synchronized(lock) {
						lock.wait();
						if(task != null) {
							synchronized(status) {
								status = WorkerStatus.RUNNING;
							}
							this.setName(task.name);
							task.run();
							this.setName(defaultName);
							synchronized(status) {
								status = WorkerStatus.IDLE;
							}
						}
				}
			}
		} catch(InterruptedException ie) {
			System.out.println("Worker thread wait interrupted!");
		}
	}
	
	public void runTask(Task task) {
		if(status == WorkerStatus.RUNNING) {
			System.out.println("Worker thread is already running!");
			System.out.println("Failed to run task: " + task.name);
			return;
		} 

		this.task = task;
		synchronized(lock) {
			lock.notify();
		}
	}
	
	public WorkerStatus getStatus() {
		synchronized(status) {
			return status;
		}
	}
}
