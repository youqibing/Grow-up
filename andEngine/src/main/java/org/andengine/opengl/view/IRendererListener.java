package org.andengine.opengl.view;

import org.andengine.opengl.util.GLState;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
              */
public interface IRendererListener {
	// ===========================================================
	// Constants
	// ===========================================================

	public void onSurfaceCreated(final GLState pGlState);
	public void onSurfaceChanged(final GLState pGlState, final int pWidth, final int pHeight);

	// ===========================================================
	// Methods
	// ===========================================================
}