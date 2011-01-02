/**
 * 
 */
package com.raddle.graph;

/**
 * @author xurong
 * 
 */
public class RenderRuntime {
	private static final ThreadLocal<RenderRuntime> runtime = new ThreadLocal<RenderRuntime>();
	private boolean purePainting = false;
	private RenderRuntime() {

	}

	public static RenderRuntime getRuntime() {
		if (runtime.get() == null) {
			runtime.set(new RenderRuntime());
		}
		return runtime.get();
	}

	public static void setRuntime(RenderRuntime runtime) {
		RenderRuntime.runtime.set(runtime);
	}

	public static void clearRuntime() {
		runtime.set(null);
	}

	public boolean isPurePainting() {
		return purePainting;
	}

	public void setPurePainting(boolean purePainting) {
		this.purePainting = purePainting;
	}
	
}
