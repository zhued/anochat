package com.anochat.node;

import java.net.Socket;
import java.io.IOException;

public interface Node {
	public void addConnection(Socket socket) throws IOException;
	public void onEvent(byte[] data) throws IOException;
}
