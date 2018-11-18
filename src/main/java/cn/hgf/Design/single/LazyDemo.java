package cn.hgf.Design.single;
/**
 * 	懒汉式:
 * 		|- 懒加载
 * 		|- 线程不安全，如果要安全则需要加同步锁
 * @author Administrator
 *
 */
public class LazyDemo {
	private static LazyDemo demo = null;
	
	
	private LazyDemo(){//私有化构造器，使得对象实例化不能通过new
		
		System.out.println("my is a demo");
		
	}
	
	//开放一个公有方法，判断是否已经存在实例，有返回，没有新建一个在返回  
	public static synchronized  LazyDemo  getInstance(){
		
		if (demo == null) {
			System.out.println("当前没有创建实例对象");
			return demo  = new LazyDemo();
		} else {
			System.out.println("当前实例对象已被创建");
		}
		return demo;
		
	}
}	
