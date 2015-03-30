package com.anochat.transport;

import java.io.IOException;
import java.net.Socket;

import com.anochat.node.Node;

public class TCPConnection {
	
	private TCPSender sender;
	private TCPReceiver receiver;
	private Thread receiverThread;
	
	public final String id;
	
	public TCPConnection(Node node, Socket socket) throws IOException {
		sender = new TCPSender(socket);
		receiver = new TCPReceiver(node, socket);
		id = socket.getInetAddress() + ":" + socket.getPort();
		receiverThread = new Thread(receiver);
		receiverThread.setName("Receiver");
		receiverThread.start();
	}
	
	public void send(byte[] data) throws IOException {
		sender.send(data);
	}
}
