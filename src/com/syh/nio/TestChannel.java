package com.syh.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

/**
* @author huangsiyu
* @version 创建时间：2019年4月12日 上午8:31:49
* @ClassName 类名称
* @Description 类描述
*/


public class TestChannel {
	
	//字符集
	@Test
	public void test6() throws IOException{
		
		//1、创建一个UTF-8字符集
		Charset charset1 = Charset.forName("UTF-8");
		
		//2、创建一个编码器
		CharsetEncoder chEncoder = charset1.newEncoder();
		
		//3、创建一个解码器
		CharsetDecoder chDecoder = charset1.newDecoder();
		
		//4、创建一个缓冲区
		CharBuffer buf = CharBuffer.allocate(1024);
		buf.put("字符集测试");
		
		//5、读出缓冲区数据进行编码
		buf.flip();
		ByteBuffer buf1 = chEncoder.encode(buf);
		byte[] dst = new byte[buf1.limit()];
		buf1.get(dst);
		System.out.println("编码结果：" + dst.toString());
		
		//6、对编码的数据进行解码
		buf1.flip();
		CharBuffer buf2 = chDecoder.decode(buf1);
		System.out.println("解码结果：" + buf2.toString());
		
	}
	
	@Test
	public void test5(){
		Map<String, Charset> charsets = Charset.availableCharsets();
		for(Entry<String, Charset> charset : charsets.entrySet()){
			System.out.println(charset.getKey() + ", " + charset.getValue());
		}
	}
	
	
	//分散和聚集
	@Test
	public void test4() throws IOException{
		//1、读取文件 
		RandomAccessFile raf = new RandomAccessFile("1.txt", "rw");
		 
		//2、创建一个通道
		FileChannel channel = raf.getChannel();
		
		//3、把通道的数据读到多个缓冲区中
		ByteBuffer buf1 = ByteBuffer.allocate(100);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		ByteBuffer[] dsts = {buf1, buf2};
		channel.read(dsts);
		for(ByteBuffer dst : dsts){
			dst.flip();
			System.out.println(new String(dst.array(), 0, dst.limit()));
		}
		
		//4、把多个缓冲区的数据读到通道中
		RandomAccessFile outRaf = new RandomAccessFile("2.txt", "rw");

		FileChannel outChannel = outRaf.getChannel();
		outChannel.write(dsts);
		
		//5、关闭通道
		channel.close();
		outChannel.close();
		raf.close();
		outRaf.close();
	}
	
	//通过质检的数据传输（直接缓冲区）
	@Test
	public void test3() throws IOException{
		long start = System.currentTimeMillis();
		FileChannel inChannel = FileChannel.open(Paths.get("D:/尚硅谷/java8.zip"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("D:/java8.zip"), StandardOpenOption.WRITE,
					StandardOpenOption.CREATE, StandardOpenOption.READ);
//		inChannel.transferTo(0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, 0, inChannel.size());
		
		inChannel.close();
		outChannel.close();
		System.out.println("test2: " + (System.currentTimeMillis() - start));
	}
	
	//使用直接缓冲区完成文件的复制（内存映射文件）
	@Test
	public void test2() throws IOException{
		long start = System.currentTimeMillis();
		FileChannel inChannel = FileChannel.open(Paths.get("D:/尚硅谷/java8.zip"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("D:/java8.zip"), StandardOpenOption.WRITE,
					StandardOpenOption.CREATE, StandardOpenOption.READ);
		//内存映射文件  
		MappedByteBuffer inMapBuf = inChannel.map(MapMode.READ_ONLY,
				0, inChannel.size());
		MappedByteBuffer outMapBuf = outChannel.map(MapMode.READ_WRITE, 
				0, inChannel.size());
		
		//直接对缓冲区进行数据的读写操作
		byte[] indst = new byte[inMapBuf.limit()];
//		inMapBuf.get(indst);
		outMapBuf.put(indst);
		
		inChannel.close();
		outChannel.close();
		
		System.out.println("test2: " + (System.currentTimeMillis() - start));
	}
	
	//利用通道完成文件的复制
	@Test
	public void test1(){
		long start = System.currentTimeMillis();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		//获取通道
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream("D:/尚硅谷/java8.zip");
			fos = new FileOutputStream("D:/java8.zip");
			
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			
			//分配指定大小的缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			//将通道的数据存入缓冲区中
			int i = 0;
			while(inChannel.read(buf) != -1){
				i++;
				buf.flip();
				outChannel.write(buf);
				buf.clear();
			}
			System.out.println("循环:" + i);
			System.out.println("test1: " + (System.currentTimeMillis() - start));
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(inChannel != null){
				try {
					inChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if(outChannel != null){
				try {
					outChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}
}
