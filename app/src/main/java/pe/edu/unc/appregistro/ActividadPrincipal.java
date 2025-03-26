package pe.edu.unc.appregistro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import Models.Persona;
import accesoDatos.DAOPersona;

public class ActividadPrincipal extends AppCompatActivity {

    //public static List<Persona> listaPersona = new ArrayList<>();
    TextView tvContar, tvUltimaVez;
    int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_principal);
        //Herramienta Toolbar para menù
        Toolbar oBarra= findViewById(R.id.tbPrincipal);
        setSupportActionBar(oBarra);

        tvContar = findViewById(R.id.tvContador);
        tvUltimaVez = findViewById(R.id.tvUltimaVez);
        //Archivo de preferencia
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        contador = oFlujo.getInt("contar",1);
        tvContar.setText("N ingresos: "+contador);

        // Obtener la última fecha de uso
        long ultimaFecha = oFlujo.getLong("ultimaFecha", 0);
        if (ultimaFecha != 0) {
            // Configurar el formato de fecha y la zona horaria para Perú (PET, UTC-5)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("America/Lima")); // Zona horaria de Perú
            String fechaFormateada = sdf.format(new Date(ultimaFecha));
            tvUltimaVez.setText("Último uso: " + fechaFormateada);
        }  else {
            tvUltimaVez.setText("Último uso: Nunca");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        SharedPreferences.Editor oEditar = oFlujo.edit();

        // Incrementar el contador
        contador++;
        oEditar.putInt("contar",contador);

        // Guardar la fecha y hora actual al cerrar la aplicación
        long fechaActual = System.currentTimeMillis();
        oEditar.putLong("ultimaFecha", fechaActual);

        oEditar.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent oIntento=null;
        if(item.getItemId()==R.id.itemSalir){
            finish();
        }
        if (item.getItemId()==R.id.itemRegistrar){
            oIntento= new Intent(this, ActividadRegistrarPersonas.class);
            startActivity(oIntento);
        }
        if (item.getItemId()==R.id.itemListar){
            oIntento= new Intent(this, ActividadListarPersonas.class);
            startActivity(oIntento);
        }
        return super.onOptionsItemSelected(item);
    }
}