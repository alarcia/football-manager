package com.damcotech.football_manager;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Anotaciones extends Fragment implements View.OnClickListener {


    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    ListView lVArchivos;
    // Variables para grabación (botón)
    private Button btnRecord;
    private boolean grabando = false;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mMediaPlayer = null;

    // pendiente ver si es necesaria
    //private final String strRutaCompleta = Environment.getExternalStorageDirectory().getAbsolutePath() + "/anotaciones_football_manager/";
    private ImageView ivGrabando;
    private TextView tvGrabando;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View anotaciones = inflater.inflate(R.layout.anotaciones, container, false);


        // Instanciaciones para la grabación
        btnRecord = (Button) anotaciones.findViewById(R.id.btnGrabar);
        btnRecord.setOnClickListener(this);
        ivGrabando = (ImageView) anotaciones.findViewById(R.id.ivGrabando);
        tvGrabando = (TextView) anotaciones.findViewById(R.id.tvGrabando);

        // Variables para el listView
        lVArchivos = (ListView) anotaciones.findViewById(R.id.lVArchivos);

        // Creación de la carpeta si no existe ya
        List<String> aLArchivosEnCarpeta = new ArrayList<>();
        File directory = Environment.getExternalStorageDirectory();
        File f = new File(directory + "/anotaciones_football_manager");

        if (!f.exists()) {
            f.mkdirs();
        }

        // Información de la lista con los archivos
        File list[] = f.listFiles();

        for (File aList : list) {
            aLArchivosEnCarpeta.add(aList.getName());
        }

        // Información del adaptador con la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.item_listview_image, R.id.tvItemLista, aLArchivosEnCarpeta);
        lVArchivos.setAdapter(adapter);

        // Onclick simple de cada item para reproducir
        lVArchivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                // Recuperamos el nombre del item clickado, directamente desde el textview
                String opcionSeleccionada = ((TextView) v.findViewById(R.id.tvItemLista)).getText().toString();

                // Lectura del archivo
                FileDescriptor fd;

                try {
                    File baseDir = Environment.getExternalStorageDirectory();
                    String audioPath = baseDir.getAbsolutePath() + "/anotaciones_football_manager/" + opcionSeleccionada;
                    FileInputStream fis = new FileInputStream(audioPath);
                    fd = fis.getFD();


                    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }

                    if (fd != null) {

                        MediaPlayer mMediaPlayer = new MediaPlayer();
                        mMediaPlayer.setDataSource(fd);
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    }


                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }

            }


        });

        // Onclick largo para borrar
        lVArchivos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                // Recuperamos el nombre del item clickado, directamente desde el textview
                String opcionSeleccionada = ((TextView) v.findViewById(R.id.tvItemLista)).getText().toString();

                // Creamos la ruta de la carpeta de las notas
                String strRutaAbsoluta = Environment.getExternalStorageDirectory().getAbsolutePath() + "/anotaciones_football_manager/";

                // Llamamos al diálogo de confirmación y le pasamos la ruta del archivo final seleccionado
                dialogoConfirmar(strRutaAbsoluta + opcionSeleccionada);


                return true;
            }
        });


        return anotaciones;
    }

    private void dialogoConfirmar(final String strRutaArchivo) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setTitle("¿Estás seguro?");

        alertDialogBuilder.setMessage("Se borrará la nota de audio: ");

        alertDialogBuilder.setNegativeButton("No, conservarla", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setPositiveButton("Sí, borrarla", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                File file = new File(strRutaArchivo);
                boolean deleted = file.delete();

                refrescarLista();

                Toast.makeText(getActivity().getBaseContext(), "¡Archivo borrado!", Toast.LENGTH_SHORT).show();


            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGrabar:

                if (grabando) {
                    ivGrabando.setVisibility(View.INVISIBLE);
                    tvGrabando.setVisibility(View.INVISIBLE);

                    grabando = false;
                    btnRecord.setText(getResources().getString(R.string.grabar));
                    //btnRecord.setBackground(getResources().getDrawable(R.drawable.grabar));
                    onRecord(grabando);
                } else {
                    ivGrabando.setVisibility(View.VISIBLE);
                    tvGrabando.setVisibility(View.VISIBLE);
                    crearArchivo();
                    grabando = true;
                    btnRecord.setText(getResources().getString(R.string.guardar));
                    //btnRecord.setBackground(getResources().getDrawable(R.drawable.escudo));
                    onRecord(grabando);
                }
                break;


        }
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        Toast.makeText(getActivity().getBaseContext(), "Archivo guardado", Toast.LENGTH_SHORT).show();
        refrescarLista();
    }

    // Metodo que genera el archivo con la fecha y la hora
    private void crearArchivo() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss_dd-MM-yyyy", Locale.US);
        Date now = new Date();

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/anotaciones_football_manager";
        mFileName += "/" + formatter.format(now) + ".3gp";
    }

    private void refrescarLista() {

        List<String> aLArchivosEnCarpeta = new ArrayList<>();
        File directory = Environment.getExternalStorageDirectory();
        File f = new File(directory + "/anotaciones_football_manager");

        // Información de la lista con los archivos
        File list[] = f.listFiles();

        for (File aList : list) {
            aLArchivosEnCarpeta.add(aList.getName());
        }

        // Información del adaptador con la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.item_listview_image, R.id.tvItemLista, aLArchivosEnCarpeta);
        lVArchivos.setAdapter(adapter);

    }

}