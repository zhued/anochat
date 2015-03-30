package com.anochat.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NodeSendsMessage implements Event {
	
	/* Wireformat
	 * byte: type
	 * int: length of message
	 * byte[^^]: message utf8 string
	 */
	
	public final String message;
	
	public NodeSendsMessage(byte[] data) throws IOException {
		ByteArrayInputStream baInputStream = 
				new ByteArrayInputStream(data);
		DataInputStream din = 
				new DataInputStream(new BufferedInputStream(baInputStream));
		
		byte type = din.readByte();
		int messageLength = din.readInt();
		byte[] messageBytes = new byte[messageLength];
		din.readFully(messageBytes, 0, messageLength);

		message = new String(messageBytes);
		
		baInputStream.close();
		din.close();
	}
	
	public NodeSendsMessage(String message) {
		this.message = message;
	}
	
	@Override
	public byte getType() {
		return Protocol.NODE_SENDS_MESSAGE;
	}
	
	@Override
	public byte[] getBytes() throws IOException {
		byte[] marshalledBytes = null;
		
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = 
				new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		byte[] messageBytes = message.getBytes();
		int messageLength = messageBytes.length;
		
		dout.writeByte(Protocol.NODE_SENDS_MESSAGE);
		dout.writeInt(messageLength);
		dout.write(messageBytes, 0, messageLength);
		dout.flush();
		
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();
		return marshalledBytes;
	}

}
