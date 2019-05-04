package com.syh.nio;
/**
* @author huangsiyu
* @version 创建时间：2019年4月11日 下午9:11:10
* @ClassName 类名称
* @Description 类描述
*/

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * 
 * 一、Buffer：在Java NIO中负责数据存取，缓冲区是数组、用于不同数据类型的数据
 * 
 * 根据数据类型不同，提供了对应类型的缓冲区（boolean除外）
 * ByteBuffer（最常用）
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 
 * 上溯缓冲区的管理方式几乎一致，都是通过allocate()获取缓冲区
 *
 *
 *二、缓冲区存取数据的两个核心方法
 *	1、put()	：存入数据到缓冲区中
 *	2、get()：获取缓冲区的数据
 *
 *四、缓冲区中的四个核心属性
 *	1、capacity：容量，表示缓冲区中最大数据存储的容量，一旦生命不能改变
 *	2、limit：界限，表示缓冲区中可以操作数据的大小，（limit后面的数据不能够进行读写）
 *	3、position：位置，表示缓冲区中正在操作数据的位置
 *	4、mark：标记，表示记录当前position的位置，可以通过reset()恢复到mark的位置
 *	0 <= mark <= position <= limit <= capacity
 *
 *五、直接缓冲区与非直接缓冲区
 *	1、非直接缓冲区：通过allocate()方法分配缓冲区，将缓冲区建立在JVM的内存中
 *	2、直接缓冲区：通过allocateDirect()方法分配直接缓冲区，将缓冲区建立在操作系统的物理内存中，这种方式可以提高效率
 */
public class TestBuffer {


	public void Test3(){
		//1、分配直接缓冲区
		ByteBuffer buf = ByteBuffer.allocateDirect(1024);
		
		//2、判断缓冲区是直接缓冲区还是非直接缓冲区
		buf.isDirect();
	}
	
	
	@Test
	public void test2(){
		String str = "fda11aa1";
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put(str.getBytes());
		buf.flip();
		byte[] dst = new byte[buf.limit()];
		buf.get(dst, 0, 3);

		System.out.println("position1:");
		System.out.println(new String(dst));
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		System.out.println();
		buf.mark();
		buf.get(dst, 0, 4);
		System.out.println("position2:");
		System.out.println(new String(dst));
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		System.out.println();
		buf.reset();
		System.out.println("position3:");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
	}
	
	@Test
	public void test1(){
		
		//1、分配一个指定大小的缓冲区,allocate()
		ByteBuffer buffer = ByteBuffer.allocate(11024);
		System.out.println("position1:");
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		System.out.println("");
		
		//2、利用put()存入数据到缓冲区中
		String first = "abc";
		buffer.put(first.getBytes());
		System.out.println("position2:");
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		System.out.println("");
		
		//3、利用flip()进入读数据模式
		buffer.flip();
		System.out.println("position3:");
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		
		
		//4、利用get()方法读取缓冲区数据
		byte[] dst = new byte[buffer.limit()];
		buffer.get(dst);
		System.out.println("获取的数据：" + new String(dst, 0, dst.length));
		System.out.println("");
		
		System.out.println("position4:");
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		
		//5、rewind()，重新回到读模式，重新开始读数据，可以用来重复读数据
		buffer.rewind();
		System.out.println("position5:");
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		
		//6、clear()，清空缓冲区，但是缓冲区的数据依然存在，但是数据处于被遗忘状态（指针位置回到最初状态，capacity/limit/position都变了最初状态）
		buffer.clear();
		System.out.println("position5:");
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
	}
}
