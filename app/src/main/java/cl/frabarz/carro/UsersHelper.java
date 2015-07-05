package cl.frabarz.carro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UsersHelper extends SQLiteOpenHelper
{
    private static final int
        DB_VERSION = 1;
    private static final String
        DB_NAME = "users",
        USERS_TABLE_NAME = "users",
        USERS_TABLE_COLUMNS =
            "_id"       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username"  + " TEXT, " +
            "password"  + " TEXT, " +
            "nombre"    + " TEXT, " +
            "edad"      + " INTEGER, " +
            "genero"    + " INTEGER";

    public UsersHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " ("+ USERS_TABLE_COLUMNS + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(db);
    }

    public Long registrar(String username, String password, String nombre, String edad, String genero)
    {
        Long result = 0L;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("username", username);
        registro.put("password", password);
        registro.put("nombre"  , nombre);
        registro.put("edad"    , edad);
        registro.put("genero"  , genero);

        if (db != null)
        {
            result = db.insert(USERS_TABLE_NAME, null, registro);

            db.close();
        }

        return result;
    }

    public ContentValues recuperarUsuario(int user_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues result = new ContentValues();

        if (db != null)
        {
            String[] parametros = {Integer.toString(user_id)};
            Cursor c = db.rawQuery(
                "SELECT " + "*" +
                 " FROM " + USERS_TABLE_NAME +
                " WHERE " + "_id = ?",
                parametros);

            if (c.moveToFirst())
            {
                //result.put("id", c.getInt(0));
                result.put("username", c.getString(1));
                result.put("nombre", c.getString(3));
                result.put("edad", c.getInt(4));
                result.put("genero", c.getInt(5));
            }

            c.close();
            db.close();
        }

        return result;
    }

    public ContentValues autenticar(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues result = new ContentValues();

        if (db != null)
        {
            String[] parametros = {username, password};
            Cursor c = db.rawQuery(
                "SELECT " + "*" +
                 " FROM " + USERS_TABLE_NAME +
                " WHERE " + "username = ?" +
                  " AND " + "password = ?",
                parametros);

            if (c.moveToFirst())
            {
                result.put("id", c.getInt(0));
                result.put("username", c.getString(1));
                result.put("nombre", c.getString(3));
                result.put("edad", c.getInt(4));
                result.put("genero", c.getInt(5));
            }

            c.close();
            db.close();
        }

        return result;
    }
}
