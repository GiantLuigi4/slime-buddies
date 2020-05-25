package com.ult1team.slimebuddies.SlimeBuddies.Entities;
public class Color{
	int value;
	public Color(int r, int g, int b) { this(r, g, b, 255); }
	public Color(int r, int g, int b, int a) {
		value = ((a & 0xFF) << 24) |
				((r & 0xFF) << 16) |
				((g & 0xFF) << 8)  |
				((b & 0xFF));
	}
	public Color darker() {
		double FACTOR=0.7;
		return new Color(Math.max((int)(getRed()*FACTOR), 0),
				Math.max((int)(getGreen()*FACTOR), 0),
				Math.max((int)(getBlue() *FACTOR), 0),
				getAlpha());
	}
	public Color darker(double FACTOR) {
		return new Color(Math.max((int)(getRed()  *FACTOR), 0),
				Math.max((int)(getGreen()*FACTOR), 0),
				Math.max((int)(getBlue() *FACTOR), 0),
				getAlpha());
	}
	public Color(int rgb) { value = 0xff000000 | rgb; }
	public int getRGB() { return value; }
	public int getRed() { return (getRGB() >> 16) & 0xFF; }
	public int getGreen() { return (getRGB() >> 8) & 0xFF; }
	public int getBlue() { return (getRGB() >> 0) & 0xFF; }
	public int getAlpha() { return (getRGB() >> 24) & 0xff; }
	public boolean equals(Object obj) { return obj instanceof Color && ((Color)obj).getRGB() == this.getRGB(); }
	public String toString() { return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]"; }
	public Color average(Color col) {
		return new Color(
				(this.getRed()+col.getRed())/2,
				(this.getGreen()+col.getGreen())/2,
				(this.getBlue()+col.getBlue())/2
		);
	}
}