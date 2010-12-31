/**
 * 
 */
package com.raddle.graph.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.raddle.graph.EditableShape;
import com.raddle.graph.decorator.FillRectDecorator;
import com.raddle.graph.decorator.LabelDecorator;
import com.raddle.graph.decorator.RectBorderDecorator;

/**
 * @author xurong
 * 
 */
public class RectShape extends AbstractShape implements EditableShape {
	private Rectangle rect;
	private String text;

	public RectShape(Rectangle rect) {
		this.rect = rect;
		this.drowBackground = true;
		this.drowBorder = true;
		this.drowLabel = true;
		this.background = new FillRectDecorator(Color.blue);
		this.border = new RectBorderDecorator(Color.black, 1);
		this.label = new LabelDecorator();
	}

	public RectShape(int x, int y, int width, int height) {
		this(new Rectangle(x, y, width, height));
	}

	public FillRectDecorator getBackground() {
		return (FillRectDecorator) this.background;
	}

	public RectBorderDecorator getBorder() {
		return (RectBorderDecorator) this.border;
	}
	
	public LabelDecorator getLabel(){
		return (LabelDecorator) this.label;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(rect);
	}

	@Override
	public boolean contains(int x, int y) {
		return rect.contains(x, y);
	}

	@Override
	public boolean contains(int x, int y, int width, int height) {
		return rect.contains(x, y, width, height);
	}

	@Override
	public boolean contains(Rectangle rect) {
		return rect.contains(rect);
	}

	@Override
	public boolean intersects(int x, int y, int width, int height) {
		return rect.intersects(x, y, width, height);
	}

	@Override
	public boolean intersects(Rectangle rect) {
		return rect.intersects(rect);
	}

	@Override
	public void paintBody(Graphics2D graphics) {
		// 只有背景和边框
	}

	@Override
	public int getBorderThickness() {
		return getBorder().getThickness();
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean moveTo(int x, int y) {
		rect.x = x;
		rect.y = y;
		return true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		((LabelDecorator)label).setText(text);
	}

}
