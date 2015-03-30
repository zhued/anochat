package com.anochat.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.anochat.node.Node;

public class TCPServer implements Runnable {

	private final Node node;
	private final ServerSocket socket;
	private final int port;
	private boolean running = false;
	
	public TCPServer(Node node, int port) throws IOException {
		this.node = node;
		this.port = port;
		this.socket = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		if(running) {
			System.out.println("TCPServer already running!");
			return;
		}

		running = true;
		try {
			while(running) {
				Socket newSocket = socket.accept();
				node.onConnect(newSocket);
			}
		} catch(IOException ioe) {
			System.out.println("Error: " + ioe.getMessage());
		}
	}
}
