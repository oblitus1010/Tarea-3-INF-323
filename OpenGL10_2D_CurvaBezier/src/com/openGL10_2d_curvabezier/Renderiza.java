package com.openGL10_2d_curvabezier;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

/**
 * Clase Renderiza (OpenGL 1.x)
 * 
 * @author Jhonny Felipez
 * @version 1.0 21/02/2016
 *
 */
public class Renderiza extends GLSurfaceView implements Renderer {
	
	/* Objetos */
	private Polilinea polilinea;
	
	/* Tamaño de la ventana en pixeles */
	private int alto;
	private int ancho;
	
	/* Contexto */
	Context contexto;
	
	/* Para almacenar los puntos */
	private final int MAX_NUM_PUNTOS_CONTROL = 20;
	private float control[][] = new float[MAX_NUM_PUNTOS_CONTROL][2];
	private int numPuntosControl = -1;
	
	/* m + 1 cantidad de puntos de la curva */
	int numPuntos = 100;
	
	/* Arreglo de coeficientes */
	private float [] coeficiente = new float[50];
	
	/* Puntos de la curva */
	private float puntos[][] = new float[150][2];
	
	public Renderiza(Context contexto) {
		super(contexto);
		this.contexto = contexto;
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		
		polilinea = new Polilinea(MAX_NUM_PUNTOS_CONTROL, numPuntos);
		
		/* Color de fondo */
		gl.glClearColor(0, 0, 0, 0);
		
		gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);
		gl.glEnable(GL10.GL_POINT_SMOOTH);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_LINE_SMOOTH);
		gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_DONT_CARE);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		/* Calcula los coeficientes binomiales. */
		calculaCoeficientes();
		
		/* Obtiene la curva de Bezier en base a los puntos de control. */
		bezier();

		/* Renderiza la Curva de Bezier */
		if (numPuntosControl > 0 && numPuntos > 0) {
			polilinea.dibuja(gl, numPuntosControl+1, control, numPuntos, puntos);
		}

	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		
		ancho = w;
		alto = h;
		gl.glViewport(0, 0, ancho, alto);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, -4, 4, -6, 6);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
	}
	
	/**
	 * Maneja los eventos de la pantalla táctil. 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		
		/* Obtiene la coordenada de la pantalla */
		float posx = e.getX();
		float posy = e.getY();

		/* Se considera cuando el dedo toca la pantalla. */
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			
			/* En coordenadas del OpenGL */
			posx = ((posx / (float) ancho) * 8) - 4;
			posy = ((1 - posy / (float) alto) * 12) - 6;
			
			/* Adiciona un nuevo punto */
			adicionaUnNuevoPunto(posx, posy);

			requestRender(); // Llama por defecto
		}

		return true;
		
	}
	
	/* Elimina el primer punto */
	public void eliminaElPrimerPunto() {
		if (numPuntosControl > 0) {
			for (int i = 0; i < numPuntosControl; i++) {
				control[i][0] = control[i + 1][0];
				control[i][1] = control[i + 1][1];
			}
			numPuntosControl--;
		}
	}

	/* Adiciona un nuevo punto */
	public void adicionaUnNuevoPunto(float x, float y) {
		if (numPuntosControl >= MAX_NUM_PUNTOS_CONTROL-1) {
			eliminaElPrimerPunto();
		}
		numPuntosControl++;
		control[numPuntosControl][0] = x;
		control[numPuntosControl][1] = y;
	}
	
	/* Calcula los coeficientes binomiales. */
	public void  calculaCoeficientes () {
		/* n + 1 puntos de control */
		for (int k = 0; k <= numPuntosControl; k++) {
			
			/* Calcula n!/(k!(n-k)!) */
			coeficiente[k] = 1;
			for (int i = numPuntosControl; i >= k+1; i--)
				coeficiente[k] = coeficiente[k] * i;
			for (int i = numPuntosControl-k; i>=2; i--)
				coeficiente[k] = coeficiente[k] / i;
		}
	}

	/* Obtiene la curva de Bezier en base a los puntos de control. */
	public void bezier() {
		float u, x, y;
		
		/* m + 1 puntos de la curva */
		for (int i = 0; i <= numPuntos; i++) { 
			u = (float)i / numPuntos;
			x = 0; y = 0;
			
			/* n + 1 puntos de control */
			for (int k = 0; k <= numPuntosControl; k++) { 
				x = x + control[k][0] * coeficiente[k] * (float)Math.pow(u,k) * (float)Math.pow(1-u, numPuntosControl-k);
				y = y + control[k][1] * coeficiente[k] * (float)Math.pow(u,k) * (float)Math.pow(1-u, numPuntosControl-k);
			}
			puntos[i][0] = x;
			puntos[i][1] = y;
		}
	}
}
