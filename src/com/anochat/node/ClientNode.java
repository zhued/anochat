package com.anochat.node;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.anochat.transport.TCPConnection;

public class ClientNode implements Node {

	private TCPConnection serverConnection;

	public ClientNode(String server, int port) throws IOException {
		addConnection(new Socket(server, port));
	}

	/* Node interface */
	@Override
	public void addConnection(Socket socket) throws IOException {
		serverConnection = new TCPConnection(this, socket);
		System.out.println("Connected to server: " + socket.getInetAddress()
			+ ":" + socket.getPort());
	}

	@Override
	public void onEvent(byte[] data) throws IOException {
		System.out.println(new String(data));
	}
	/* End Node interface */

	public void send(String message) throws IOException {
		serverConnection.send(message.getBytes());
	}

	public static void main(String[] args) {
		try {
			String server = args[0];
			int port = Integer.parseInt(args[1]);

			ClientNode client = new ClientNode(server, port);

			Scanner keyboard = new Scanner(System.in);
			String message = keyboard.nextLine();
			while(message != null && !message.equalsIgnoreCase("quit")) {
				client.send(message);
				message = keyboard.nextLine();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
