/**
 * 
 */
package com.raddle.graph.shape;

import java.awt.Color;
import java.awt.Rectangle;

import com.raddle.graph.HandlerPort;

/**
 * @author xurong
 *
 */
public class BasicHandlerPort extends RectShape implements HandlerPort {

	public BasicHandlerPort(int x, int y, int width, int height) {
		super(x, y, width, height);
		super.drowLabel = false;
		super.getBorder().setColor(Color.GREEN);
	}

	public BasicHandlerPort(Rectangle rect) {
		super(rect);
	}

}
