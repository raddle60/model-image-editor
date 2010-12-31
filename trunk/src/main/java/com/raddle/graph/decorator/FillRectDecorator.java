/**
 * 
 */
package com.raddle.graph.decorator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

import com.raddle.graph.GraphShape;
import com.raddle.graph.ShapeDecorator;

/**
 * @author xurong
 * 
 */
public class FillRectDecorator implements ShapeDecorator {
	private Color color = Color.BLACK;
	private float opacity = 1;

	public FillRectDecorator(Color color, float opacity) {
		this.color = color;
		this.opacity = opacity;
	}

	public FillRectDecorator(Color color) {
		this.color = color;
	}
	
	public FillRectDecorator() {
	}

	@Override
	public void decorate(Graphics2D graphics, GraphShape shape) {
		Color oldColor = graphics.getColor();
		Composite oldComposite = graphics.getComposite();
		graphics.setColor(color);
		if (opacity > 0 && opacity < 1) {
			graphics.setComposite(AlphaComposite.SrcOver.derive(opacity));
		}
		graphics.fill(shape.getBounds());
		//
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

}
