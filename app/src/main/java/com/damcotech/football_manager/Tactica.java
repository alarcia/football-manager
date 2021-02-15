package com.damcotech.football_manager;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Tactica extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //construimos y añadimos el View con el inflater
        View tactics = inflater.inflate(R.layout.tactica, container, false);

        //Fichas ADAE

        //Referenciamos el ImageView adae1 con el XML imageView11
        ImageView adae1 = (ImageView) tactics.findViewById(R.id.imageView11);
        //Llamamos al codigo del Drag and Drop
        adae1.setOnTouchListener(handlerMover);

        //Referenciamos el ImageView adae2 con el XML imageView12
        ImageView adae2 = (ImageView) tactics.findViewById(R.id.imageView12);
        //Llamamos al codigo del Drag and Drop
        adae2.setOnTouchListener(handlerMover);

        //Referenciamos el ImageView adae3 con el XML imageView13
        ImageView adae3 = (ImageView) tactics.findViewById(R.id.imageView13);
        //Llamamos al codigo del Drag and Drop
        adae3.setOnTouchListener(handlerMover);

        //Referenciamos el ImageView adae4 con el XML imageView14
        ImageView adae4 = (ImageView) tactics.findViewById(R.id.imageView14);
        //Llamamos al codigo del Drag and Drop
        adae4.setOnTouchListener(handlerMover);

        //Referenciamos el ImageView adae5 con el XML imageView15
        ImageView adae5 = (ImageView) tactics.findViewById(R.id.imageView15);
        adae5.setOnTouchListener(handlerMover);

        //Fichas Rival

        //Referenciamos el ImageView rival1 con el XML imageView21
        ImageView rival1 = (ImageView) tactics.findViewById(R.id.imageView21);
        //Llamamos al codigo del Drag and Drop
        rival1.setOnTouchListener(handlerMover);

        //Referenciamos el ImageView rival2 con el XML imageView22
        ImageView rival2 = (ImageView) tactics.findViewById(R.id.imageView22);
        //Llamamos al codigo del Drag and Drop
        rival2.setOnTouchListener(handlerMover);

        //Referenciamos el ImageView rival3 con el XML imageView23
        ImageView rival3 = (ImageView) tactics.findViewById(R.id.imageView23);
        //Llamamos al codigo del Drag and Drop
        rival3.setOnTouchListener(handlerMover);

        //Referenciamos el ImageView rival4 con el XML imageView24
        ImageView rival4 = (ImageView) tactics.findViewById(R.id.imageView24);
        rival4.setOnTouchListener(handlerMover);

        //Referenciamos el ImageView rival5 con el XML imageView25
        ImageView rival5 = (ImageView) tactics.findViewById(R.id.imageView25);
        //Llamamos al codigo del Drag and Drop
        rival5.setOnTouchListener(handlerMover);

        return tactics;
    }


    //Codigo del Drag and Drop
    private final View.OnTouchListener handlerMover = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            PointF DownPT = new PointF();
            PointF StartPT;
            new PointF();
            int eid = event.getAction();
            switch (eid) {
                case MotionEvent.ACTION_MOVE:
                    // Obtenemos la posicion actual del elemento
                    StartPT = new PointF(v.getX(), v.getY());

                    // Calculamos el desplazamiento
                    PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);

                    // Asignamos al elemento la posicion actual menos un valor que se define para
                    // que el elemento quede centrado con respecto a nuestro dedo.
                    int modificarX = 20;
                    v.setX((StartPT.x + mv.x) - modificarX);
                    int modificarY = 20;
                    v.setY((StartPT.y + mv.y) - modificarY);


                    break;
                case MotionEvent.ACTION_DOWN:
                    // Guardamos la posicion inicial
                    DownPT.x = event.getX();
                    DownPT.y = event.getY();

                    break;
                case MotionEvent.ACTION_UP:
                    // En esta parte se podran guardar en una base de datos
                    // la nueva posicion para que ese elemento se muestre en dicha posicion
                    // la proxima vez que abramos la aplicacion.
                    // hariamos algo asi:
                    //      adae1.setPosX(v.getX());
                    //		adae1.setPosY(v.getY());
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    @Override
    public void onClick(View v) {

    }
}