package proxy.t03;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/**
 * 问题3: 怎么让TankTimeProxy和TankLogProxy代理非Movable的对象?
 * -- 使用Object类型代理Movable?
 * -- 这样会不知道具体代理哪些方法(不代理哪些方法)

 * -- 使用动态代理
 * -- 不手写TankTimeProxy和TankLogProxy这样的代理类
 * -- 怎么做呢? JDK动态代理 Proxy类和InvocationHandler接口
 */
public class Tank implements Movable {

    /**
     * 模拟坦克移动了一段儿时间
     */
    @Override
    public void move() {
        System.out.println("Tank moving claclacla...");
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 保持代理.class文件
        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles","true");
        Tank tank = new Tank();

        //reflection 通过二进制字节码分析类的属性和方法

        Movable m = (Movable) Proxy.newProxyInstance(
                Tank.class.getClassLoader(), // 类加载器 使用和被代理对象相同的classLoader
                new Class[]{Movable.class},  // 被代理对象的接口
                // new LogHandler(tank)      // 被代理对象的被代理方法被调用时改怎么做的实现类
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("method " + method.getName() + " start..");
                        Object o = method.invoke(tank, args);
                        System.out.println("method " + method.getName() + " end!");
                        return o;
                    }
                }
        );

        m.move();
    }
}

class LogHandler implements InvocationHandler {

    Tank tank;

    public LogHandler(Tank tank) {
        this.tank = tank;
    }
    // getClass.getMethods[]
    // Proxy类
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method " + method.getName() + " start..");
        Object o = method.invoke(tank, args);
        System.out.println("method " + method.getName() + " end!");
        return o;
    }
}

interface Movable {
    void move();
}

