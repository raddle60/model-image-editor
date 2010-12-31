/**
 * 
 */
package com.raddle.graph.decorator;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.SwingUtilities;

import com.raddle.graph.GraphShape;
import com.raddle.graph.ShapeDecorator;

/**
 * @author xurong
 * 
 */
public class LabelDecorator implements ShapeDecorator {
	private String text;
	private Font font;

	public LabelDecorator() {
	}

	public LabelDecorator(String text) {
		this.text = text;
	}

	@Override
	public void decorate(Graphics2D graphics, GraphShape shape) {
		if (text != null && text.trim().length() > 0) {
			Font old = graphics.getFont();
			if (font != null) {
				graphics.setFont(font);
			}
			String realText = text.trim();
			int width = SwingUtilities.computeStringWidth(graphics.getFontMetrics(), text);
			Rectangle bounds = shape.getBounds();
			int x = (int) (bounds.getCenterX() - width / 2);
			graphics.drawString(realText, x, (int) bounds.getCenterY());
			graphics.setFont(old);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

}
