package com.anochat.node;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import com.anochat.transport.TCPConnection;
import com.anochat.transport.TCPServer;

public class ServerNode implements Node {

	private HashMap<String, TCPConnection> connections;
	private TCPServer server;
	
	public ServerNode(int port) throws IOException {
		connections = new HashMap<String, TCPConnection>();
		server = new TCPServer(this, port);
		new Thread(server).start();
		System.out.println("Server is listening for connections.");
	}

	/* Node Interface */
	@Override
	public synchronized void addConnection(Socket socket) throws IOException {
		String nodeId = socket.getInetAddress()
			+ ":" + socket.getPort();
		connections.put(nodeId, new TCPConnection(this, socket));
		System.out.println("Client connected: " + nodeId);
	}

	@Override
	public synchronized void onEvent(byte[] data) throws IOException {
		String message = new String(data);
		System.out.println(message);
	}
	/* End Node Interface */

	public static void main(String[] args) {
		try {
			int port = Integer.parseInt(args[0]);
			ServerNode server = new ServerNode(port);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
