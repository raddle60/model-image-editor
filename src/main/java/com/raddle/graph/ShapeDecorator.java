/**
 * 
 */
package com.raddle.graph;

import java.awt.Graphics2D;

/**
 * @author xurong
 * 
 */
public interface ShapeDecorator {
	/**
	 * 画出图形
	 * 
	 * @param graphics
	 */
	public void decorate(Graphics2D graphics, GraphShape shape);
}
