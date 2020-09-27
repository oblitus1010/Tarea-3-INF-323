package com.opengl10_2d_interaccion_sonido;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;
/**
 * Clase Rectangulo (OpenGL 1.x)
 *  
 * @author Jhonny Felipez
 * @version 1.0 21/08/2014
 *
 */
public class Rectangulo {
	
	/**
	 *    3 ---------- 2
	 *     |         /| 
	 *     |      /   | 
	 *     |   /      |
	 *     |/         |
	 *    0 ---------- 1  
	 */	
	private float vertices[] = new float[] {
		// Rectángulo 1
		-3, 0, // 0
		 0, 0, // 1
		 0, 3, // 2
		-3, 3, // 3
		// Rectángulo 2
		 0, 0, // 4
		 3, 0, // 5
		 3, 3, // 6
		 0, 3,  // 7
		 //Rectangulo verde
		-2, 3, //8
		 1, 3, //9
		 1, 6, //10
		-2, 6  //11
		 /*/Rectangulo naranja
		-1, 3, //12
		 2, 3, //13
		 2, 6, //14
		-1, 6  //15*/
	};

	private byte colores[] = new byte[] {
		// Rectángulo 1
		(byte)255, 0, 0, 0, // 0
		(byte)255, 0, 0, 0, // 1
		(byte)255, 0, 0, 0, // 2
		(byte)255, 0, 0, 0, // 3
		// Rectángulo 2
		0, 0, (byte)255, 0, // 4
		0, 0, (byte)255, 0, // 5
		0, 0, (byte)255, 0, // 6
		0, 0, (byte)255, 0,  // 7
		//Rectangulo verde
		0, (byte)255, 0, 0, //8
		0, (byte)255, 0, 0, //9
		0, (byte)255, 0, 0, //10
		0, (byte)255, 0, 0, //11
		//Rectangulo naranja
		/*(byte)255, (byte)128, 0, 0, //12
		(byte)255, (byte)128, 0, 0, //13
		(byte)255, (byte)128, 0, 0, //14
		(byte)255, (byte)128, 0, 0, //15*/
	};
	
	private short indices[] = new short [] { 
		 0, 1, 2, 0, 2, 3, // Rectángulo 1
		 4, 5, 6, 4, 6, 7,  // Rectángulo 2
		 8, 9, 10, 8, 10, 11 //Rectangulo verde
		 /*12, 13, 14, 12, 14, 15 //Rectangulo naranja*/
	};
	
	FloatBuffer bufVertices;
	ByteBuffer bufColores;
	ShortBuffer bufIndices;
	
	public Rectangulo(){
		
		/* Lee los vértices */
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufVertices = bufByte.asFloatBuffer(); // Convierte de byte a float
		bufVertices.put(vertices);
		bufVertices.rewind(); // puntero al principio del buffer

		/* Lee los colores */
		bufColores = ByteBuffer.allocateDirect(colores.length);
		bufColores.put(colores);
		bufColores.rewind(); // puntero al principio del buffer

		/* Lee los indices */
		bufByte = ByteBuffer.allocateDirect(indices.length * 2);
		bufByte.order(ByteOrder.nativeOrder()); // Utiliza el orden de byte nativo
		bufIndices = bufByte.asShortBuffer(); // Convierte de byte a short
		bufIndices.put(indices);
		bufIndices.rewind(); // puntero al principio del buffer
		
	}
	
	public void dibuja(GL10 gl) {
		
		/* Se habilita el acceso al arreglo de vértices */
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		/* Se habilita el acceso al arreglo de colores */
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		/* Se especifica los datos del arreglo de vértices */
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);

		/* Se especifica los datos del arreglo de colores */
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, bufColores);

		/* Renderiza las primitivas desde los datos de los arreglos (vértices,
		 * colores e indices) */
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_SHORT, bufIndices);

		/* Se deshabilita el acceso al arreglo de vértices */
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

		/* Se deshabilita el acceso al arreglo de colores */
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		
	}
}
