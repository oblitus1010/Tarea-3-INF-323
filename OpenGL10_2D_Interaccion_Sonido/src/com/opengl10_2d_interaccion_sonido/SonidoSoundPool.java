package com.opengl10_2d_interaccion_sonido;

import java.io.IOException;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Emite el efecto de sonido del archivo explosion.ogg, cuando se presiona
 * la pantalla.
 * 
 * @author Jhonny Felipez
 * @version 1.0 19/09/2015
 * 
 */
public class SonidoSoundPool {
    SoundPool soundPool;
    private int sonidoID = -1;
    public SonidoSoundPool(Context contexto, String nombreArchivo){
        
        /* Se crea el objeto SoundPool(max, tipo, calidad) */
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        try {
        	/* Provee el acceso a la carpeta assets */
            AssetManager am = contexto.getAssets();
            
            /* Abre el archivo */
            AssetFileDescriptor afd = am.openFd(nombreArchivo);
            
            /* Lee el archivo (descripcion, prioridad) */
            sonidoID = soundPool.load(afd, 1);
            
        } catch (IOException e) {
            //"No se puede cargar el archivo de efecto de sonido, " + e.getMessage());
        	e.printStackTrace();
        }
    }
    
    public void play(){
    	/* reproduce(sonidoID, volumen izq, volumen der, prioridad, repetición,
    	 * velocidad)
    	 * reproduce(id, [0..1], [0..1], [0..1], [-1 = c/repetición 0 = s/repetición],
    	 * [0.5 .. 2] 1 = normal)  */
        soundPool.play(sonidoID, 1, 1, 0, 0, 1);
    }
}
