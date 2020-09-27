package com.example.conectandopuntos;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

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
        * Activity <- GLSurfaceView : Coloca la Vista de la Superficie del
        * OpenGL como un Contexto de ésta Actividad.
        */
        setContentView(superficie);
        //setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
