/**
 * 
 */
package com.raddle.graph.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import com.raddle.graph.EditableShape;
import com.raddle.graph.HandlerPort;
import com.raddle.graph.constant.Direction;
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
		// 增加port
		int portWidth = 8;
		ports.add(new BasicHandlerPort(rect.x, rect.y, portWidth, portWidth));
		ports.add(new BasicHandlerPort(rect.x + rect.width / 2, rect.y, portWidth, portWidth));
		ports.add(new BasicHandlerPort(rect.x + rect.width - portWidth, rect.y, portWidth, portWidth));
		ports.add(new BasicHandlerPort(rect.x, rect.y + rect.height / 2, portWidth, portWidth));
		ports.add(new BasicHandlerPort(rect.x + rect.width - portWidth, rect.y + rect.height / 2, portWidth, portWidth));
		ports.add(new BasicHandlerPort(rect.x, rect.y + rect.height - portWidth, portWidth, portWidth));
		ports.add(new BasicHandlerPort(rect.x + rect.width / 2, rect.y + rect.height - portWidth, portWidth, portWidth));
		ports.add(new BasicHandlerPort(rect.x + rect.width - portWidth, rect.y + rect.height - portWidth, portWidth, portWidth));
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

	public LabelDecorator getLabel() {
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
		((LabelDecorator) label).setText(text);
	}

	@Override
	public Shape getShape() {
		return rect;
	}

	@Override
	public boolean isScalable() {
		return true;
	}

	@Override
	public boolean scale(int pixel, Direction direction) {
		if (pixel == 0) {
			return true;
		}
		if (direction == Direction.vertical) {
			// 端口
			for (HandlerPort handlerPort : ports) {
				if (handlerPort.getBounds().y == rect.y) {
					handlerPort.moveTo(handlerPort.getBounds().x, handlerPort.getBounds().y + pixel);
				}
			}
			// 自己
			rect.height = rect.height + pixel;
		} else if (direction == Direction.horizontal) {
			// 端口
			for (HandlerPort handlerPort : ports) {
				if (handlerPort.getBounds().x == rect.x) {
					handlerPort.moveTo(handlerPort.getBounds().x + pixel, handlerPort.getBounds().y);
				}
			}
			// 自己
			rect.width = rect.width + pixel;
		}
		return true;
	}

	@Override
	public void portMoved(HandlerPort port, Point from, Point to) {
		if (from.x != to.x) {
			scale(to.x - from.x, Direction.horizontal);
		}
		if (from.y != to.y) {
			scale(to.y - from.y, Direction.vertical);
		}
	}

}
