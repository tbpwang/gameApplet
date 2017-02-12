package edu.cumtb;

import java.applet.Applet;
import java.awt.*;
import java.util.Random;

/**
 * Created by Wang on 2017/2/12.
 */
public class gameApplet extends Applet implements Runnable {
    private int x, y;
    private int diameter;
    private int width, height;
    private Thread gameThread;
    private Image image;
    private Graphics dbg;
    private static final int FPS = 70;

    @Override
    public void init() {
        x = 50;
        y = 50;
        diameter = 100;
        width = 500;
        height = 500;
        setBackground(Color.MAGENTA);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void stop() {
        gameThread = null;
    }

    public void run() {
        long t1, t2, dt, sleepTime;
        long period = 1000 / FPS;  //计算每一次循环需要的执行时间，单位毫秒
        t1 = System.nanoTime();  //保存游戏循环执行前的系统时间，单位纳秒

        while (true) {
            gameUpdate();
            gameRender();
            gamePaint();
            t2 = System.nanoTime(); //游戏循环执行后的系统时间，单位纳秒
            dt = (t2 - t1) / 1000000L;  //本次循环实际花费的时间，并转换为毫秒
            sleepTime = period - dt;//计算本次循环剩余的时间，单位毫秒
            if (sleepTime <= 0)        //防止sleepTime值为负数
                sleepTime = 2;
            try {
                Thread.sleep(sleepTime); //让线程休眠，由sleepTime值决定
            } catch (InterruptedException ex) {
            }
            t1 = System.nanoTime();  //重新获取当前系统时间
        }
    }

    private void gamePaint() {
        Graphics g = this.getGraphics();
        if (g != null && image != null) {
            g.drawImage(image, 0, 0, null);
        }

    }

    private void gameRender() {
        if (image == null) {
            image = createImage(getWidth(), getHeight());
        }
        dbg = image.getGraphics();

        dbg.setColor(Color.pink);
        dbg.fillRect(0, 0, width, height);
        dbg.setColor(Color.blue);
        dbg.fillOval(x, y, diameter, diameter);



    }

    private void gameUpdate() {
        Random rand = new Random();
        switch (rand.nextInt(4)) {
            case 0:
                x += 10;
                break;
            case 1:
                x -= 10;
                break;
            case 2:
                y += 10;
                break;
            case 3:
                y -= 10;
                break;
        }
        if (x > getWidth()) {
            x = -diameter;
        }
        if (y > getHeight()) {
            y = -diameter;
        }
        if (x < -diameter) {
            x = getWidth();
        }
        if (y < -diameter) {
            y = getHeight();
        }
    }
}
