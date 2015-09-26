package com.anochat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.anochat.concurrent.Task;
import com.anochat.concurrent.ThreadPoolManager;
import com.anochat.node.Node;
import com.anochat.transport.TCPConnection;
import com.anochat.wireformats.Event;
import com.anochat.wireformats.NodeSendsMessage;
import com.anochat.wireformats.Protocol;

public class AnochatClient implements Node, Runnable {
	
	private final Object lock;
	private final ThreadPoolManager threadPool;
	private TCPConnection server;
	
	public AnochatClient() {
		lock = new Object();
		threadPool = new ThreadPoolManager(8);
	}
	
	public void connectToServer(String serverUrl, int port) throws IOException {
		server = new TCPConnection(this, new Socket(serverUrl, port));
	}
	
	@Override
	public void onConnect(Socket socket) throws IOException {
		// unused
	}
	
	@Override
	public void onDisconnect(String id) throws IOException {
		System.out.println("Disconnected from server!");
	}
	
	@Override
	public void onEvent(Event event) {
		switch(event.getType()) {
		case Protocol.NODE_SENDS_MESSAGE: onMessageReceipt((NodeSendsMessage)event); break;
		}
	}
	
	@Override
	public void addTask(Task task) {
		threadPool.addTask(task);
	}
	
	public void onMessageReceipt(NodeSendsMessage event) {
		System.out.println(event.message);
	}
	
	public void sendMessage(String message) throws IOException {
		server.send(new NodeSendsMessage(message).getBytes());
	}

	@Override
	public void run() {
		try {
			connectToServer("localhost", 10865);
			synchronized(lock) {
				lock.wait();
			}
		} catch(Exception e) {
		} 
	}

	public void stop() throws InterruptedException {
		synchronized(lock) {
			lock.notify();
		}
		System.out.println("Stopped!");
	}

	
	public static void main(String[] args) {
		//try {
			/*
			if(args.length < 2) {
				System.out.println("Please provide server and port");
				System.exit(1);
			}
			*/
			//String server = args[0];
			//int port = Integer.parseInt(args[1]);
			AnochatClient client = new AnochatClient();
			new Thread(client).start();
			
			//client.connectToServer(server, port);
			/*
			Scanner keyboard = new Scanner(System.in);
			String message = keyboard.nextLine();
			while(message != null && !message.equalsIgnoreCase("quit")) {
				client.sendMessage(message);
				message = keyboard.nextLine();
			}
			*/
		/*
		} catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		*/
	}
}
