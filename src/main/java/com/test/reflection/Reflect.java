package com.test.reflection;

import java.lang.reflect.Method;

public class Reflect {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> classType = Class.forName("com.test.reflection.Student");
		Student s = (Student) classType.newInstance();
		
		Method[] methods = classType.getMethods();
	}
}

class Student{
	int id;
	String name;
	int age;
	public Student() {
	}
	public Student(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
