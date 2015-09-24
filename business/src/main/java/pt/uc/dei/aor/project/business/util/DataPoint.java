package pt.uc.dei.aor.project.business.util;

public class DataPoint<T,U> {

	private T x;
	private U y;
	
	public DataPoint(T x, U y) {
		this.x = x;
		this.y = y;
	}

	public T getX() {
		return x;
	}

	public U getY() {
		return y;
	}
	
	
}
