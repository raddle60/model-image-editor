/**
 * 
 */
package com.raddle.graph.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.raddle.graph.EditableShape;
import com.raddle.graph.GraphShape;
import com.raddle.graph.HandlerPort;
import com.raddle.graph.ShapeDecorator;
import com.raddle.graph.constant.Direction;
import com.raddle.graph.constant.ShapeState;

/**
 * @author xurong
 * 
 */
public abstract class AbstractShape implements EditableShape {
	protected GraphShape parent;
	protected List<GraphShape> children = new LinkedList<GraphShape>();
	protected List<HandlerPort> ports = new ArrayList<HandlerPort>();
	protected ShapeDecorator background;
	protected ShapeDecorator border;
	protected ShapeDecorator label;
	protected boolean drowBackground = false;
	protected boolean drowBorder = false;
	protected boolean drowLabel = false;
	protected Set<ShapeState> shapeStates = new HashSet<ShapeState>();

	@Override
	public GraphShape getParent() {
		return parent;
	}

	@Override
	public Collection<GraphShape> getChildren() {
		return children;
	}

	public void addChildren(GraphShape child) {
		children.add(child);
	}

	public void setParent(GraphShape parent) {
		this.parent = parent;
	}

	@Override
	public void paintShape(Graphics2D graphics) {
		if (drowBackground && background != null) {
			background.decorate(graphics, this);
		}
		paintBody(graphics);
		if (drowLabel && label != null) {
			label.decorate(graphics, this);
		}
		if (drowBorder && border != null) {
			border.decorate(graphics, this);
		}
	}

	abstract protected void paintBody(Graphics2D graphics);

	@Override
	public boolean isMovable() {
		return false;
	}

	@Override
	public boolean isScalable() {
		return false;
	}

	@Override
	public boolean isRotatable() {
		return false;
	}

	@Override
	public boolean moveTo(int x, int y) {
		return false;
	}
	
	@Override
	public boolean move(int pixel, Direction direction) {
		return false;
	}
	
	@Override
	public boolean moveToOffset(int x, int y) {
		return false;
	}

	@Override
	public boolean scale(int pixel, Direction from, Direction to) {
		return false;
	}

	@Override
	public boolean rotate(Point center, double angle) {
		return false;
	}

	@Override
	public int getOffsetX() {
		return 0;
	}

	@Override
	public int getOffsetY() {
		return 0;
	}

	public boolean isDrowBackground() {
		return drowBackground;
	}

	public void setDrowBackground(boolean drowBackground) {
		this.drowBackground = drowBackground;
	}

	public boolean isDrowBorder() {
		return drowBorder;
	}

	public void setDrowBorder(boolean drowBorder) {
		this.drowBorder = drowBorder;
	}

	public ShapeDecorator getLabel() {
		return label;
	}

	public boolean isDrowLabel() {
		return drowLabel;
	}

	public void setDrowLabel(boolean drowLabel) {
		this.drowLabel = drowLabel;
	}

	@Override
	public List<HandlerPort> getHandlerPorts() {
		return ports;
	}

	@Override
	public void portMoved(HandlerPort port, Point from, Point to) {

	}

	@Override
	public void showHandlerPorts(Graphics2D graphics) {
		for (HandlerPort handlerPort : ports) {
			handlerPort.paintShape(graphics);
		}
	}

	@Override
	public HandlerPort getHandlerPortAt(Point p) {
		for (HandlerPort handlerPort : ports) {
			if (handlerPort.contains(p.x, p.y)) {
				return handlerPort;
			}
		}
		return null;
	}

	@Override
	public void addState(ShapeState state) {
		shapeStates.add(state);
	}

	@Override
	public void removeState(ShapeState state) {
		shapeStates.remove(state);
	}

	@Override
	public void clearState(ShapeState state) {
		shapeStates.clear();
	}

	@Override
	public boolean containState(ShapeState state) {
		return shapeStates.contains(state);
	}

}
