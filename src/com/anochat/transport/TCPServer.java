package com.anochat.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.anochat.node.Node;


public class TCPServer implements Runnable {

	private ServerSocket serverSocket;
	private Node node;

	public TCPServer(Node node, int port) throws IOException {
		serverSocket = new ServerSocket(port);
		this.node = node;
	}

	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				Socket socket = serverSocket.accept();
				node.addConnection(socket); // this is a callback defined in the Node interface 
			}
		} catch(IOException ioe) { /* eat it for now */ }
	}
}
