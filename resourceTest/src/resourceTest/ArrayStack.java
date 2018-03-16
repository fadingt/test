package resourceTest;

import java.util.ArrayList;
import java.util.List;

public class ArrayStack<T> implements Stack<T> {
	private List<T> list;
	private int top;

	public ArrayStack() {
		list = new ArrayList<T>();
		top = list.size();
	}

	@Override
	public T pop() {
		if (!list.isEmpty()) {
			top--;
			return list.remove(top);
		}
		return null;
	}

	@Override
	public void push(T element) {
		list.add(element);
		top++;

	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public T peek() {
		if (this.isEmpty()) {
			System.out.println("EmptyStack");
//			throw new RuntimeException("EmptyStack");
		}
		return list.get(top);
	}

	public static void main(String[] args) {
		ArrayStack<String> stack = new ArrayStack<String>();
		stack.push("liuxingyu1");
		stack.push("liuxingyu2");
		stack.push("liuxingyu3");
		stack.push("liuxingyu4");

		while (!stack.isEmpty()) {
			System.out.println(stack.pop());
//			System.out.println(Thread.currentThread().getName());
		}
//		System.out.flush();
//		stack.peek();
//		System.err.print("I'm red");
	}

	public int getTop() {
		return top;
	}

}
