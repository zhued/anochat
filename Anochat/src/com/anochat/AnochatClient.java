package com.anochat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.anochat.node.Node;
import com.anochat.transport.TCPConnection;
import com.anochat.wireformats.Event;
import com.anochat.wireformats.NodeSendsMessage;
import com.anochat.wireformats.Protocol;

public class AnochatClient implements Node {
	
	private TCPConnection server;
	
	public void connectToServer(String serverUrl, int port) throws IOException {
		server = new TCPConnection(this, new Socket(serverUrl, port));
	}
	
	@Override
	public void onConnect(Socket socket) throws IOException {
		// unused
	}
	
	@Override
	public void onEvent(Event event) {
		switch(event.getType()) {
		case Protocol.NODE_SENDS_MESSAGE: onMessageReceipt((NodeSendsMessage)event); break;
		}
	}
	
	public void onMessageReceipt(NodeSendsMessage event) {
		System.out.println(event.message);
	}
	
	public void sendMessage(String message) throws IOException {
		server.send(new NodeSendsMessage(message).getBytes());
	}
	
	
	public static void main(String[] args) {
		try {
			AnochatClient client = new AnochatClient();
			client.connectToServer("localhost", 10865);
			Scanner keyboard = new Scanner(System.in);
			String message = keyboard.nextLine();
			while(message != null && !message.equalsIgnoreCase("quit")) {
				client.sendMessage(message);
				message = keyboard.nextLine();
			}
		} catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
}
