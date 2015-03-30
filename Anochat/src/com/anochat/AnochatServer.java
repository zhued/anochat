package com.anochat;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.anochat.node.Node;
import com.anochat.transport.TCPConnection;
import com.anochat.transport.TCPServer;
import com.anochat.wireformats.Event;
import com.anochat.wireformats.NodeSendsMessage;
import com.anochat.wireformats.Protocol;

public class AnochatServer implements Node {
	
	private TCPServer tcpServer;
	private Thread tcpServerThread;
	
	private Map<String, TCPConnection> connections
		= new HashMap<String, TCPConnection>();

	public AnochatServer(int port) throws IOException {
		tcpServer = new TCPServer(this, port);
		tcpServerThread = new Thread(tcpServer);
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
	
	public void start() {
		tcpServerThread.start();
	}
	
	public void onMessageReceipt(NodeSendsMessage event) {
		System.out.println(event.message);
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
