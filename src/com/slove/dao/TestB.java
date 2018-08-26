package com.slove.dao;

public class TestB extends TestA{
	
	
	public TestB() {
		System.out.println("TestB");
		test();
	}
	
	public void test(){
		System.out.println("b");
	}
	
	public static void A(String a){
		a = new String("2");
		System.out.println(a);
	}
	public static void B(Integer b){
		b = new Integer(2);
		System.out.println(b);
	}
	public static void C(int c){
		c = 2;
		System.out.println(c);
	}
	
	public static void main(String[] args) {
		new TestB();
		/*String a = "1";
		Integer b = new Integer(1);
		int c = 1;
		A(a);
		B(b);
		C(c);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);*/
	}

}
