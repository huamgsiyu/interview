package com.syh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;


/**
 * 
*/
public class TestNonBlockingNIO {
	
	@Test
	public void client() throws IOException{
		
		//1、获取通道
		SocketChannel clientChannel = SocketChannel.open(
				new InetSocketAddress("127.0.0.1", 8888));
		
		//2、设置非阻塞模式
		clientChannel.configureBlocking(false);
		
		//3、搞个缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		//4、把信息放进缓冲区
		buf.put("服务器你好".getBytes());
		
//		//5、缓冲区信息读取到通道中
		buf.flip();
		clientChannel.write(buf);
		buf.clear();
		
		//6、关闭通道
		clientChannel.close();
		
		
	}
	
	@Test
	public void clientCanner() throws IOException{
		
		//1、获取通道
		SocketChannel clientChannel = SocketChannel.open(
				new InetSocketAddress("127.0.0.1", 8888));
		
		//2、设置非阻塞模式
		clientChannel.configureBlocking(false);
		
		//3、搞个缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		//4、将输入的信息放进缓冲区，然后读取缓冲区信息放到网络通道中
		
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			buf.put(("服务端接收数据成功" + scanner.next()).getBytes());
			buf.flip();
			clientChannel.write(buf);
			buf.clear();
			
		}
		
		//5、关闭通道
		scanner.close();
		clientChannel.close();
		
		
	}
	
	@Test
	public void server() throws IOException{
		
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		
		serverChannel.configureBlocking(false);
		
		serverChannel.bind(new InetSocketAddress(8888));
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		Selector selector = Selector.open();

		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(selector.select() > 0){
			Iterator<SelectionKey> sk = selector.selectedKeys().iterator();
			while (sk.hasNext()) {
				SelectionKey key = sk.next();
				if(key.isAcceptable()){
					SocketChannel sChannel = serverChannel.accept();
					
					sChannel.configureBlocking(false);
					
					sChannel.register(selector, SelectionKey.OP_READ);
					
				}else if(key.isReadable()){
					SocketChannel readerChannel = (SocketChannel) key.channel();
					
					while(readerChannel.read(buf) > 0){
						buf.flip();
						System.out.println(new String(buf.array(),
								0,
								buf.array().length));
						buf.clear();
					}
				}
				sk.remove();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
