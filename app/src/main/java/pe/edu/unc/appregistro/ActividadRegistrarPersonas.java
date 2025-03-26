package pe.edu.unc.appregistro;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import Models.Persona;
import accesoDatos.DAOPersona;

public class ActividadRegistrarPersonas extends AppCompatActivity {

    EditText txtNom, txtAp, txtEd, txtDni, txtPe, txtAl;
    RadioGroup sexo;
    Spinner sp_ciudad;
    ImageView img;
    Button btnReg, btnLis;
    ///public static List<Persona> lista;
    String[] ciudades = {"Seleccionar ciudad","Cajamarca","Trujillo","Chiclayo"};
    //Uri imagenSeleccionada = null;
    byte[] imagenSeleccionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_registrar_personas);
        //codigo toolbar
        Toolbar oBarra= findViewById(R.id.tbRegistrarPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtNom = findViewById(R.id.txtNombres);
        txtAp = findViewById(R.id.txtApellidos);
        sexo = findViewById(R.id.rgSexo);
        sp_ciudad = findViewById(R.id.sp_ciudad);
        //Cargar los items para sp_ciudad
        sp_ciudad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ciudades));
        txtEd = findViewById(R.id.txtEdad);
        txtDni = findViewById(R.id.txtDNI);
        txtPe = findViewById(R.id.txtPeso);
        txtAl = findViewById(R.id.txtAltura);
        img = findViewById(R.id.imgFoto);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarFoto();
            }
        });
        btnReg = findViewById(R.id.btnRegistrar);
        btnLis = findViewById(R.id.btnListar);


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarPersona();
            }
        });

        btnLis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarPersona();
            }
        });

        //lista = new ArrayList<>();

    }

    private void registrarPersona() {
        //Tarea validar todas los EditText
        if (validarDatos()) {
            return;
        }

        //Almacenar en variables los valores EditText
        String nombres = txtNom.getText().toString();
        String apellidos = txtAp.getText().toString();
        String sexo = seleccionDeSexo();
        String ciudad = sp_ciudad.getSelectedItem().toString();
        int edad = Integer.valueOf(txtEd.getText().toString());
        String dni = txtDni.getText().toString();
        double peso = Double.valueOf(txtPe.getText().toString());
        double altura = Double.valueOf(txtAl.getText().toString());

        Persona oPersona = new Persona(nombres,apellidos,sexo,ciudad,edad,dni,peso,altura,imagenSeleccionada);
        //Se registrara si el dni es valido
        if(oPersona.verificarDNI()){
            Toast.makeText(this,"Registro Correcto "+oPersona.toString(),Toast.LENGTH_SHORT).show();
            //lista.add(oPersona);
            //ActividadPrincipal.listaPersona.add(oPersona);

            //utilizando mi clase DAOPersona
            DAOPersona oDAOPersona = new DAOPersona();
            oDAOPersona.Agregar(this,oPersona);

            cuadroDialogo();
            limpiar();
        }else {
            Toast.makeText(this,"No se registró, DNI invalido",Toast.LENGTH_SHORT).show();
        }
    }

    private void cuadroDialogo() {
        AlertDialog.Builder oDialogo=new AlertDialog.Builder(this);
        oDialogo.setTitle("Aviso");
        oDialogo.setMessage("¿Desea seguir registrando?");
        oDialogo.setIcon(R.drawable.baseline_add_alert_24);
        oDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ActividadRegistrarPersonas.this.finish();
            }
        });
        oDialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                limpiar();
            }
        });
        oDialogo.show();
    }

    private String seleccionDeSexo() {
        int identificador = sexo.getCheckedRadioButtonId();
        if (identificador==R.id.rbFemenino)
            return "Femenino";
        if (identificador==R.id.rbMasculino)
            return "Masculino";
        return "";
    }

    private void seleccionarFoto() {
        Intent oIntento = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Filtrar archivos de tipo  Imagen
        oIntento.setType("image/*");
        startActivityIfNeeded(Intent.createChooser(oIntento,"Seleccionar Imagen"),100);
    }

    //Metodo(superClase) que recoge la imagen seleccionada
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent oIntento) {
        super.onActivityResult(requestCode, resultCode, oIntento);
        if(requestCode == 100){ //Si el servicio está funcionando
            if(resultCode==RESULT_OK){//Quiere decir que si hicieron uso del servicio
                //imagenSeleccionada = oIntento.getData();
                //img.setImageURI(imagenSeleccionada);
                Uri foto = oIntento.getData();
                img.setImageURI(foto);
                img.buildDrawingCache();
                Bitmap oBitMap = img.getDrawingCache();
                ByteArrayOutputStream oFlujoSalida = new ByteArrayOutputStream();
                oBitMap.compress(Bitmap.CompressFormat.PNG,0,oFlujoSalida);
                imagenSeleccionada=oFlujoSalida.toByteArray();
            }
        }
    }

    private boolean validarDatos() {

        if (comprobarCampoObligatorio(txtNom,"Nombres")) return true;
        if (comprobarCampoObligatorio(txtNom,"Apellidos")) return true;
        if (seleccionDeSexo().isEmpty()){
            mostrarMensaje("Seleccionar un tipo de sexo de la persona");
            return true;
        }
        if (sp_ciudad.getSelectedItemPosition()==0){
            mostrarMensaje("Seleccionar ciudad de procedencia");
            sp_ciudad.requestFocus();
            return true;
        }
        if (comprobarCampoObligatorio(txtEd,"Edad")) return true;
        if (comprobarCampoObligatorio(txtNom,"DNI")) {
            return true;
        }else {
            if (txtDni.getText().toString().length()<8 || txtDni.getText().toString().length()>8){
                txtDni.setError("Verifique su DNI");
                txtDni.requestFocus();
                return true;
            }
        }
        if (comprobarCampoObligatorio(txtPe,"Peso")) return true;
        if (comprobarCampoObligatorio(txtAl,"Altura")) return true;
        if (imagenSeleccionada==null){
            mostrarMensaje("Seleccionar una foto de galería");
            img.requestFocus();
            return true;
        }

        //Valida si todos los campos no estan vacios y que el dni tenga 8 digitos para indicar que la validación es correcta
        if (txtDni.getText().toString().length()==8){

            Toast.makeText(this,"Validación Correcta",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    private boolean comprobarCampoObligatorio(EditText campo, String mensaje) {
        if (campo.getText().toString().trim().isEmpty()){
            campo.setError("Porfavor ingrese " +mensaje);
            campo.requestFocus();
            return true;
        }
        return false;
    }

    private void limpiar() {
        txtNom.setText("");
        txtAp.setText("");
        sexo.check(R.id.rbFemenino);
        sp_ciudad.setSelection(0);
        txtEd.setText("");
        txtDni.setText("");
        txtPe.setText("");
        txtAl.setText("");
        img.setImageResource(R.drawable.click);
        txtNom.requestFocus();
    }

    private void listarPersona() {
        DAOPersona oDAOPersona = new DAOPersona();
        //if (ActividadPrincipal.listaPersona.isEmpty())
        if (oDAOPersona.getSize()==0){
            Toast.makeText(this, "No hay personas registradas", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent oIntento = new Intent(this, ActividadListarPersonas.class);
        startActivity(oIntento);
    }

}