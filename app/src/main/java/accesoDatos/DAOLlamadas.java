package accesoDatos;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class DAOLlamadas {
    private Uri oTabla;
    private String[] oColumna;

    public DAOLlamadas() {
        oTabla = CallLog.Calls.CONTENT_URI;
        oColumna = new String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER};
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> listarLlamadas(Activity contexto, int tipoLlamada) {
        List<String> lista = new ArrayList<>();
        try {
            ContentResolver oCR = contexto.getContentResolver();

            // Definir filtro si se especificó un tipo
            String Filtro = null;
            String[] listaFiltro = null;
            if (tipoLlamada != -1) {
                Filtro = CallLog.Calls.TYPE + " = ?";
                listaFiltro = new String[]{String.valueOf(tipoLlamada)};
            }

            Cursor oRegistro = oCR.query(oTabla, oColumna, Filtro, listaFiltro,null);

            if (oRegistro.moveToFirst()) {
                do {
                    int iTipo = oRegistro.getInt(0);
                    String numero = oRegistro.getString(1);
                    String[] tipoLLamada = {"Entrante", "Saliente", "Perdida", "No Contestada", "Bloqueada"};
                    lista.add("Llamada " + tipoLLamada[iTipo - 1] + " numero: " + numero);
                } while (oRegistro.moveToNext());
                oRegistro.close();
            }
        } catch (Exception ex) {
            Toast.makeText(contexto, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return lista;
    }
}
