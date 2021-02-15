package com.damcotech.football_manager;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Estadisticas extends Fragment implements View.OnClickListener {

    private final long lngUnMinuto = 60000;
    // Variables contador Veinte
    private Button btnStartStopVeinte;
    private Button btnRestartVeinte;
    private boolean isEmpezadoVeinte = false;
    private MiContador countDownTimerVeinte;
    private TextView tvContadorVeinte;
    private long lngTiempoActualVeinte;

    private final long lngIntervalo = 1;

    // Variables contador Dos
    private Button btnStartStopDos;
    private Button btnRestartDos;
    private boolean isEmpezadoDos = false;
    private MiContador countDownTimerDos;
    private TextView tvContadorDos;
    private long lngTiempoActualDos;

    // Variables marcador
    private TextView tvMarcadorLoc;
    private TextView tvMarcadorVis;
    private int contadorLoc = 0;
    private int contadorVis = 0;
    Button btnMasLoc;
    Button btnMenosLoc;
    Button btnMasVis;
    Button btnMenosVis;

    // Variables faltas
    private TextView tvFaltasCifra;
    private int faltasCifra = 0;
    private Button btnMasFalta;
    private Button btnReiniciarFaltas;

    // Variables partes
    private TextView tvParte;
    private int intParte = 1;

    // Reiniciar partido
    Button btnReiniciarPartido;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View estadisticas = inflater.inflate(R.layout.estadisticas, container, false);

        // Botones veinte
        btnStartStopVeinte = (Button) estadisticas.findViewById(R.id.btnStartStopVeinte);
        btnStartStopVeinte.setOnClickListener(this);
        btnRestartVeinte = (Button) estadisticas.findViewById(R.id.btnRestartVeinte);
        btnRestartVeinte.setOnClickListener(this);

        // Botones dos
        btnStartStopDos = (Button) estadisticas.findViewById(R.id.btnStartStopDos);
        btnStartStopDos.setOnClickListener(this);
        btnRestartDos = (Button) estadisticas.findViewById(R.id.btnRestartDos);
        btnRestartDos.setOnClickListener(this);

        // TextViews
        tvContadorVeinte = (TextView) estadisticas.findViewById(R.id.tvContadorVeinte);
        tvContadorDos = (TextView) estadisticas.findViewById(R.id.tvContadorDos);

        // Variables para el contador

        countDownTimerVeinte = new MiContador(lngUnMinuto * 20, lngIntervalo, 1);
        countDownTimerDos = new MiContador(lngUnMinuto * 2, lngIntervalo, 2);

        // Inicialización de botones y listeners
        btnMasLoc = (Button) estadisticas.findViewById(R.id.btnMasLoc);
        btnMasLoc.setOnClickListener(this);
        btnMenosLoc = (Button) estadisticas.findViewById(R.id.btnMenosLoc);
        btnMenosLoc.setOnClickListener(this);
        btnMasVis = (Button) estadisticas.findViewById(R.id.btnMasVis);
        btnMasVis.setOnClickListener(this);
        btnMenosVis = (Button) estadisticas.findViewById(R.id.btnMenosVis);
        btnMenosVis.setOnClickListener(this);
        btnMasFalta = (Button) estadisticas.findViewById(R.id.btnMasFalta);
        btnMasFalta.setOnClickListener(this);
        btnReiniciarFaltas = (Button) estadisticas.findViewById(R.id.btnReiniciarFaltas);
        btnReiniciarFaltas.setOnClickListener(this);

        // Variables para el marcador
        tvMarcadorLoc = (TextView) estadisticas.findViewById(R.id.tvMarcadorLoc);
        tvMarcadorLoc.setOnClickListener(this);
        tvMarcadorVis = (TextView) estadisticas.findViewById(R.id.tvMarcadorVis);
        tvMarcadorVis.setOnClickListener(this);

        btnReiniciarPartido = (Button) estadisticas.findViewById(R.id.btnReiniciarPartido);
        btnReiniciarPartido.setOnClickListener(this);

        // Variables para las faltas
        tvFaltasCifra = (TextView) estadisticas.findViewById(R.id.tvFaltasCifra);
        tvParte = (TextView) estadisticas.findViewById(R.id.partePartido);


        return estadisticas;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // Pulsamos Start/Stop
            case R.id.btnStartStopVeinte:
                if (!isEmpezadoVeinte) {
                    // Cuando está parado, comenzar
                    countDownTimerVeinte.start();
                    isEmpezadoVeinte = true;
                    btnStartStopVeinte.setBackgroundResource(R.drawable.stop_blanco);
                    btnRestartVeinte.setEnabled(false);
                    btnRestartVeinte.setBackgroundResource(R.drawable.restartdisabled);
                } else {
                    // Cuando le damos a parar, iniciamos la cuenta atrás
                    // desde el tiempo en que pausamos
                    countDownTimerVeinte.cancel();
                    countDownTimerVeinte = new MiContador(lngTiempoActualVeinte, lngIntervalo, 1);
                    isEmpezadoVeinte = false;
                    btnStartStopVeinte.setBackgroundResource(R.drawable.start_blanco);
                    btnRestartVeinte.setEnabled(true);
                    btnRestartVeinte.setBackgroundResource(R.drawable.restart_blanco);
                }
                break;

            // Pulsamos reset
            case R.id.btnRestartVeinte:

                countDownTimerVeinte.cancel();
                tvContadorVeinte.setTextColor(getResources().getColor(R.color.verde));
                tvContadorVeinte.setText("20:00");
                countDownTimerVeinte = new MiContador(lngUnMinuto * 20, lngIntervalo, 1);
                btnRestartVeinte.setEnabled(false);
                btnRestartVeinte.setBackgroundResource(R.drawable.restartdisabled);

                if (!isEmpezadoVeinte) {
                    btnStartStopVeinte.setEnabled(true);
                } else {
                    isEmpezadoVeinte = true;
                }


                break;

            case R.id.btnStartStopDos:
                if (!isEmpezadoDos) {
                    // Cuando está parado, comenzar
                    countDownTimerDos.start();
                    isEmpezadoDos = true;
                    btnStartStopDos.setBackgroundResource(R.drawable.stop_blanco);
                    btnRestartDos.setEnabled(false);
                    btnRestartDos.setBackgroundResource(R.drawable.restartdisabled);
                } else {
                    // Cuando le damos a parar, iniciamos la cuenta atrás
                    // desde el tiempo en que pausamos
                    countDownTimerDos.cancel();
                    countDownTimerDos = new MiContador(lngTiempoActualDos, lngIntervalo, 2);
                    isEmpezadoDos = false;
                    btnStartStopDos.setBackgroundResource(R.drawable.start_blanco);
                    btnRestartDos.setEnabled(true);
                    btnRestartDos.setBackgroundResource(R.drawable.restart_blanco);
                }

                break;

            case R.id.btnRestartDos:
                countDownTimerDos.cancel();
                tvContadorDos.setTextColor(getResources().getColor(R.color.verde));
                tvContadorDos.setText("02:00");
                countDownTimerDos = new MiContador(lngUnMinuto * 2, lngIntervalo, 2);
                btnRestartDos.setEnabled(false);
                btnRestartDos.setBackgroundResource(R.drawable.restartdisabled);

                if (!isEmpezadoDos) {
                    btnStartStopDos.setEnabled(true);
                } else {
                    isEmpezadoDos = true;
                }
                break;

            case R.id.btnMasLoc:

                contadorLoc++;
                tvMarcadorLoc.setText(String.format("%02d", contadorLoc));

                break;

            case R.id.btnMenosLoc:

                if (contadorLoc > 0) {

                    contadorLoc--;
                    tvMarcadorLoc.setText(String.format("%02d", contadorLoc));
                }

                break;

            case R.id.btnMasVis:

                contadorVis++;
                tvMarcadorVis.setText(String.format("%02d", contadorVis));

                break;

            case R.id.btnMenosVis:

                if (contadorVis > 0) {
                    contadorVis--;
                    tvMarcadorVis.setText(String.format("%02d", contadorVis));
                }
                break;

            case R.id.btnReiniciarPartido:

                dialogoReiniciar();

                break;

            case R.id.btnMasFalta:

                if (faltasCifra < 6) {
                    btnReiniciarFaltas.setEnabled(true);
                    btnReiniciarFaltas.setBackgroundResource(R.drawable.borde_blanco);
                    btnReiniciarFaltas.setTextColor(getResources().getColor(R.color.blanco));
                    faltasCifra++;
                    if (faltasCifra == 6) {
                        tvFaltasCifra.setTextColor(getResources().getColor(R.color.rojo));
                        tvFaltasCifra.setText(String.valueOf(faltasCifra));
                        btnMasFalta.setEnabled(false);
                        btnMasFalta.setTextColor(getResources().getColor(R.color.gris));
                        btnMasFalta.setBackgroundResource(R.drawable.borde_gris);
                        mostrarDialogo("¡6 FALTAS!", "Ahora cada falta será un doble penalti.");
                    } else if (faltasCifra == 5) {
                        tvFaltasCifra.setTextColor(getResources().getColor(R.color.naranja));

                        mostrarDialogo("¡¡AVISO: 5 FALTAS!!", "A partir de la sexta, cada falta es doble penalti.");

                        tvFaltasCifra.setText(String.valueOf(faltasCifra));
                    } else {

                        tvFaltasCifra.setTextColor(getResources().getColor(R.color.verde));

                        tvFaltasCifra.setText(String.valueOf(faltasCifra));
                    }
                }

                break;

            case R.id.btnReiniciarFaltas:
                tvFaltasCifra.setTextColor(getResources().getColor(R.color.verde));
                faltasCifra = 0;
                tvFaltasCifra.setText(String.valueOf(faltasCifra));
                btnMasFalta.setEnabled(true);
                btnMasFalta.setTextColor(getResources().getColor(R.color.blanco));
                btnMasFalta.setBackgroundResource(R.drawable.borde_blanco);
                btnReiniciarFaltas.setEnabled(false);
                btnReiniciarFaltas.setTextColor(getResources().getColor(R.color.gris));
                btnReiniciarFaltas.setBackgroundResource(R.drawable.borde_gris);


                break;

            case R.id.tvMarcadorLoc:
                contadorLoc++;
                tvMarcadorLoc.setText(String.format("%02d", contadorLoc));
                break;

            case R.id.tvMarcadorVis:
                contadorVis++;
                tvMarcadorVis.setText(String.format("%02d", contadorVis));
                break;

        }


    }

    private void mostrarDialogo(String strTitulo, String strMensaje) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setTitle(strTitulo);

        alertDialogBuilder.setMessage(strMensaje);

        alertDialogBuilder.setNeutralButton("Vale", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private void dialogoReiniciar() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setTitle("¿ESTÁS SEGURO?");

        alertDialogBuilder.setMessage("Se borrarán todas las estadísticas de todas las pestañas, " +
                "salvo las notas de audio.");

        alertDialogBuilder.setNegativeButton("No, mejor no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setPositiveButton("Sí, borrar todo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                countDownTimerDos.cancel();
                countDownTimerVeinte.cancel();
                Intent i = getActivity().getPackageManager()
                        .getLaunchIntentForPackage(getActivity().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public class MiContador extends CountDownTimer {

        // Esta id está asociada a cada contador, para distinguir el de 20 y el de 2 minutos
        // id = 1 es para 20 min
        // id = 2 es parai 2 mn
        private final int id;

        // Constructor
        public MiContador(long lngTiempoInicial, long lngIntervalo, int id) {
            super(lngTiempoInicial, lngIntervalo);
            this.id = id;
        }

        // Método que dice qué hace al terminar una cuenta atras
        @Override
        public void onFinish() {
            if (id == 2) {
                isEmpezadoDos = false;
                mostrarDialogo("¡Expulsión terminada!", "El jugador puede volver al campo.");
                countDownTimerDos.cancel();
                tvContadorDos.setTextColor(getResources().getColor(R.color.verde));
                tvContadorDos.setText("02:00");
                countDownTimerDos = new MiContador(lngUnMinuto * 2, lngIntervalo, 2);
                btnStartStopDos.setEnabled(true);
                btnStartStopDos.setBackgroundResource(R.drawable.start_blanco);

            }

            if (id == 1 && intParte == 1) {

                isEmpezadoVeinte = false;
                mostrarDialogo("Final de la primera parte", "Ha terminado la primera parte.");
                this.cancel();
                tvContadorVeinte.setTextColor(getResources().getColor(R.color.verde));
                tvContadorVeinte.setText("20:00");
                countDownTimerVeinte = new MiContador(lngUnMinuto * 20, lngIntervalo, 1);
                btnStartStopVeinte.setEnabled(true);
                btnStartStopVeinte.setBackgroundResource(R.drawable.start_blanco);

                intParte++;
                tvParte.setText(String.valueOf(intParte));
                btnRestartVeinte.setBackgroundResource(R.drawable.restartdisabled);
                btnRestartVeinte.setEnabled(false);

            } else if (id == 1 && intParte == 2) {

                isEmpezadoVeinte = false;
                mostrarDialogo("Final de la segunda parte", "Ha terminado el partido.");
                this.cancel();
                btnStartStopVeinte.setEnabled(false);

                btnStartStopVeinte.setBackgroundResource(R.drawable.start_gris);
                btnRestartVeinte.setEnabled(false);

                btnMasFalta.setEnabled(false);
                btnMasFalta.setBackgroundResource(R.drawable.borde_gris);
                btnMasFalta.setTextColor(getResources().getColor(R.color.gris));

                btnReiniciarFaltas.setEnabled(false);
                btnReiniciarFaltas.setTextColor(getResources().getColor(R.color.gris));
                btnReiniciarFaltas.setBackgroundResource(R.drawable.borde_gris);

                btnMasLoc.setEnabled(false);
                btnMasLoc.setTextColor(getResources().getColor(R.color.gris));
                btnMasLoc.setBackgroundResource(R.drawable.borde_gris);

                btnMasVis.setEnabled(false);
                btnMasVis.setTextColor(getResources().getColor(R.color.gris));
                btnMasVis.setBackgroundResource(R.drawable.borde_gris);

                btnMenosVis.setEnabled(false);
                btnMenosVis.setTextColor(getResources().getColor(R.color.gris));
                btnMenosVis.setBackgroundResource(R.drawable.borde_gris);

                btnMenosLoc.setEnabled(false);
                btnMenosLoc.setTextColor(getResources().getColor(R.color.gris));
                btnMenosLoc.setBackgroundResource(R.drawable.borde_gris);

                countDownTimerDos.cancel();
                btnStartStopDos.setEnabled(false);
                btnStartStopDos.setBackgroundResource(R.drawable.stop_gris);
                btnRestartDos.setEnabled(false);
                btnRestartDos.setBackgroundResource(R.drawable.restartdisabled);

            }

        }


        // Qué hacer en cada tick, debemos diferenciar dentro de qué contador se trata
        @Override
        public void onTick(long millisUntilFinished) {

            if (id == 1) {
                lngTiempoActualVeinte = millisUntilFinished;
                tvContadorVeinte.setText((new SimpleDateFormat("mm:ss", java.util.Locale.getDefault()))
                        .format(new Date(millisUntilFinished)));

            }

            if (id == 2) {
                lngTiempoActualDos = millisUntilFinished;
                tvContadorDos.setText((new SimpleDateFormat("mm:ss", java.util.Locale.getDefault()))
                        .format(new Date(millisUntilFinished)));

            }

            if (millisUntilFinished < 31000) {

                if (id == 1) {
                    tvContadorVeinte.setTextColor(getResources().getColor(R.color.rojo));

                }

                if (id == 2) {
                    tvContadorDos.setTextColor(getResources().getColor(R.color.rojo));

                }
            }
        }
    }
}