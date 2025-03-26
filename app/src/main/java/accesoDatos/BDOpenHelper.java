package accesoDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDOpenHelper  extends SQLiteOpenHelper {

    String tabla_Persoma = "CREATE  TABLE Persona(IdPersona Integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
            " nombre VARCHAR(40) NOT NULL, apellido VARCHAR(40) NOT NULL," +
            " sexo VARCHAR(10) NOT NUlL, ciudad VARCHAR (30) NOT NULL, edad Integer NOT NULL," +
            " dni VARCHAR(8) NOT NULL, peso DOUBLE NOT NULL, altura DOUBLE NOT NULL, foto BLOB )";

    public BDOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla_Persoma);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Persona");
        db.execSQL(tabla_Persoma);
    }
}
