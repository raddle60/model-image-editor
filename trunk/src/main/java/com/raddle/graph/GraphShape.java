package com.raddle.graph;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Collection;

public interface GraphShape {

	/**
	 * 获得包含这个形状的边界
	 * 
	 * @return
	 */
	public Rectangle getBounds();

	/**
	 * 是否包含点
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(int x, int y);

	/**
	 * 是否包含区域
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean contains(int x, int y, int width, int height);

	/**
	 * 是否包含区域
	 * 
	 * @param rect
	 * @return
	 */
	public boolean contains(Rectangle rect);

	/**
	 * 是否和区域有交叉
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean intersects(int x, int y, int width, int height);

	/**
	 * 是否和区域有交叉
	 * 
	 * @param rect
	 * @return
	 */
	public boolean intersects(Rectangle rect);

	/**
	 * 画出图形
	 * 
	 * @param graphics
	 */
	public void paintShape(Graphics2D graphics);

	/**
	 * 获得父形状
	 * 
	 * @return
	 */
	public GraphShape getParent();

	/**
	 * 获得子形状
	 * 
	 * @return
	 */
	public Collection<GraphShape> getChildren();

	/**
	 * 相对父形状左上角位置
	 * 
	 * @return
	 */
	public int getOffsetX();

	/**
	 * 相对父形状右上角位置
	 * 
	 * @return
	 */
	public int getOffsetY();
	
	/**
	 * 获得边框厚度
	 * @return
	 */
	public int getBorderThickness();
}
