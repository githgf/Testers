package cn.hgf.Design.FactoryDesign;

public class SimpleFactory {
	
	public static void main(String[] args) {
		SimpleFactory.getInstance("PIG").syso();

		System.out.println("--------------------");
		
		Dog Dog = 
				(Dog)SimpleFactory.getReflectInstance(Dog.class);
		Dog.syso();
		
		
	}
	/**
	 * 	更具传入的类返回相应的实体类
	 * @param clazz
	 * @return
	 */
	public static Object getReflectInstance(Class<?> clazz) {
		Object object = null;
		if (clazz == null) {
			return null;
		}
		
		try {
			
			object =  Class.forName(clazz.getName()).newInstance();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
		
	}
	
	
	//利用传入的的类名创建相应的实体类
	public static InstanceFactory getInstance(String className) {
		if (className == null || ("").equals(className)) {
			System.out.println(" is null");
			return null;
		}
		if ("PIG".equals(className)) {
			System.out.println("pig is create");
			return new Pig();
		}
		if ("CAT".equals(className)) {
			return new Cat();
		}
		if ("DOG".equals(className)) {
			return new Dog();
		}

		return null;
		
	}
}
class Pig implements InstanceFactory{

	@Override
	public void syso() {
		System.out.println("pig is syso!!");
	}
	
}
class Cat implements InstanceFactory{
	
	@Override
	public void syso() {
		System.out.println("cat is syso!!");
	}
	
}
class Dog implements InstanceFactory{
	
	@Override
	public void syso() {
		System.out.println("dog is syso!!");
	}
	
}
