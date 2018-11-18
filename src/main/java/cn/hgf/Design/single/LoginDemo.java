package cn.hgf.Design.single;

public class LoginDemo {
	//在类加载时就被创建logindemo实例,封装在静态内部类中
	private static class loginDemoHolder{
		
		private static final LoginDemo LOGIN_DEMO = new LoginDemo();
		
	}
	
	private LoginDemo() {}
	//将方法定义为常亮，保证logindemo只能被创建一次
	public static final LoginDemo getInstance() {

		return loginDemoHolder.LOGIN_DEMO;
		
	}
}
