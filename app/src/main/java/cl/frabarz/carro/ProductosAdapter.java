package cl.frabarz.carro;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductosAdapter extends ArrayAdapter<Object>
{
    Context context;
    private ArrayList<Producto> productos;

    public ProductosAdapter(Context context, ArrayList<Producto> productos)
    {
        super(context, R.layout.item_producto);
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount()
    {
        return productos.size();
    }

    private static class PlaceHolder
    {
        TextView nombre;
        TextView precio;
        ImageView imagen;

        public static PlaceHolder generate(View convertView)
        {
            PlaceHolder placeHolder = new PlaceHolder();

            placeHolder.nombre = (TextView) convertView.findViewById(R.id.producto_nombre);
            placeHolder.precio = (TextView) convertView.findViewById(R.id.producto_precio);
            placeHolder.imagen = (ImageView) convertView.findViewById(R.id.producto_imagen);

            return placeHolder;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        PlaceHolder placeHolder;
        Producto producto = productos.get(position);

        if (convertView == null)
        {
            convertView = View.inflate(context, R.layout.item_producto, null);
            placeHolder = PlaceHolder.generate(convertView);
            convertView.setTag(placeHolder);
        }
        else
        {
            placeHolder = (PlaceHolder) convertView.getTag();
        }

        placeHolder.nombre.setText(producto.getNombre());
        placeHolder.precio.setText(producto.getPrecio());
        placeHolder.imagen.setImageResource(R.drawable.logo_big);

        return (convertView);
    }
}
