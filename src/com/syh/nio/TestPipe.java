package com.syh.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SourceChannel;

import org.junit.Test;

/**
*	管道
*/
public class TestPipe {
	
	@Test
	public void test() throws IOException{
		//1、获取管道
		Pipe pipe = Pipe.open();
		
		//2、将缓冲区的数据放入管道
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put("2222222222222222".getBytes());
		Pipe.SinkChannel sink = pipe.sink();
		sink.write(buf);
		
		//3、读取缓冲区的数据
		SourceChannel source = pipe.source();
		source.read(buf);
		buf.flip();
		System.out.println(new String(buf.array(),
				0, 
				buf.array().length));
		
	}
	
}
