package resourceTest;

import java.util.Date;

import org.junit.Test;

public class ThreadDemo {
	public static void main(String[] args) throws InterruptedException {
		// new ThreadDemo().text1();
//		System.out.println(Integer.MAX_VALUE);
		// ThreadGroup g = Thread.currentThread().getThreadGroup();
		// ThreadGroup topg = g;
		// while (g != null) {
		// topg = g;
		// System.out.println(g.getName());
		// g = g.getParent();
		// }
		// System.out.println(topg.activeCount());
		// System.out.println(topg.activeGroupCount());

		ThreadGroup tg = new ThreadGroup("mygroup");
		MyThread t0 = new MyThread(tg, "thread#0") {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "running");
				super.run();
			}

		};
		MyThread t1 = new MyThread(tg, "thread#1");
		MyThread t2 = new MyThread(tg, "thread#2");

		t0.start();
		t1.start();
		t2.start();

		Thread.sleep(1000);

		System.out.println(tg.activeCount() + " threads in thread group.");

		Thread thrds[] = new Thread[tg.activeCount()];
		tg.enumerate(thrds);
		for (Thread t : thrds)
			System.out.println(t.getName());

		t0.myStop();

		Thread.sleep(1000);

		System.out.println(tg.activeCount() + " threads in tg.");
		tg.interrupt();
	}

	@Test
	public void text1() {
		new Thread() {
			@Override
			public void run() {
				int count = 0;
				while (count < 100) {
					System.out.println(this.getName() + "run..." + count);
					System.out.println(new Date().getTime());
					System.out.println(this.getThreadGroup().getName());
					try {
						Thread.sleep(1000, 0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					count++;
					super.run();
				}
			}
		}.start();
	}
}

class MyThread extends Thread {
	boolean stopped;

	MyThread(ThreadGroup g, String name) {
		super(g, name);
		stopped = false;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " starting.");
		try {
			for (int i = 1; i < 1000; i++) {
				System.out.print(".");
				Thread.sleep(250);
				synchronized (this) {
					if (stopped)
						break;
				}
			}
		} catch (Exception exc) {
			System.out.println(Thread.currentThread().getName() + " interrupted.");
		}
		System.out.println(Thread.currentThread().getName() + " exiting.");
	}

	synchronized void myStop() {
		stopped = true;
	}
}