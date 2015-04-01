package com.anochat.wireformats;

import java.io.IOException;

public class EventFactory {

	private static final EventFactory instance = new EventFactory();
	
	private EventFactory() { }
	
	public static EventFactory getInstance() {
		return instance;
	}
	
	public Event createEvent(byte[] data) throws IOException {
		if(data == null || data.length < 1) return null;
		
		switch(data[0]) {
		case Protocol.NODE_SENDS_MESSAGE: return new NodeSendsMessage(data);

		default: return null;
		}
	}
}
