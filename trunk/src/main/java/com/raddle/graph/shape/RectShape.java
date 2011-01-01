/**
 * 
 */
package com.raddle.graph.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

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
	private int portWidth = 8;
	private BasicHandlerPort leftMiddlePort;
	private BasicHandlerPort leftTopPort;
	private BasicHandlerPort topMiddlePort;
	private BasicHandlerPort rightTopPort;
	private BasicHandlerPort rightMiddlePort;
	private BasicHandlerPort rightBottomPort;
	private BasicHandlerPort bottomMiddlePort;
	private BasicHandlerPort leftBottom;
	private List<BasicHandlerPort> leftPorts = new ArrayList<BasicHandlerPort>();
	private List<BasicHandlerPort> rightPorts = new ArrayList<BasicHandlerPort>();
	private List<BasicHandlerPort> topPorts = new ArrayList<BasicHandlerPort>();
	private List<BasicHandlerPort> bottomPorts = new ArrayList<BasicHandlerPort>();

	public RectShape(Rectangle rect) {
		this.rect = rect;
		this.drowBackground = true;
		this.drowBorder = true;
		this.drowLabel = true;
		this.background = new FillRectDecorator(Color.blue);
		this.border = new RectBorderDecorator(Color.black, 1);
		this.label = new LabelDecorator();
		// 增加port
		leftMiddlePort = new BasicHandlerPort(rect.x, rect.y + rect.height / 2 - portWidth / 2, portWidth, portWidth);
		leftTopPort = new BasicHandlerPort(rect.x, rect.y, portWidth, portWidth);
		topMiddlePort = new BasicHandlerPort(rect.x + rect.width / 2 - portWidth / 2, rect.y, portWidth, portWidth);
		rightTopPort = new BasicHandlerPort(rect.x + rect.width - portWidth, rect.y, portWidth, portWidth);
		rightMiddlePort = new BasicHandlerPort(rect.x + rect.width - portWidth, rect.y + rect.height / 2 - portWidth / 2, portWidth, portWidth);
		rightBottomPort = new BasicHandlerPort(rect.x + rect.width - portWidth, rect.y + rect.height - portWidth, portWidth, portWidth);
		bottomMiddlePort = new BasicHandlerPort(rect.x + rect.width / 2 - portWidth / 2, rect.y + rect.height - portWidth, portWidth, portWidth);
		leftBottom = new BasicHandlerPort(rect.x, rect.y + rect.height - portWidth, portWidth, portWidth);
		ports.add(leftMiddlePort);
		ports.add(leftTopPort);
		ports.add(topMiddlePort);
		ports.add(rightTopPort);
		ports.add(rightMiddlePort);
		ports.add(rightBottomPort);
		ports.add(bottomMiddlePort);
		ports.add(leftBottom);
		// //
		leftPorts.add(leftMiddlePort);
		leftPorts.add(leftTopPort);
		leftPorts.add(leftBottom);
		// //
		rightPorts.add(rightTopPort);
		rightPorts.add(rightMiddlePort);
		rightPorts.add(rightBottomPort);
		// //
		topPorts.add(leftTopPort);
		topPorts.add(topMiddlePort);
		topPorts.add(rightTopPort);
		// //
		bottomPorts.add(leftBottom);
		bottomPorts.add(bottomMiddlePort);
		bottomPorts.add(rightBottomPort);
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
	public int getBorderThickness() {
		return getBorder().getThickness();
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean moveTo(int x, int y) {
		// 端口
		for (HandlerPort handlerPort : ports) {
			handlerPort.move(x - rect.x, Direction.right);
			handlerPort.move(y - rect.y, Direction.down);
		}
		rect.x = x;
		rect.y = y;
		return true;
	}

	@Override
	public boolean move(int pixel, Direction direction) {
		if (pixel == 0) {
			return true;
		}
		if (direction == Direction.up) {
			rect.y -= pixel;
		} else if (direction == Direction.down) {
			rect.y += pixel;
		} else if (direction == Direction.left) {
			rect.x -= pixel;
		} else if (direction == Direction.right) {
			rect.x += pixel;
		}
		// 端口
		for (HandlerPort handlerPort : ports) {
			handlerPort.move(pixel, direction);
		}
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
	public boolean scale(int pixel, Direction from, Direction to) {
		if (pixel == 0) {
			return true;
		}
		if (to == Direction.up) {
			// 自己
			if (from == Direction.up) {
				rect.height = rect.height + pixel;
				rect.y -= pixel;
				// 端口
				for (HandlerPort handlerPort : ports) {
					if (topPorts.contains(handlerPort)) {
						handlerPort.move(pixel, Direction.up);
					}
				}
			} else {
				rect.height = rect.height - pixel;
				// 端口
				for (HandlerPort handlerPort : ports) {
					if (!topPorts.contains(handlerPort)) {
						handlerPort.move(pixel, Direction.up);
					}
				}
			}
			leftMiddlePort.moveTo(rect.x, rect.y + rect.height / 2 - portWidth / 2);
			rightMiddlePort.moveTo(rect.x + rect.width - portWidth, rect.y + rect.height / 2 - portWidth / 2);
		} else if (to == Direction.down) {
			// 自己
			if (from == Direction.down) {
				rect.height = rect.height + pixel;
				// 端口
				for (HandlerPort handlerPort : ports) {
					if (bottomPorts.contains(handlerPort)) {
						handlerPort.move(pixel, Direction.down);
					}
				}
			} else {
				rect.height = rect.height - pixel;
				rect.y += pixel;
				// 端口
				for (HandlerPort handlerPort : ports) {
					if (!bottomPorts.contains(handlerPort)) {
						handlerPort.move(pixel, Direction.down);
					}
				}
			}
			leftMiddlePort.moveTo(rect.x, rect.y + rect.height / 2 - portWidth / 2);
			rightMiddlePort.moveTo(rect.x + rect.width - portWidth, rect.y + rect.height / 2 - portWidth / 2);
		} else if (to == Direction.left) {
			// 自己
			if (from == Direction.left) {
				rect.width = rect.width + pixel;
				rect.x -= pixel;
				// 端口
				for (HandlerPort handlerPort : ports) {
					if (leftPorts.contains(handlerPort)) {
						handlerPort.move(pixel, Direction.left);
					}
				}
			} else {
				rect.width = rect.width - pixel;
				// 端口
				for (HandlerPort handlerPort : ports) {
					if (!leftPorts.contains(handlerPort)) {
						handlerPort.move(pixel, Direction.left);
					}
				}
			}
			topMiddlePort.moveTo(rect.x + rect.width / 2 - portWidth / 2, rect.y);
			bottomMiddlePort.moveTo(rect.x + rect.width / 2 - portWidth / 2, rect.y + rect.height - portWidth);
		} else if (to == Direction.right) {
			// 自己
			if (from == Direction.right) {
				rect.width = rect.width + pixel;
				for (HandlerPort handlerPort : ports) {
					if (rightPorts.contains(handlerPort)) {
						handlerPort.move(pixel, Direction.right);
					}
				}
			} else {
				rect.width = rect.width - pixel;
				rect.x += pixel;
				for (HandlerPort handlerPort : ports) {
					if (!rightPorts.contains(handlerPort)) {
						handlerPort.move(pixel, Direction.right);
					}
				}
			}
			topMiddlePort.moveTo(rect.x + rect.width / 2 - portWidth / 2, rect.y);
			bottomMiddlePort.moveTo(rect.x + rect.width / 2 - portWidth / 2, rect.y + rect.height - portWidth);
		}
		return true;
	}

	@Override
	public void portMoved(HandlerPort port, Point from, Point to) {
		if (to.x > from.x) {
			scale(to.x - from.x, getFromDirection(port, Direction.right), Direction.right);
		}
		if (to.x < from.x) {
			scale(from.x - to.x, getFromDirection(port, Direction.left), Direction.left);
		}
		if (to.y > from.y) {
			scale(to.y - from.y, getFromDirection(port, Direction.down), Direction.down);
		}
		if (to.y < from.y) {
			scale(from.y - to.y, getFromDirection(port, Direction.up), Direction.up);
		}
	}

	private Direction getFromDirection(HandlerPort port, Direction to) {
		if (to == Direction.left || to == Direction.right) {
			if (rightPorts.contains(port)) {
				return Direction.right;
			} else {
				return Direction.left;
			}
		}
		if (to == Direction.up || to == Direction.down) {
			if (topPorts.contains(port)) {
				return Direction.up;
			} else {
				return Direction.down;
			}
		}
		return null;
	}

}
