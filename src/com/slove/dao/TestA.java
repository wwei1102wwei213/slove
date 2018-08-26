package com.slove.dao;

public class TestA {
	
	public TestA() {
		System.out.println("TestA");
		System.out.println(this.getClass().getName());
		test();
	}
	
	private void test(){
		System.out.println("a");
	}
}
