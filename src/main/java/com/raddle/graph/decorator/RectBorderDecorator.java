package com.raddle.graph.decorator;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import com.raddle.graph.GraphShape;
import com.raddle.graph.ShapeDecorator;

public class RectBorderDecorator implements ShapeDecorator {
	private Color color = Color.BLACK;
	private float opacity = 1;
	private int thickness = 1;
	private int dashWidth = -1;

	public RectBorderDecorator(Color color, int thickness) {
		this.color = color;
		this.thickness = thickness;
	}

	@Override
	public void decorate(Graphics2D graphics, GraphShape shape) {
		Color oldColor = graphics.getColor();
		Composite oldComposite = graphics.getComposite();
		Stroke oldStroke = graphics.getStroke();
		graphics.setColor(color);
		if (opacity > 0 && opacity < 1) {
			graphics.setComposite(AlphaComposite.SrcOver.derive(opacity));
		}
		Rectangle bounds = shape.getBounds();
		if (dashWidth > 0) {
			graphics.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,
					new float[] { dashWidth }, 0.0f));
			graphics.draw(new Rectangle(bounds.x + thickness / 2, bounds.y + thickness / 2, bounds.width - thickness,
					bounds.height - thickness));
		} else {
			for (int i = 0; i < Math.max(thickness, 1); i++) {
				graphics.drawRect(bounds.x + i, bounds.y + i, bounds.width - i - i - 1, bounds.height - i - i - 1);
			}
		}
		graphics.setColor(oldColor);
		graphics.setComposite(oldComposite);
		graphics.setStroke(oldStroke);
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

	public int getDashWidth() {
		return dashWidth;
	}

	public void setDashWidth(int dashWidth) {
		this.dashWidth = dashWidth;
	}
}