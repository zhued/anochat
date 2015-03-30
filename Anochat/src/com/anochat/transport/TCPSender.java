package com.anochat.transport;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPSender {

	private final Socket socket;
	private final DataOutputStream dataOutputStream;
	
	public TCPSender(Socket socket) throws IOException {
		this.socket = socket;
		this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	public void send(byte[] data) throws IOException {
		System.out.println("Sending data");
		int dataLength = data.length;
		dataOutputStream.writeInt(dataLength);
		dataOutputStream.write(data, 0, dataLength);

		dataOutputStream.flush();
	}
}
