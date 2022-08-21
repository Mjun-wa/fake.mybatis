package proxy.t02;

import java.util.Random;

/**
 * 问题2: 如果无法改变方法源码呢?
 * 使用静态代理
 * 问题3: 怎么让TankTimeProxy和TankLogProxy代理非Movable的对象?
 */
public class Tank implements Movable {

    /**
     * 模拟坦克移动了一段儿时间
     */
    public void move() {
        System.out.println("Tank moving claclacla...");
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Tank tank = new Tank();
        tank.move();
        System.out.println("---- ----");
        TankTimeProxy timeProxy = new TankTimeProxy(tank);
        timeProxy.move();
        System.out.println("---- ----");
        TankLogProxy logProxy = new TankLogProxy(timeProxy);
        logProxy.move();
    }
}

class TankTimeProxy implements Movable {

    Movable tank;

    public TankTimeProxy(Movable tank) {
        this.tank = tank;
    }

    public void move() {
        long start = System.currentTimeMillis();
        tank.move();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

class TankLogProxy implements Movable {
    Movable tank;

    public TankLogProxy(Movable tank) {
        this.tank = tank;
    }

    public void move() {
        System.out.println("start moving...");
        tank.move();
        System.out.println("stopped!");
    }
}

interface Movable {
    void move();
}
