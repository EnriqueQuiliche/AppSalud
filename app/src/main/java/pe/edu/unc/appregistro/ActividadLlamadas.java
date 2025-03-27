package pe.edu.unc.appregistro;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import accesoDatos.DAOLlamadas;

public class ActividadLlamadas extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ly_llamadas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spTipoLLamada = findViewById(R.id.spTipoLllamada);
        String[] tipo = {"Todas las llamadas", "Entrante", "Saliente", "Perdida", "No Contestada", "Bloqueada"};
        spTipoLLamada.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tipo));

        ListView registro = findViewById(R.id.lvRegistroLllamadas);
        DAOLlamadas ollamadas = new DAOLlamadas();

        // Cargar todas las llamadas inicialmente (tipoFiltro = -1)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ollamadas.listarLlamadas(this, -1));
        registro.setAdapter(adapter);

        // Listener para filtrar al seleccionar un tipo
        spTipoLLamada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Posición 0: "Todas las llamadas" → tipoFiltro = -1
                // Posiciones 1-5 corresponden a tipos 1-5
                int tipoFiltro = (position == 0) ? -1 : position;
                List<String> llamadasFiltradas = ollamadas.listarLlamadas(ActividadLlamadas.this, tipoFiltro);
                adapter.clear();
                adapter.addAll(llamadasFiltradas);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada o mostrar todas
            }
        });
    }
}
