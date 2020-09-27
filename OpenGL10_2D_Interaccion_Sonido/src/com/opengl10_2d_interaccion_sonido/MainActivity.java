package com.opengl10_2d_interaccion_sonido;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;

/**
 * Programa que interactua con el usuario en OpenGL ES 1.x.
 * Interacción y Sonido
 * 
 * @author Jhonny Felipez
 * @version 1.0 19/09/2015
 *
 */
public class MainActivity extends Activity {
	
	GLSurfaceView superficie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* Ventana sin título */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		/* Establece las banderas de la ventana de esta Actividad */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		/* Se crea el objeto Renderiza */
		superficie = new Renderiza(this);

		/*
		 * Activity <- GLSurfaceView  : Coloca la Vista de la Superficie del
		 * OpenGL como un Contexto de ésta Actividad.
		 */
		setContentView(superficie);
		// setContentView(R.layout.activity_main);
	}
}
