package com.anochat.transport;

import java.io.IOException;
import java.net.Socket;

import com.anochat.node.Node;

public class TCPConnection {
	
	private final Node node;
	private final TCPSender sender;
	private final TCPReceiver receiver;
	
	public final String id;
	
	public TCPConnection(Node node, Socket socket) throws IOException {
		id = socket.getInetAddress() + ":" + socket.getPort();
		this.node = node;
		sender = new TCPSender(socket);
		receiver = new TCPReceiver(node, socket, id);
		new Thread(receiver).start();
	}
	
	public void send(byte[] data) throws IOException {
		sender.send(data);
	}
}
