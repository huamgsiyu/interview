package com.syh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

/**
* @author huangsiyu
* @version 创建时间：2019年4月14日 下午3:49:07
* @ClassName 类名称
* @Description 类描述
*/
public class TestNonBlockingNIODatagram {

	
	@Test
	public void sent() throws IOException{
		
		DatagramChannel datagramChannel = DatagramChannel.open();
		
		datagramChannel.configureBlocking(false);
		
		Scanner scanner = new Scanner(System.in);
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		while(scanner.hasNext()){
			String in = scanner.next();
			byteBuffer.put((new Date().toString() + in).getBytes());
			byteBuffer.flip();
			datagramChannel.send(byteBuffer, 
					new InetSocketAddress("127.0.0.1", 9999));
			byteBuffer.clear();
		}
		
		scanner.close();
		datagramChannel.close();
	}
	
	@Test
	public void receive() throws IOException{
		DatagramChannel receiveChannel = DatagramChannel.open();
		
		receiveChannel.bind(new InetSocketAddress(9999));
		
		receiveChannel.configureBlocking(false);
		
		Selector selector = Selector.open();
		
		receiveChannel.register(selector, 
				SelectionKey.OP_READ);
		
		while(selector.select() > 0){
			Iterator<SelectionKey> sk = selector.selectedKeys().iterator();
			
			while(sk.hasNext()){
				SelectionKey key = sk.next();
				
				if(key.isReadable()){
					ByteBuffer buf = ByteBuffer.allocate(1024);
					
//					第一种
					receiveChannel.receive(buf);
					buf.flip();
					System.out.println(new String(buf.array(),
							0,
							buf.array().length));
					
					//第二种
//					DatagramChannel dc = (DatagramChannel) key.channel();
//					dc.configureBlocking(false);
//					dc.receive(buf);
//					buf.flip();
//					System.out.println(new String(buf.array(), 0, buf.array().length));
					
					buf.clear();
					
				}
				sk.remove();
			}
		}
		
	}
}
