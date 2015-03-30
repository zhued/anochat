package com.anochat.node;

import java.io.IOException;
import java.net.Socket;

import com.anochat.wireformats.Event;

public interface Node {
	public void onEvent(Event event) throws IOException;
	public void onConnect(Socket socket) throws IOException;
}
