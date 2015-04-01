package com.anochat;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.anochat.concurrent.Task;
import com.anochat.concurrent.ThreadPoolManager;
import com.anochat.node.Node;
import com.anochat.transport.TCPConnection;
import com.anochat.transport.TCPServer;
import com.anochat.wireformats.Event;
import com.anochat.wireformats.NodeSendsMessage;
import com.anochat.wireformats.Protocol;

public class AnochatServer implements Node {
	
	private final ThreadPoolManager threadPool;
	private TCPServer tcpServer;
	
	private Map<String, TCPConnection> connections
		= new HashMap<String, TCPConnection>();

	public AnochatServer(int port) throws IOException {
		threadPool = new ThreadPoolManager(8);
		tcpServer = new TCPServer(this, port);
	}
	
	@Override
	public void onConnect(Socket socket) throws IOException {
		TCPConnection conn = new TCPConnection(this, socket);
		synchronized(connections) {
			connections.put(conn.id, conn);
		}
		System.out.println("Client connected: " + conn.id);
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
	
	public void start() {
		addTask(tcpServer);
	}

	public void send(TCPConnection conn, Event e) throws IOException {
		conn.send(e.getBytes());
	}
	
	private void onMessageReceipt(NodeSendsMessage event) {
		try {
			for(String key : connections.keySet()) {
				send(connections.get(key), event);
			}
		} catch(IOException ioe) {
			System.out.println("Failed to parrot message!");
		}
	}
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: java AnochatServer <serverPort>");
		}

		try {
			int port = Integer.parseInt(args[0]);
			AnochatServer server = new AnochatServer(port);
			server.start();
			System.out.println("Server started.");
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
