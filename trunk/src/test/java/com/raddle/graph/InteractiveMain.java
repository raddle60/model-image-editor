/**
 * 
 */
package com.raddle.graph;

import javax.swing.JFrame;

/**
 * @author xurong
 * 
 */
public class InteractiveMain extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		InteractiveMain frame = new InteractiveMain();
		frame.getContentPane().add(new GraphPane());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
