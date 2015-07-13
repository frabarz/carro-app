package cl.frabarz.carro;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductosCursorAdapter extends CursorAdapter
{
    public ProductosCursorAdapter(Context context, Cursor c)
    {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.item_producto, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView nombre = (TextView) view.findViewById(R.id.producto_nombre);
        TextView precio = (TextView) view.findViewById(R.id.producto_precio);
        ImageView imagen = (ImageView) view.findViewById(R.id.producto_imagen);

        nombre.setText(cursor.getString(cursor.getColumnIndex("nombre")));
        precio.setText("$" + cursor.getString(cursor.getColumnIndex("precio")));
        imagen.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public Producto getItem(int position)
    {
        Cursor c = getCursor();
        Producto producto = null;

        if(c.moveToPosition(position)) {
            producto = new Producto();

            producto.setId(          c.getLong(   c.getColumnIndex("_id") ) );
            producto.setNombre(      c.getString( c.getColumnIndex("nombre") ) );
            producto.setPrecio(      c.getInt(    c.getColumnIndex("precio") ) );
            producto.setDescripcion( c.getString( c.getColumnIndex("descrip") ) );
            producto.setCategoria(   c.getString( c.getColumnIndex("categoria") ) );
            producto.setImagen(      c.getString( c.getColumnIndex("imagen") ) );
        }

        return producto;
    }
}
