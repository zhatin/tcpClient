package com.bdreport.test.client;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TestClientHandler extends ChannelInboundHandlerAdapter {

	private ByteBuf firstMessage;
	private ByteBuf secondMessage;
	private byte[] testMsg = { (byte) 0xEE, (byte) 0xCA, (byte) 0x0C, (byte) 0xA1, (byte) 0x00, (byte) 0x5A,
			(byte) 0xA2, (byte) 0x00, (byte) 0x5A, (byte) 0xA3, (byte) 0x00, (byte) 0x5A, (byte) 0xA4, (byte) 0x00,
			(byte) 0x5A, (byte) 0xF2, (byte) 0xFF, (byte) 0xFC, (byte) 0xFF, (byte) 0xFF, (byte) 0xCA, (byte) 0x0C,
			(byte) 0xA1, (byte) 0x00, (byte) 0x5A, (byte) 0xA2, (byte) 0x00, (byte) 0x5A, (byte) 0xA3, (byte) 0xEE,
			(byte) 0xCA, (byte) 0x0C, (byte) 0xA1, (byte) 0x00, (byte) 0x5A, (byte) 0xA2, (byte) 0x00, (byte) 0x5A,
			(byte) 0xA3, (byte) 0x00, (byte) 0x5A, (byte) 0xA4, (byte) 0x00, (byte) 0x5A, (byte) 0xF2, (byte) 0xFF,
			(byte) 0xFC, (byte) 0xFF, (byte) 0xFF, (byte) 0xEE, (byte) 0xCA, (byte) 0x0C, (byte) 0xA1, (byte) 0x00,
			(byte) 0x5A, (byte) 0xA2, (byte) 0x00, (byte) 0x5A, (byte) 0xA3 };
	private byte[] testPackageB1half = { (byte) 0xEE, (byte) 0xB1, (byte) 0x00, (byte) 0x01, (byte) 0x07, (byte) 0xE1,
			(byte) 0x06, (byte) 0x0D, (byte) 0x09, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x00,
			(byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x4E, (byte) 0x6C, (byte) 0x4E, (byte) 0x73, (byte) 0x00,
			(byte) 0x02, (byte) 0x00, (byte) 0x02, (byte) 0x4E, (byte) 0x6C, (byte) 0x4E, (byte) 0x73, (byte) 0xFD,
			(byte) 0xFF, (byte) 0xFC, (byte) 0xFF, (byte) 0xFF };
	private byte[] testPackageB1custom0625 = { (byte) 0xEE, (byte) 0xB1, (byte) 0x00, (byte) 0x01, (byte) 0x07,
			(byte) 0xE1, (byte) 0x06, (byte) 0x0D, (byte) 0x09, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10,
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x04, (byte) 0x07, (byte) 0xD0, (byte) 0x01, (byte) 0x91,
			(byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x04, (byte) 0xFF, (byte) 0x6F, (byte) 0xFC, (byte) 0x90,
			(byte) 0x6E, (byte) 0xFF, (byte) 0xFC, (byte) 0xFF, (byte) 0xFF };
	private byte[] testPackageBacustom0625 = { (byte) 0xEE, (byte) 0xA1, (byte) 0x00, (byte) 0x01, (byte) 0x07,
			(byte) 0xE1, (byte) 0x06, (byte) 0x0D, (byte) 0x09, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x17,
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x0E, (byte) 0x00, (byte) 0x01, (byte) 0xBB, (byte) 0x8F,
			(byte) 0xFF, (byte) 0x00, (byte) 0x06, (byte) 0xBB, (byte) 0x8F, (byte) 0xFF, (byte) 0x00, (byte) 0x02,
			(byte) 0x00, (byte) 0x09, (byte) 0x00, (byte) 0x10, (byte) 0xBB, (byte) 0x8F, (byte) 0xFF, (byte) 0x0C,
			(byte) 0xFF, (byte) 0xFC, (byte) 0xFF, (byte) 0xFF };
	private String testStr = "test message.\n";

	public TestClientHandler() {
		firstMessage = Unpooled.wrappedBuffer(testPackageB1custom0625);
		secondMessage = Unpooled.wrappedBuffer(testPackageBacustom0625);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		//ctx.writeAndFlush(firstMessage);
		//String hexStr = Hex.encodeHexString(testPackageB1custom0625).toUpperCase();
		//System.out.println("Sent Message: " + hexStr);
		ctx.writeAndFlush(secondMessage);
		String hexStr = Hex.encodeHexString(testPackageBacustom0625).toUpperCase();
		System.out.println("Sent Message: " + hexStr);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf byteBuf = (ByteBuf) msg;
		byte[] hexByte = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(hexByte);
		String hexStr = Hex.encodeHexString(hexByte).toUpperCase();
		System.out.println("Received Response: " + hexStr);
		byteBuf.release();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
