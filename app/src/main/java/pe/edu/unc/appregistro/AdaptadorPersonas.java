package pe.edu.unc.appregistro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Models.Persona;
import accesoDatos.DAOPersona;

public class AdaptadorPersonas extends BaseAdapter {

    //el equivalente a la lista seria DAOPersona
    //private List<Persona> listaPersonas;
    private DAOPersona oDAOPersona;
    private Context contexto;
    LayoutInflater inflater;

    //public AdaptadorPersonas(List<Persona> listaPersonas, Context contexto)
    public AdaptadorPersonas(DAOPersona oDAOPersona, Context contexto) {
        //this.listaPersonas = listaPersonas;
        this.oDAOPersona = oDAOPersona;
        this.contexto = contexto;
        inflater= (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //crea un numero especifica de tarjetas
    @Override
    public int getCount() {
        //return listaPersonas.size();
        return oDAOPersona.getSize();
    }

    //este metodo se utiliza si quieres optener algo de alguna de las tarjetas
    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista= inflater.inflate(R.layout.ly_item_persona,null);
        ImageView imgFoto = vista.findViewById(R.id.imgFoto);
        ImageView imgSexo = vista.findViewById(R.id.imgSexo);
        TextView nombre= vista.findViewById(R.id.lbNombreCompleto);
        TextView peso= vista.findViewById(R.id.lbTipoPeso);
        TextView maymenEdad= vista.findViewById(R.id.lbMayorMenorEdad);
        TextView procedencia= vista.findViewById(R.id.lbProcedencia);

        //llenar valores en los objetos de tipo View
        //Persona oP = listaPersonas.get(i);
        Persona oP = oDAOPersona.getObjetoPersona(i);

        //Ya no se utilizara el setImageUri
        //imgFoto.setImageURI(oP.getFoto());
        imgFoto.setImageBitmap(convertirBitMap(oP.getFoto())); //utilizamos un metodo para convertir un byte en formato BitMap
        nombre.setText(oP.getNombreCompleto());
        peso.setText(oP.getTipoPeso());
        maymenEdad.setText(oP.getTipoPersona());
        if(oP.getSexo().equals("Femenino")){
            imgSexo.setImageResource(R.drawable.femenino);
        }else {
            imgSexo.setImageResource(R.drawable.masculino);
        }
        procedencia.setText(oP.getCiudad());

        return vista;
    }

    private Bitmap convertirBitMap(byte[] foto) {
        return BitmapFactory.decodeByteArray(foto,0, foto.length);
    }
}
