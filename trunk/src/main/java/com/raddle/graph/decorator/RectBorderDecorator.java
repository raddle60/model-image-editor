package com.raddle.graph.decorator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.raddle.graph.GraphShape;
import com.raddle.graph.ShapeDecorator;

public class RectBorderDecorator implements ShapeDecorator {
	private Color color = Color.BLACK;
	private float opacity = 1;
	private int thickness = 1;

	public RectBorderDecorator(Color color, int thickness) {
		this.color = color;
		this.thickness = thickness;
	}

	@Override
	public void decorate(Graphics2D graphics, GraphShape shape) {
		Color oldColor = graphics.getColor();
		Composite oldComposite = graphics.getComposite();
		graphics.setColor(color);
		if (opacity > 0 && opacity < 1) {
			graphics.setComposite(AlphaComposite.SrcOver.derive(opacity));
		}
		Rectangle bounds = shape.getBounds();
		for (int i = 0; i < Math.max(thickness, 1); i++) {
			graphics.drawRect(bounds.x + i, bounds.y + i, bounds.width - i - i - 1, bounds.height - i - i - 1);
		}
		graphics.setColor(oldColor);
		graphics.setComposite(oldComposite);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
}