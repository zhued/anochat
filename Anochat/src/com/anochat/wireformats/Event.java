package com.anochat.wireformats;

import java.io.IOException;

public interface Event {
	
	public byte getType();
	public byte[] getBytes() throws IOException;
}
