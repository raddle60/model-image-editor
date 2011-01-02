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

import com.raddle.graph.constant.ShapeState;
import com.raddle.graph.shape.AbstractShape;
import com.raddle.graph.shape.RectShape;

/**
 * @author xurong
 * 
 */
public class GraphPane extends JScrollPane {
	private Point pressedPoint;
	private Point draggedPoint;
	private HandlerPort draggedHandlerPort;
	private HandlerPort overHandlerPort;
	private DraggedRect draggedRect;
	private static final long serialVersionUID = 1L;
	private List<AbstractShape> selectedShapes = new ArrayList<AbstractShape>();
	private AbstractShape overShape;
	private AbstractShape pressedShape;
	private Point pressedShapePoint;
	private List<AbstractShape> topShapes = new ArrayList<AbstractShape>();

	public GraphPane() {
		super();
		GraphMouseListener l = new GraphMouseListener();
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		RectShape rectShape = new RectShape(50, 10, 100, 100);
		rectShape.getBorder().setDashWidth(15);
		rectShape.getBorder().setThickness(5);
		rectShape.setText("rect_" + (topShapes.size() + 1));
		topShapes.add(rectShape);
		rectShape = new RectShape(200, 10, 100, 100);
		rectShape.getBorder().setDashWidth(15);
		rectShape.getBorder().setThickness(5);
		rectShape.setText("rect_" + (topShapes.size() + 1));
		topShapes.add(rectShape);
		rectShape = new RectShape(50, 150, 100, 100);
		rectShape.getBorder().setDashWidth(15);
		rectShape.getBorder().setThickness(5);
		rectShape.setText("rect_" + (topShapes.size() + 1));
		topShapes.add(rectShape);
	}

	private List<AbstractShape> getAllShapes() {
		LinkedList<AbstractShape> allShapes = new LinkedList<AbstractShape>();
		populateShapes(allShapes, topShapes);
		return allShapes;
	}

	private void populateShapes(LinkedList<AbstractShape> allShapes, Collection<AbstractShape> list) {
		for (AbstractShape graphShape : list) {
			allShapes.addFirst(graphShape);
			LinkedList<AbstractShape> children = new LinkedList<AbstractShape>();
			for (GraphShape child : graphShape.getChildren()) {
				children.add((AbstractShape)child);
			}
			populateShapes(allShapes, children);
		}
	}

	private void overShape(Point p) {
		if (overHandlerPort != null) {
			overHandlerPort.removeState(ShapeState.mouseover);
		}
		if (overShape != null) {
			overShape.removeState(ShapeState.mouseover);
		}
		overShape = null;
		for (AbstractShape graphShape : getAllShapes()) {
			if (graphShape.contains(p.x, p.y)) {
				overShape = graphShape;
				break;
			}
		}
		overHandlerPort = null;
		if(overShape != null){
			for (HandlerPort port : overShape.getHandlerPorts()) {
				if(port.contains(p.x, p.y)){
					overHandlerPort = port;
					break;
				}
			}
		}
		if (overShape != null) {
			overShape.addState(ShapeState.mouseover);
		}
		if (overHandlerPort != null) {
			overHandlerPort.addState(ShapeState.mouseover);
		}
	}

	/**
	 * 
	 */
	public class GraphMouseListener implements MouseListener, MouseMotionListener {

		public void mouseClicked(MouseEvent e) {
			for (AbstractShape graphShape : selectedShapes) {
				graphShape.removeState(ShapeState.selected);
			}
			selectedShapes.clear();
			for (GraphShape graphShape : getAllShapes()) {
				if (graphShape.contains(e.getPoint().x, e.getPoint().y)) {
					selectedShapes.add((AbstractShape) graphShape);
					break;
				}
			}
			for (AbstractShape graphShape : selectedShapes) {
				graphShape.addState(ShapeState.selected);
			}
			GraphPane.this.repaint();
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			pressedPoint = e.getPoint();
			draggedPoint = e.getPoint();
			pressedShape = null;
			pressedShapePoint = null;
			for (GraphShape graphShape : getAllShapes()) {
				if (graphShape.contains(e.getPoint().x, e.getPoint().y)) {
					pressedShape = (AbstractShape) graphShape;
					pressedShapePoint = new Point(graphShape.getBounds().x, graphShape.getBounds().y);
					break;
				}
			}
			if(pressedShape != null){
				pressedShape.addState(ShapeState.pressed);
			}
			GraphPane.this.repaint();
		}

		public void mouseReleased(MouseEvent e) {
			if (draggedRect != null) {
				for (AbstractShape graphShape : selectedShapes) {
					graphShape.removeState(ShapeState.selected);
				}
				selectedShapes.clear();
				Rectangle rectangle = draggedRect.toRectangle();
				for (GraphShape graphShape : getAllShapes()) {
					if (rectangle.contains(graphShape.getBounds())) {
						selectedShapes.add((AbstractShape) graphShape);
					}
				}
				for (AbstractShape graphShape : selectedShapes) {
					graphShape.addState(ShapeState.selected);
				}
				overShape(e.getPoint());
			}
			///////
			if(pressedShape != null){
				pressedShape.removeState(ShapeState.pressed);
			}
			////
			pressedPoint = null;
			draggedPoint = null;
			draggedRect = null;
			pressedShape = null;
			pressedShapePoint = null;
			draggedHandlerPort = null;
			GraphPane.this.repaint();
		}

		public void mouseDragged(final MouseEvent e) {
			if (pressedPoint != null) {
				if (pressedShape != null && pressedShapePoint != null) {
					overShape = null;
					EditableShape editableShape = (EditableShape) pressedShape;
					if(draggedHandlerPort == null){
						draggedHandlerPort = editableShape.getHandlerPortAt(e.getPoint());
					}
					if (draggedHandlerPort != null) {
						Point oldPortPoint = new Point(draggedHandlerPort.getBounds().x, draggedHandlerPort.getBounds().y);
						editableShape.portMoved(draggedHandlerPort, oldPortPoint, new Point(oldPortPoint.x + (e.getPoint().x - draggedPoint.x),
								oldPortPoint.y + (e.getPoint().y - draggedPoint.y)));
					} else {
						((EditableShape) pressedShape).moveTo(pressedShapePoint.x + (e.getPoint().x - pressedPoint.x),
								pressedShapePoint.y + (e.getPoint().y - pressedPoint.y));
					}
				} else {
					draggedRect = new DraggedRect(pressedPoint, e.getPoint());
				}
				GraphPane.this.repaint();
			}
			draggedPoint = e.getPoint();
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
//		if (overShape != null) {
//			overShape.paintShape(graphics);
//			overShape.showHandlerPorts(graphics);
//		} else if(pressedShape != null){
//			pressedShape.showHandlerPorts(graphics);
//		}
//		if(overMaskShape != null){
//			overMaskShape.paintShape(graphics);
//		}
//		for (GraphShape graphShape : selectedShapes) {
//			graphShape.paintShape(graphics);
//		}
		if (pressedShape == null && draggedRect != null && pressedPoint != null) {
			DraggedRect rect = draggedRect;
			graphics.setComposite(AlphaComposite.SrcOver.derive(0.3f));
			graphics.setColor(Color.BLUE);
			graphics.fillRect(rect.x, rect.y, rect.width, rect.height);
			graphics.setComposite(AlphaComposite.Clear);
			new LineBorder(Color.BLUE, 1).paintBorder(null, graphics, rect.x, rect.y, rect.width, rect.height);
		}
	}
}
