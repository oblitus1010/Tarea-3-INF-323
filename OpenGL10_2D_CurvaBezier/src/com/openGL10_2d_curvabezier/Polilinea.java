package com.openGL10_2d_curvabezier;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

/**
 * Clase Polilinea (OpenGL 1.x)
 * 
 * @author Jhonny Felipez
 * @version 1.0 21/02/2016
 * 
 */
public class Polilinea {
	
	FloatBuffer bufVerticesControl;
	FloatBuffer bufVerticesPuntos;
	
	public Polilinea(int MAX_NUM_PUNTOS_CONTROL, int numPuntos){
		
		/* Reserva espacio para los vértices de los puntos de control */
		
		ByteBuffer bufByte = ByteBuffer.allocateDirect(MAX_NUM_PUNTOS_CONTROL * 2 * 4);
		bufByte.order(ByteOrder.nativeOrder());
		bufVerticesControl = bufByte.asFloatBuffer();
		
		/* Reserva espacio para los vértices de la curva */
		
		bufByte = ByteBuffer.allocateDirect(numPuntos * 2 * 4);
		bufByte.order(ByteOrder.nativeOrder());
		bufVerticesPuntos = bufByte.asFloatBuffer();
	}
	
	public void dibuja(GL10 gl, int numPuntosControl, float control[][], int numPuntos, float puntos[][]) {
		
		/* Lee los vértices de los puntos de control */
		
		bufVerticesControl.clear();
		for (int i = 0; i < numPuntosControl; i++) {
			bufVerticesControl.put(control[i][0]); // x
			bufVerticesControl.put(control[i][1]); // y
		}
		bufVerticesControl.rewind();
		
		/* Lee los vértices de la curva */
		
		bufVerticesPuntos.clear();
		for (int i = 0; i < numPuntos; i++) {
			bufVerticesPuntos.put(puntos[i][0]); // x
			bufVerticesPuntos.put(puntos[i][1]); // y
		}
		bufVerticesPuntos.rewind();
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		/* Dibuja las líneas que conectan los puntos de control (color amarillo) */
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVerticesControl);
		gl.glLineWidth(1);
		gl.glColor4f(1, 1, 0, 0);
		if (numPuntosControl > 0)
			gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, numPuntosControl);
		
		/* Dibuja los puntos de control (color rojo) */
		
		gl.glPointSize(10);
		gl.glColor4f(1, 0, 0, 0);
		if (numPuntosControl > 0)
			gl.glDrawArrays(GL10.GL_POINTS, 0, numPuntosControl);

		/* Dibuja la curva (color verde) */
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVerticesPuntos);
		gl.glLineWidth(2);
		gl.glColor4f(0, 1, 0, 0);
		if (numPuntos > 0)
			gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, numPuntos);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
