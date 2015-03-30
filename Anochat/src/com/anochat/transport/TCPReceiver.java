package com.anochat.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPReceiver {

	private final Socket socket;
	private final DataInputStream dataInputStream;
	private boolean running = false;
	
	public TCPReceiver(Socket socket) throws IOException {
		this.socket = socket;
		this.dataInputStream = new DataInputStream(socket.getInputStream());
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

			System.out.println("Received data!");
		}
	}
}
