package cn.hgf.Design.single;
/**
 * 	饿汉式
 * 		|- 立即加载
 * 		|- 线程安全
 * @author Administrator
 *
 */
public class HungerDemo {
	
	private static HungerDemo demo = new HungerDemo();
	
	
	private HungerDemo(){//私有化构造器，使得对象实例化不能通过new
		
		System.out.println("my is demo");
		
	}
	
	public static HungerDemo getInstance() {
		
		return demo;
		
	}
}
