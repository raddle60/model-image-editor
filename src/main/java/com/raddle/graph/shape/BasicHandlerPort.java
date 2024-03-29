/**
 * 
 */
package com.raddle.graph.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.raddle.graph.HandlerPort;
import com.raddle.graph.RenderRuntime;
import com.raddle.graph.ShapeDecorator;
import com.raddle.graph.constant.Direction;
import com.raddle.graph.constant.ShapeState;
import com.raddle.graph.decorator.FillRectDecorator;
import com.raddle.graph.decorator.RectBorderDecorator;

/**
 * @author xurong
 * 
 */
public class BasicHandlerPort extends AbstractShape implements HandlerPort {
	private Rectangle rect;
	private Set<Direction> acceptDirections = new HashSet<Direction>();
	private ShapeDecorator mouseoverDecorator = new RectBorderDecorator(Color.red, 2);

	public BasicHandlerPort(Rectangle rect) {
		this.rect = rect;
		this.drowBackground = true;
		this.drowBorder = true;
		this.drowLabel = true;
		this.background = new FillRectDecorator(Color.green);
		this.border = new RectBorderDecorator(Color.black, 1);
	}

	public void addDirection(Direction... directions) {
		acceptDirections.addAll(Arrays.asList(directions));
	}

	public void removeDirection(Direction direction) {
		acceptDirections.remove(direction);
	}

	public BasicHandlerPort(int x, int y, int width, int height) {
		this(new Rectangle(x, y, width, height));
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
		return this.rect.contains(rect);
	}

	@Override
	public boolean intersects(int x, int y, int width, int height) {
		return rect.intersects(x, y, width, height);
	}

	@Override
	public boolean intersects(Rectangle rect) {
		return this.rect.intersects(rect);
	}

	@Override
	public void paintBody(Graphics2D graphics) {
		// 只有背景和边框
	}
	
	@Override
	protected void afterPaint(Graphics2D graphics) {
		if(!RenderRuntime.getRuntime().isPurePainting()){
			if(containState(ShapeState.mouseover) && mouseoverDecorator != null){
				mouseoverDecorator.decorate(graphics, this);
				this.showHandlerPorts(graphics);
			}
		}
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

	@Override
	public boolean move(int pixel, Direction direction) {
		if (direction == Direction.up) {
			rect.y -= pixel;
		} else if (direction == Direction.down) {
			rect.y += pixel;
		} else if (direction == Direction.left) {
			rect.x -= pixel;
		} else if (direction == Direction.right) {
			rect.x += pixel;
		}
		return true;
	}

	@Override
	public Shape getShape() {
		return rect;
	}

	public RectBorderDecorator getBorder() {
		return (RectBorderDecorator) this.border;
	}

	@Override
	public boolean isAcceptDirection(Direction direction) {
		return acceptDirections.contains(direction);
	}

}
