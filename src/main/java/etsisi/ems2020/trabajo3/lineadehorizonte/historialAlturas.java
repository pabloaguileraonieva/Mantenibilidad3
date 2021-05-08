package etsisi.ems2020.trabajo3.lineadehorizonte;

public class historialAlturas {
	private int s1y;
	private int s2y;
	private int prev;
	
	public historialAlturas()
	{
		s1y=-1;
		s2y=-1;
		prev=-1;
	}

	public int getS1y() {
		return s1y;
	}

	public void setS1y(int s1y) {
		this.s1y = s1y;
	}

	public int getS2y() {
		return s2y;
	}

	public void setS2y(int s2y) {
		this.s2y = s2y;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}
	
	
}
