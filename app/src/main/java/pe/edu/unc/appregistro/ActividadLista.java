package pe.edu.unc.appregistro;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class ActividadLista extends AppCompatActivity {

    ListView lvPersonas;
    List<Persona> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ly_lista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvPersonas = findViewById(R.id.lvListaPersonas);
        // Recibir la lista de personas
        lista = (List<Persona>) getIntent().getSerializableExtra("listaPersonas");
        // Mostrar los datos en el ListView
        List<String> datosPersonas = new ArrayList<>();
        for (Persona persona : lista) {
            String[] tipoPeso = {"debajo de ideal","ideal","sobre lo ideal"};
            datosPersonas.add(persona.getNombre() + " " + persona.getApellido() +
                    "\nEdad: " + persona.getEdad() +
                    "\nDNI: " + persona.getDni() +
                    "\nIMC: " + tipoPeso[persona.calcularIMC()+1] +
                    "\nMayor de Edad: " + (persona.esMayorDeEdad() ? "Sí" : "No"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosPersonas);
        lvPersonas.setAdapter(adapter);

    }

}