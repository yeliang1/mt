package com.test.communication.socket.chap34;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

public class GetPutData {
	public static void main(String[] args) {
		// 创建缓冲区的四种方式
		IntBuffer intBuffer = IntBuffer.allocate(10);
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
		CharBuffer charBuffer = CharBuffer.wrap("abcdefg");
		DoubleBuffer doubleBuffer = DoubleBuffer.wrap(new double[] { 1.1, 2.2 });
		// 向缓冲区中写入数据
		intBuffer.put(1000);
		intBuffer.put(2000);
		System.out.println("intBuffer 的当前位置：" + intBuffer.position());
		intBuffer.position(1); // 将缓冲区的当前位置设为1
		System.out.println(intBuffer.get()); // 输出缓冲区的当前数据
		intBuffer.rewind(); // 将缓冲区的当前位置设为0
		System.out.println(intBuffer.get()); // 输出缓冲区的当前数据
		byteBuffer.put((byte) 20);
		byteBuffer.put((byte) 33);
		byteBuffer.flip(); // 将limit 设为position，在这里是2
		byteBuffer.rewind();
		// 枚举byteBuffer 中的数据
		while (byteBuffer.hasRemaining())
			System.out.print(byteBuffer.get() + " ");
		// 枚举charBuffer 中的数据
		while (charBuffer.hasRemaining())
			System.out.print(charBuffer.get() + " ");
		// 枚举doubleBuffer 中的数据
		while (doubleBuffer.position() < doubleBuffer.limit())
			System.out.print(doubleBuffer.get() + " ");
	}
}
