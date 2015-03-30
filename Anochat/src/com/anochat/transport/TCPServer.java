package com.anochat.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	private final ServerSocket socket;
	private final int port;
	private boolean running = false;
	
	public TCPServer(int port) throws IOException {
		this.port = port;
		this.socket = new ServerSocket(port);
	}
	
	public void run() {
		if(running) {
			System.out.println("TCPServer already running!");
			return;
		}

		running = true;
		try {
			while(running) {
				Socket newSocket = socket.accept();
				System.out.println("Client connected: " + newSocket.getInetAddress() 
						+ ":" + newSocket.getPort());
			}
		} catch(IOException ioe) {
			System.out.println("Error: " + ioe.getMessage());
		}
	}
}
