package com.anochat.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.anochat.concurrent.Task;
import com.anochat.node.Node;
import com.anochat.wireformats.EventFactory;

public class TCPReceiver implements Runnable {

	private final Node node;
	private final Socket socket;
	private final DataInputStream dataInputStream;
	private boolean running = false;
	
	public final String id;
	
	public TCPReceiver(Node node, Socket socket, String id) throws IOException {
		this.node = node;
		this.socket = socket;
		this.dataInputStream = new DataInputStream(socket.getInputStream());
		this.id = id;
	}
	
	public void run() {
		try {
			receive();
		} catch(IOException ioe) {
			try {
			node.onDisconnect(id);
			} catch (IOException ioe2) {
				System.err.println("Error when disconnecting: " + ioe2.getMessage());
			}
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
