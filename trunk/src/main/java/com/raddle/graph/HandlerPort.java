/**
 * 
 */
package com.raddle.graph;

import com.raddle.graph.constant.Direction;

/**
 * @author xurong
 * 
 */
public interface HandlerPort extends EditableShape {
	public boolean isAcceptDirection(Direction direction);
}
