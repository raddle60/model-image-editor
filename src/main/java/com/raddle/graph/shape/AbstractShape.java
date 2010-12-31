/**
 * 
 */
package com.raddle.graph.shape;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.raddle.graph.GraphShape;
import com.raddle.graph.ShapeDecorator;

/**
 * @author xurong
 * 
 */
public abstract class AbstractShape implements GraphShape {
	protected GraphShape parent;
	protected List<GraphShape> children = new LinkedList<GraphShape>();
	protected ShapeDecorator background;
	protected ShapeDecorator border;
	protected boolean drowBackground = false;
	protected boolean drowBorder = false;

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
		if (drowBorder && border != null) {
			border.decorate(graphics, this);
		}
	}

	abstract protected void paintBody(Graphics2D graphics);

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
}
