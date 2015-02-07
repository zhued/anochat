package com.anochat.transport;

import java.io.IOException;
import java.net.Socket;

import com.anochat.node.Node;

public class TCPConnection {
	private TCPSender sender;
	private TCPReceiver receiver;

	public TCPConnection(Node node, Socket socket) throws IOException {
		sender = new TCPSender(socket);
		receiver = new TCPReceiver(node, socket);
		new Thread(receiver).start();
	}

	public void send(byte[] data) throws IOException {
		sender.send(data);
	}
}
