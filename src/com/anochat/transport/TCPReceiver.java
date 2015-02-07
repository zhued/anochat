package com.anochat.transport;

import java.io.IOException;
import java.io.DataInputStream;
import java.net.Socket;

import com.anochat.node.Node;

public class TCPReceiver implements Runnable {
	
	private Node node;
	private Socket socket;
	private DataInputStream dataInputStream;

	public TCPReceiver(Node node, Socket socket) throws IOException {
		this.node = node;
		this.socket = socket;
		dataInputStream = new DataInputStream(socket.getInputStream());
	}

	public void receive() throws IOException {
		int dataLength;
		while(true) {
			dataLength = dataInputStream.readInt();
			byte[] data = new byte[dataLength];
			dataInputStream.readFully(data, 0, dataLength);
			node.onEvent(data);
		}
	}

	public void run() {
		try {
			receive();
		} catch(IOException ioe) { /* eat for now */ }
	}
}
