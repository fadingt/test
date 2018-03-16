package resourceTest;

import java.io.UnsupportedEncodingException;

public class Poker implements Comparable<Poker> {
	private int value;
	private String name;
	private Type type;

	public static void main(String[] args) throws UnsupportedEncodingException {
		Poker a = new Poker();
		Poker b = new Poker();
		a.setType(Type.红桃);
		a.setValue(9);
		b.setType(Type.方片);
		b.setValue(1);
		System.out.println(a.toString() + b.toString());
	}

	public static enum Type {
		黑桃('\u2660', 4), 红桃('\u2665', 3), 草花('\u2663', 2), 方片('\u2666', 1);
		private int v;
		private char shape;

		private Type(char shape, int v) {
			this.shape = shape;
			this.v = v;
		}

		public int getV() {
			return v;
		}

		public char getShape() {
			return shape;
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "[" + this.getType().getShape() +this.getValue() + "]";
	}

	@Override
	public int compareTo(Poker o) {
		if (this.value == o.value) {
			return this.getType().getV() - o.getType().getV();
		}
		return this.getValue() - o.getValue();
	}
}
