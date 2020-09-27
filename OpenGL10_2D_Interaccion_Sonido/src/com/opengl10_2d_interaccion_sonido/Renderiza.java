package com.opengl10_2d_interaccion_sonido;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL10Ext;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Clase Renderiza (OpenGL 1.x)
 * 
 * @author Jhonny Felipez
 * @version 1.0 19/09/2015
 *
 */
public class Renderiza extends GLSurfaceView implements Renderer {
	
	/* Tamaño de la ventana en pixeles */
	private int alto;
	private int ancho;

	/* Objeto */
	private Rectangulo rectangulo;
	private Rectangulo2 rectangulo2;
	private Circulo circulo, circulo2;
	
	/* Contexto */
	Context contexto;
	
	/* Sonidos */
	SonidoSoundPool sonido1;
	SonidoSoundPool sonido2;
	SonidoSoundPool sonido3;
	
	public Renderiza(Context contexto) {
		super(contexto);
		
		this.contexto = contexto;
		
		sonido1 = new SonidoSoundPool(contexto, "0437.ogg");
		sonido2 = new SonidoSoundPool(contexto, "0438.ogg");
		sonido3 = new SonidoSoundPool(contexto, "0564.ogg");
		
		/* Inicia el renderizado */
		this.setRenderer(this);
		
		/* La ventana solicita recibir una entrada */
		this.requestFocus();
        
		/* Establece que la ventana detectará el modo táctil */
		this.setFocusableInTouchMode(true);
		
		/* Se renderizará al inicio o cuando se llame a requestRender() */
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		
		rectangulo = new Rectangulo();
		rectangulo2 = new Rectangulo2();
		circulo = new Circulo(0.5f, 360, true);
		circulo2 = new Circulo(0.5f, 360, true);
		/* Color de fondo */
		gl.glClearColor(0, 0, 0, 0);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		
		/* Inicializa el buffer de color */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		/* Inicializa la Matriz del Modelo-Vista */
		gl.glLoadIdentity();
		
		rectangulo.dibuja(gl);
		
		rectangulo2.dibuja(gl);
		/* Traslada */
		gl.glTranslatef(0, -3, 0);
		
		circulo.dibuja(gl, 1, 1, 0);
		//Trasladar
		gl.glTranslatef(2, 0, 0);
		circulo2.dibuja(gl, 1, 0, 1);//zzs
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		
		/* Obtiene el ancho y el alto de la pantalla */
		ancho = w;
		alto = h;
	 
		/* Ventana de despliegue */
		gl.glViewport(0, 0, ancho, alto);
	 
		/* Matriz de Proyección */
		gl.glMatrixMode(GL10.GL_PROJECTION);
	 
		/* Inicializa la Matriz de Proyección */
		gl.glLoadIdentity();
	 
		/* Proyección paralela */
		GLU.gluOrtho2D(gl, -4, 4, -6, 6);
	 
		/* Matriz del Modelo-Vista */
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	 
		/* Inicializa la Matriz del Modelo-Vista */
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

		/* Se considera cuando se levanta el dedo de la pantalla. */ 
		if (e.getAction() == MotionEvent.ACTION_UP) {
			
			/* En coordenadas del OpenGL */
			posx = ((posx / (float) ancho) * 8) - 4;
			posy = ((1 - posy / (float) alto) * 12) - 6;

			/* Verifica área elegida */
			if (puntoEstaDentroDelRectangulo(posx, posy, -3, 0, 3, 3)) {

				sonido1.play();

				Toast.makeText(contexto.getApplicationContext(), "Rojo",
						Toast.LENGTH_SHORT).show();

			} else if (puntoEstaDentroDelRectangulo(posx, posy, 0, 0, 3, 3)) {

				sonido2.play();

				Toast.makeText(contexto.getApplicationContext(), "Azul",
						Toast.LENGTH_SHORT).show();
			} else if (puntoEstaDentroDelCirculo(posx, posy, 0, -3, 0.5f)) {

				sonido3.play();

				Toast.makeText(contexto.getApplicationContext(), "Amarillo",
						Toast.LENGTH_SHORT).show();
			//ADICION	
			} else if(puntoEstaDentroDelCirculo(posx, posy, 2, -3, 0.5f)){
				
				Toast.makeText(contexto.getApplicationContext(), "Escarlata",
						Toast.LENGTH_SHORT).show();
			} else if (puntoEstaDentroDelRectangulo(posx, posy, -2, 3, 3, 3)){
				
				Toast.makeText(contexto.getApplicationContext(), "Verde",
						Toast.LENGTH_SHORT).show();
				
			} else if (puntoEstaDentroDelRectangulo(posx, posy, -1, 3, 3, 3)){
			
				Toast.makeText(contexto.getApplicationContext(), "Naranja",
						Toast.LENGTH_SHORT).show();
				
			} 
			
			//FIN ADICION
			// requestRender(); // Llama por defecto
		}

		return true;
	}
	
	private boolean puntoEstaDentroDelRectangulo(float posx, float posy, int x,
			int y, int ancho, int alto) {
		return (x < posx && posx < x + ancho && y < posy && posy < y + alto);
	}

	private boolean puntoEstaDentroDelCirculo(float posx, float posy, float x,
			float y, float radio) {
		return (distancia2(posx, posy, x, y) < radio * radio);
	}

	public float distancia2(float x1, float y1, float x2, float y2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}
}
