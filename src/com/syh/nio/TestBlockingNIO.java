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
 * 一、使用NIO完成网络通信的三部分
 * 	1、通道：负责连接
 * 	
 * 	2、缓冲区：负责数据存储
 * 
 * 	3、选择器：是SelectableChannel 的多路复用器，用于监控SelectableChannel的IO状况
 * 
 *
 */

//阻塞式，无响应
public class TestBlockingNIO {

	
	
	//阻塞式
	@Test
	public void client() throws IOException{
		//1、获取通道
		SocketChannel ss = SocketChannel.open(
				new InetSocketAddress("127.0.0.1", 8888));
		
		//2、创建缓冲区，
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		//2、创建文件通道
		FileChannel fileChannel = FileChannel.open(Paths.get("1.txt"), 
				StandardOpenOption.READ, 
				StandardOpenOption.WRITE);
		
		//4、通过文件通道，把文件信息保存到缓冲区中，然后把缓冲区信息写到网络通道中
		while (fileChannel.read(byteBuffer) != -1) {
			byteBuffer.flip();
			ss.write(byteBuffer);
			byteBuffer.clear();
		}
		//5、关闭连接
		fileChannel.close();
		ss.close();
		
		
	}
	
	@Test
	public void server() throws IOException {
		//1、获取网络通道
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		
		//2、绑定连接
		ssChannel.bind(new InetSocketAddress(8888));
		
		//3、获取客户端连接的通道
		SocketChannel sChannel = ssChannel.accept();
		
		//4、把客户端通道中的数据放到缓冲区中，再把缓冲区中的数据写到文件通道，清空缓冲区
		FileChannel outChannel = FileChannel.open(Paths.get("2.txt"), 
				StandardOpenOption.WRITE,
				StandardOpenOption.CREATE);
		
		ByteBuffer dst = ByteBuffer.allocate(1024);
		
		while (sChannel.read(dst) != -1) {
			dst.flip();
			outChannel.write(dst);
			dst.clear();
		}
		
		//5、关闭连接
		sChannel.close();
		ssChannel.close();
		outChannel.close();
		
	}
}
