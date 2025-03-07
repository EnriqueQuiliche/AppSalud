package pe.edu.unc.appregistro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class MainActivity extends AppCompatActivity {

    EditText txtNom, txtAp, txtEd, txtDni, txtPe, txtAl;
    Button btnReg, btnLis;
    List<Persona> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNom = findViewById(R.id.txtNombres);
        txtAp = findViewById(R.id.txtApellidos);
        txtEd = findViewById(R.id.txtEdad);
        txtDni = findViewById(R.id.txtDNI);
        txtPe = findViewById(R.id.txtPeso);
        txtAl = findViewById(R.id.txtAltura);
        btnReg = findViewById(R.id.btnRegistrar);
        btnLis = findViewById(R.id.btnListar);

        lista = new ArrayList<>();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarPersona();
            }
        });

        btnLis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarPersonas();
            }
        });
    }

    private void registrarPersona() {
        //Tarea validar todas los EditText
        if (validarDatos()) {
            return;
        }

        //Almacenar en variables los valores EditText
        String nombres = txtNom.getText().toString();
        String apellidos = txtAp.getText().toString();
        int edad = Integer.valueOf(txtEd.getText().toString());
        String dni = txtDni.getText().toString();
        double peso = Double.valueOf(txtPe.getText().toString());
        double altura = Double.valueOf(txtAl.getText().toString());
        Persona oPersona = new Persona(nombres,apellidos,edad,dni,peso,altura);
        //Se registrara si el dni es valido
        if(oPersona.verificarDNI()){
            Toast.makeText(this,"Registro Correcto "+oPersona.toString(),Toast.LENGTH_SHORT).show();
            lista.add(oPersona);
            limpiar();
        }else {
            Toast.makeText(this,"No se registró, DNI invalido",Toast.LENGTH_SHORT).show();
        }
    }

    private void listarPersonas() {
        Intent intent = new Intent(this, ActividadLista.class);
        intent.putExtra("listaPersonas", new ArrayList<>(lista)); // Envía la lista como Serializable
        startActivity(intent);
    }

    private boolean validarDatos() {
        if (txtNom.getText().toString().trim().isEmpty()){
            txtNom.setError("Porfavor ingrese Nombres");
            txtNom.requestFocus();
            return true;
        }
        if (txtAp.getText().toString().trim().isEmpty()){
            txtAp.setError("Porfavor ingrese Apellidos");
            txtAp.requestFocus();
            return true;
        }
        if (txtEd.getText().toString().isEmpty()){
            txtEd.setError("Porfavor ingrese edad");
            txtEd.requestFocus();
            return true;
        }
        if (txtDni.getText().toString().isEmpty()){
            txtDni.setError("Porfavor ingrese DNI");
            txtDni.requestFocus();
            return true;
        }else {
            if (txtDni.getText().toString().length()<8 || txtDni.getText().toString().length()>8){
                txtDni.setError("Verifique su DNI");
                txtDni.requestFocus();
                return true;
            }
        }
        if (txtPe.getText().toString().isEmpty()){
            txtPe.setError("Porfavor ingrese Peso");
            txtPe.requestFocus();
            return true;
        }
        if (txtAl.getText().toString().isEmpty()){
            txtAl.setError("Porfavor ingrese Altura");
            txtAl.requestFocus();
            return true;
        }
        //Valida si todos los campos no estan vacios y que el dni tenga 8 digitos para indicar que la validación es correcta
        if (!txtNom.getText().toString().isEmpty() &&
                !txtAp.getText().toString().isEmpty() &&
                txtDni.getText().toString().length()==8 &&
                !txtEd.getText().toString().isEmpty() &&
                !txtPe.getText().toString().isEmpty() &&
                !txtAl.getText().toString().isEmpty()){

            Toast.makeText(this,"Validación Correcta",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void limpiar() {
        txtNom.setText("");
        txtAp.setText("");
        txtEd.setText("");
        txtDni.setText("");
        txtPe.setText("");
        txtAl.setText("");
        txtNom.requestFocus();
    }


}