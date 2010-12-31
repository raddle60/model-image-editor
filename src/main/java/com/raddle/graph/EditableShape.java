/**
 * 
 */
package com.raddle.graph;

import com.raddle.graph.constant.Direction;

/**
 * @author xurong
 * 
 */
public interface EditableShape extends GraphShape {
	public boolean isMovable();

	public boolean isScalable();

	public boolean isRotatable();

	public boolean moveTo(int x, int y);

	public boolean moveToOffset(int x, int y);

	public boolean scale(int pixel, Direction direction);

	public boolean rotate(double angle);
}
