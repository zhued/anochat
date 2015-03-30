package com.anochat.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.anochat.node.Node;
import com.anochat.wireformats.EventFactory;

public class TCPReceiver implements Runnable {

	private final Node node;
	private final Socket socket;
	private final DataInputStream dataInputStream;
	private boolean running = false;
	
	public TCPReceiver(Node node, Socket socket) throws IOException {
		this.node = node;
		this.socket = socket;
		this.dataInputStream = new DataInputStream(socket.getInputStream());
	}
	
	public void run() {
		try {
			receive();
		} catch(IOException ioe) {
			System.out.println("Error: " + ioe.getMessage());
		}
	}
	
	private void receive() throws IOException {
		if(running) {
			System.out.println("TCPReceiver is already running!");
			return;
		}

		running = true;
		while(running) {
			int dataLength = dataInputStream.readInt(); // blocking call
			byte[] data = new byte[dataLength];
			dataInputStream.readFully(data, 0, dataLength); // blocking call
			
			node.onEvent(EventFactory.getInstance().createEvent(data));
		}
	}
}
