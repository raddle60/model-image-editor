/**
 * 
 */
package com.raddle.graph;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import com.raddle.graph.shape.RectShape;

/**
 * @author xurong
 * 
 */
public class GraphPane extends JScrollPane {
	private Point pressedPoint;
	private DraggedRect draggedRect;
	private static final long serialVersionUID = 1L;
	private List<GraphShape> selectedShapes = new ArrayList<GraphShape>();
	private GraphShape overShape;
	private List<GraphShape> topShapes = new ArrayList<GraphShape>();

	public GraphPane() {
		super();
		GraphMouseListener l = new GraphMouseListener();
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		RectShape rectShape = new RectShape(50, 10, 100, 100);
		rectShape.getBorder().setDashWidth(15);
		rectShape.getBorder().setThickness(5);
		topShapes.add(rectShape);
		rectShape = new RectShape(200, 10, 100, 100);
		rectShape.getBorder().setDashWidth(15);
		rectShape.getBorder().setThickness(5);
		topShapes.add(rectShape);
		rectShape = new RectShape(50, 150, 100, 100);
		rectShape.getBorder().setDashWidth(15);
		rectShape.getBorder().setThickness(5);
		topShapes.add(rectShape);
	}

	private List<GraphShape> getAllShapes() {
		List<GraphShape> allShapes = new LinkedList<GraphShape>();
		populateShapes(allShapes, topShapes);
		return allShapes;
	}

	private void populateShapes(List<GraphShape> allShapes, Collection<GraphShape> list) {
		for (GraphShape graphShape : list) {
			allShapes.add(graphShape);
			populateShapes(allShapes, graphShape.getChildren());
		}
	}

	private void overShape(Point p) {
		overShape = null;
		for (GraphShape graphShape : getAllShapes()) {
			if (graphShape.contains(p.x, p.y)) {
				RectShape s = new RectShape(graphShape.getBounds());
				s.setDrowBackground(false);
				s.getBorder().setColor(Color.RED);
				overShape = s;
				break;
			}
		}
	}

	/**
	 * 
	 */
	public class GraphMouseListener implements MouseListener, MouseMotionListener {

		public void mouseClicked(MouseEvent e) {
			for (GraphShape graphShape : getAllShapes()) {
				if (graphShape.contains(e.getPoint().x, e.getPoint().y)) {
					RectShape s = new RectShape(graphShape.getBounds());
					s.setDrowBackground(false);
					s.getBorder().setDashWidth(5);
					s.getBorder().setThickness(3);
					s.getBorder().setColor(Color.gray);
					selectedShapes.add(s);
					break;
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			selectedShapes.clear();
			pressedPoint = e.getPoint();
		}

		public void mouseReleased(MouseEvent e) {
			if (draggedRect != null) {
				Rectangle rectangle = draggedRect.toRectangle();
				for (GraphShape graphShape : getAllShapes()) {
					if (rectangle.contains(graphShape.getBounds())) {
						RectShape s = new RectShape(graphShape.getBounds());
						s.setDrowBackground(false);
						s.getBorder().setDashWidth(5);
						s.getBorder().setThickness(3);
						s.getBorder().setColor(Color.gray);
						selectedShapes.add(s);
					}
				}
			}
			pressedPoint = null;
			draggedRect = null;
			GraphPane.this.repaint();
		}

		public void mouseDragged(final MouseEvent e) {
			if (pressedPoint != null) {
				draggedRect = new DraggedRect(pressedPoint, e.getPoint());
				GraphPane.this.repaint();
			}
		}

		public void mouseMoved(MouseEvent e) {
			overShape(e.getPoint());
			GraphPane.this.repaint();
		}

	}

	class DraggedRect {
		int x = -1;
		int y = -1;
		int width = -1;
		int height = -1;

		public DraggedRect(Point point1, Point point2) {
			x = Math.min(point1.x, point2.x);
			y = Math.min(point1.y, point2.y);
			width = Math.abs(point2.x - point1.x);
			height = Math.abs(point2.y - point1.y);
		}

		public Rectangle toRectangle() {
			return new Rectangle(x, y, width, height);
		}

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics = (Graphics2D) g;
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, GraphPane.this.getWidth(), GraphPane.this.getHeight());
		graphics.setColor(Color.ORANGE);
		for (GraphShape graphShape : topShapes) {
			graphShape.paintShape(graphics);
		}
		if (overShape != null) {
			overShape.paintShape(graphics);
		}
		for (GraphShape graphShape : selectedShapes) {
			graphShape.paintShape(graphics);
		}
		if (draggedRect != null && pressedPoint != null) {
			DraggedRect rect = draggedRect;
			graphics.setComposite(AlphaComposite.SrcOver.derive(0.3f));
			graphics.setColor(Color.BLUE);
			graphics.fillRect(rect.x, rect.y, rect.width, rect.height);
			graphics.setComposite(AlphaComposite.Clear);
			new LineBorder(Color.BLUE, 1).paintBorder(null, graphics, rect.x, rect.y, rect.width, rect.height);
		}
	}
}