package pe.edu.unc.appregistro;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Models.Persona;
import accesoDatos.DAOPersona;

public class ActividadListarPersonas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_listar_personas);
        //codigo toolbar
        Toolbar oBarra= findViewById(R.id.tbListaPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lvPersonas = findViewById(R.id.lvListaPersonas);
        // Recibir la lista de personas
        //lvPersonas.setAdapter(new ArrayAdapter<Persona>(this, android.R.layout.simple_list_item_1, ActividadRegistrarPersonas.lista));
        DAOPersona oDAOPersona = new DAOPersona();
        oDAOPersona.cargarLista(this);
        lvPersonas.setAdapter(new AdaptadorPersonas(oDAOPersona,this));

    }

}