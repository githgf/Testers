package cn.hgf.Design.single;

import org.junit.Test;

public class TsetSingle {

	@Test
	public void testLazy() {
		
		System.out.println("mdemo创建-----");
		LazyDemo lazyDemo1 = LazyDemo.getInstance();
		System.out.println("demo创建------");
		LazyDemo lazyDemo2 = LazyDemo.getInstance();
		
		System.out.println("判断两个demo是否相同-----");
		System.out.println(lazyDemo1 == lazyDemo2);
		
		
	}
	@Test
	public void testHunger() {
		
//		Demo demo = new Demo();
		
		System.out.println("mdemo创建-----");
		HungerDemo mDemo = HungerDemo.getInstance();
		System.out.println("demo创建------");
		HungerDemo demo = HungerDemo.getInstance();
		System.out.println("判断两个demo是否相同-----");
		System.out.println(mDemo == demo);
		//因为demo被创建只能通过自建的方法，无论创建多少个都只有一个
		
	}
	public static void main(String[] args) {
		System.out.println("mdemo创建-----");
		LazyDemo lazyDemo1 = LazyDemo.getInstance();
		System.out.println("demo创建------");
		LazyDemo lazyDemo2 = LazyDemo.getInstance();
		System.out.println("判断两个demo是否相同-----");
		System.out.println(lazyDemo1 == lazyDemo2);
		

	}
}
