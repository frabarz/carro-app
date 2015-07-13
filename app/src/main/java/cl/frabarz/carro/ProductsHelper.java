package cl.frabarz.carro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ProductsHelper extends SQLiteOpenHelper
{
    private static final int
        DB_VERSION = 1;
    private static final String
        DB_NAME = "Products",
        PROD_TABLE_NAME = "listado",
        PROD_TABLE_COLUMNS =
            "_id"       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre"    + " TEXT, " +
            "precio"    + " INTEGER, " +
            "descrip"   + " TEXT, " +
            "categoria" + " TEXT, " +
            "imagen"    + " TEXT",
        CART_TABLE_NAME = "carro",
        CART_TABLE_COLUMNS =
            "_id"        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id"    + " INTEGER, " +
            "product_id" + " INTEGER",
        WISH_TABLE_NAME = "wishlist",
        WISH_TABLE_COLUMNS =
            "_id"        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id"    + " INTEGER, " +
            "product_id" + " INTEGER";

    public ProductsHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + PROD_TABLE_NAME + " (" + PROD_TABLE_COLUMNS + ")");
        db.execSQL("CREATE TABLE " + CART_TABLE_NAME + " (" + CART_TABLE_COLUMNS + ")");
        db.execSQL("CREATE TABLE " + WISH_TABLE_NAME + " (" + WISH_TABLE_COLUMNS + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ PROD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CART_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ WISH_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertProduct(String nombre, int precio, String descrip, String categoria, String imagen)
    {
        Long result = -1L;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("precio"   , precio);
        registro.put("descrip"  , descrip);
        registro.put("categoria", categoria);
        registro.put("imagen"   , imagen);

        if (db != null)
        {
            result = db.insert(PROD_TABLE_NAME, null, registro);
            db.close();
        }

        return result > -1L;
    }

    public boolean insertProducts(ArrayList<Producto> productos)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Long result = -1L;

        if (db != null)
        {
            for (Producto producto : productos)
                result = db.insert(PROD_TABLE_NAME, null, producto.getAsContentValues());
            db.close();
        }

        return result > -1L;
    }

    public Producto getProduct(long product_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Producto result = new Producto();

        if (db != null)
        {
            String[] parametros = {Long.toString(product_id)};
            // rawQuery("SELECT * FROM "+ PROD_TABLE_NAME +" WHERE id = ?", parametros)
            Cursor c = db.query(PROD_TABLE_NAME, null, "_id = ?", parametros, null, null, null);

            if (c.moveToFirst())
            {
                result.setId(          c.getLong(   c.getColumnIndex("_id") ) );
                result.setNombre(      c.getString( c.getColumnIndex("nombre") ) );
                result.setPrecio(      c.getInt(    c.getColumnIndex("precio") ) );
                result.setDescripcion( c.getString( c.getColumnIndex("descrip") ) );
                result.setCategoria(   c.getString( c.getColumnIndex("categoria") ) );
                result.setImagen(      c.getString( c.getColumnIndex("imagen") ) );
            }

            c.close();
            db.close();
        }

        return result;
    }

    public Cursor getProductsCategoryCursor(String categoria)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] parametros = {categoria};
        // rawQuery("SELECT * FROM PROD_TABLE_NAME WHERE categoria = ?", parametros)
        Cursor c = db.query(PROD_TABLE_NAME, null, "categoria = ?", parametros, null, null, null, null);

        if (c != null)
            c.moveToFirst();

        db.close();

        return c;
    }

    public Cursor getProductsSearchCursor(String filter)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        //String[] parametros = {"%" + filter + "%"};
        Cursor c;

        if (filter == null || filter.length() == 0)
            c = db.query(PROD_TABLE_NAME, null, null, null, null, null, null, null);
        else
            // https://code.google.com/p/android/issues/detail?id=3153
            c = db.query(PROD_TABLE_NAME, null, "nombre LIKE '%" + filter + "%'", null, null, null, null, null);

        if (c != null)
            c.moveToFirst();

        db.close();

        return c;
    }

    public Cursor getCartList(int user_id)
    {
        Cursor c;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] parametros = {Integer.toString(user_id)};
        c = db.rawQuery(
            "SELECT " + PROD_TABLE_NAME + "._id," +
                        PROD_TABLE_NAME + ".nombre," +
                        PROD_TABLE_NAME + ".precio," +
                        PROD_TABLE_NAME + ".descrip," +
                        PROD_TABLE_NAME + ".categoria," +
                        PROD_TABLE_NAME + ".imagen" +
             " FROM " + PROD_TABLE_NAME +
        " INNER JOIN "+ CART_TABLE_NAME +
               " ON " + PROD_TABLE_NAME + "._id = " + CART_TABLE_NAME + ".product_id" +
            " WHERE " + CART_TABLE_NAME + ".user_id = ?",
            parametros);

        if (c != null)
            c.moveToFirst();

        db.close();

        return c;
    }

    public Cursor getWishList(int user_id)
    {
        Cursor c;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] parametros = {Integer.toString(user_id)};
        c = db.rawQuery(
            "SELECT " + PROD_TABLE_NAME + "._id," +
                        PROD_TABLE_NAME + ".nombre," +
                        PROD_TABLE_NAME + ".precio," +
                        PROD_TABLE_NAME + ".descrip," +
                        PROD_TABLE_NAME + ".categoria," +
                        PROD_TABLE_NAME + ".imagen" +
             " FROM " + PROD_TABLE_NAME +
       " INNER JOIN " + WISH_TABLE_NAME +
               " ON " + PROD_TABLE_NAME + "._id = " + WISH_TABLE_NAME + ".product_id" +
            " WHERE " + WISH_TABLE_NAME + ".user_id = ?",
                parametros);

        if (c != null)
            c.moveToFirst();

        db.close();

        return c;
    }

    public boolean insertCart(int user_id, Long product_id)
    {
        Long result = -1L;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("user_id", user_id);
        registro.put("product_id", product_id);

        if (db != null)
        {
            result = db.insert(CART_TABLE_NAME, null, registro);
            db.close();
        }

        return result > -1L;
    }

    public boolean insertWish(int user_id, Long product_id)
    {
        Long result = -1L;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("user_id", user_id);
        registro.put("product_id", product_id);

        if (db != null)
        {
            result = db.insert(WISH_TABLE_NAME, null, registro);
            db.close();
        }

        return result > -1L;
    }

    public boolean removeCart(int user_id, Long product_id)
    {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        String[] registro = {Integer.toString(user_id), Long.toString(product_id)};

        if (db != null)
        {
            result = db.delete(CART_TABLE_NAME, "user_id = ? AND product_id = ?", registro);
            db.close();
        }

        return result > 0;
    }

    public boolean removeWish(int user_id, Long product_id)
    {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        String[] registro = {Integer.toString(user_id), Long.toString(product_id)};

        if (db != null)
        {
            result = db.delete(WISH_TABLE_NAME, "user_id = ? AND product_id = ?", registro);
            db.close();
        }

        return result > 0;
    }
}
