    代理（proxy）设计模式：
        1.静态代理
        2.jdk动态代理
        3.cglib动态代理

    为什么用？
        当我们在开发时，需要对接口实现类中实现的接口进行更多的附加操作时
        （比如说在执行注册时，提前进行新加的验证，或者增加日志输出等等），
        此时若修改接口实现类的代码就违反了开闭原则，那么我们可以再接口的执行过程中附加代理实现类，在目标实现类实现之前执行


    静态代理：》*》*》*》*》*》*》*》*》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》

        通过代理类和目标类共同实现接口（必须条件）
            （例子中loggerImpl和timerImpl是代理类。其中logger是二级代理，timer是一级代理，执行操作时先进行最外部的代理类
        调用其上级代理类，直到最后的目标被代理类(EmployeeServiceImpl)

        例子中执行顺序：
            employeeServiceTimer.register()             ====>       输出注册执行开始  2018-08-02 14:04:25
            employeeServiceLogger.register()            ====>       jack开始注册
            EmployeeServiceImpl.register()              ====>       jack
            employeeServiceLogger.register()            ====>       jack注册结束
            employeeServiceTimer.register()             ====>       注册结束时间  2018-08-02 14:04:25

        优点：严格符合开闭原则，实现可插拔模式设计
        缺点：会增加更多的类，造成内存的浪费




    动态代理》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》


