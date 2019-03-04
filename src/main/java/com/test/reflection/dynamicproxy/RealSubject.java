package com.test.reflection.dynamicproxy;

//具体角色
public class RealSubject implements Subject {
	public RealSubject() {
	}
	public void request() {
		System.out.println("From real subject.");
	}
}
