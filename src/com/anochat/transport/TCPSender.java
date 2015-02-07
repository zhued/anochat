package com.anochat.transport;

import java.net.Socket;
import java.io.IOException;
import java.io.DataOutputStream;

public class TCPSender {

	private Socket socket;
	private DataOutputStream dataOutputStream;
	
	public TCPSender(Socket socket) throws IOException {
		this.socket = socket;
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
	}

	public void send(byte[] data) throws IOException {
		int dataLength = data.length;
		dataOutputStream.writeInt(dataLength);
		dataOutputStream.write(data, 0, dataLength);
		dataOutputStream.flush();
	}
}
