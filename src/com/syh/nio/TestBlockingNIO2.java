package com.syh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 阻塞式，有响应
*/
public class TestBlockingNIO2 {

	@Test
	public void client() throws IOException{
		
		SocketChannel clientChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8889));
		
		FileChannel fileChannel = FileChannel.open(Paths.get("2.txt"),
				StandardOpenOption.READ);
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		while(fileChannel.read(buf) != -1){
			buf.flip();
			clientChannel.write(buf);
			buf.clear();
		}
		
		clientChannel.shutdownOutput();
		
		int len = 0;
		while ((len = clientChannel.read(buf)) != -1) {
			buf.flip();
			System.out.println("收到服务端响应：" + new String(buf.array(), 0, len));
		}
		
		fileChannel.close();
		clientChannel.close();
	}
	
	@Test
	public void server() throws IOException{
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.bind(new InetSocketAddress(8889));
		SocketChannel sChannel = serverChannel.accept();
		FileChannel outChannel = FileChannel.open(Paths.get("3.txt"), 
				StandardOpenOption.WRITE,
				StandardOpenOption.CREATE);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while(sChannel.read(buffer) != -1){
			buffer.flip();
			outChannel.write(buffer);
			buffer.clear();
		}
		buffer.put("服务端接收数据成功".getBytes());
		buffer.flip();
		sChannel.write(buffer);
		
		outChannel.close();
		sChannel.close();
		serverChannel.close();
		
	}
	
}
