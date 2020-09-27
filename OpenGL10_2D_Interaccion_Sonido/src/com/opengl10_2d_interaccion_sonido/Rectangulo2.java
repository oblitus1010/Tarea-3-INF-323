package com.opengl10_2d_interaccion_sonido;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Rectangulo2 {
	private float vertices[] = new float[] {
			 //Rectangulo naranja
			-1, 3, //0
			 2, 3, //1
			 2, 6, //2
			-1, 6  //3
		};

		private byte colores[] = new byte[] {
			//Rectangulo naranja
			(byte)255, (byte)128, 0, 0, //0
			(byte)255, (byte)128, 0, 0, //1
			(byte)255, (byte)128, 0, 0, //2
			(byte)255, (byte)128, 0, 0, //3
		};
		
		private short indices[] = new short [] { 
			 0, 1, 2, 0, 2, 3, // Rectángulo naranja
			 
		};
		
		FloatBuffer bufVertices;
		ByteBuffer bufColores;
		ShortBuffer bufIndices;
		
		public Rectangulo2(){
			
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
