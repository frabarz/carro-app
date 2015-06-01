package cl.frabarz.carro;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;

public class ProductosAdapter extends ArrayAdapter<Object> implements Filterable
{
    Context context;
    private ArrayList<Producto>
        complete_list,
        public_list;

    public ProductosAdapter(Context context)
    {
        super(context, R.layout.item_producto);
        this.context = context;
        this.complete_list = new ArrayList<Producto>();
        this.public_list = new ArrayList<Producto>();
    }

    public void add(Producto producto)
    {
        public_list.add(producto);
        complete_list.add(producto);
    }

    @Override
    public int getCount()
    {
        return public_list.size();
    }

    @Override
    public Producto getItem(int position)
    {
        return public_list.get(position);
    }

    @Override
    public Filter getFilter()
    {
        return new ProductoFilter();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        PlaceHolder placeHolder;
        Producto producto = public_list.get(position);

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
        placeHolder.imagen.setImageResource(R.mipmap.ic_launcher);

        return (convertView);
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

    private class ProductoFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults result = new FilterResults();

            if (constraint == null || constraint.length() == 0)
            {
                result.values = complete_list;
                result.count = complete_list.size();
            }
            else
            {
                ArrayList<Producto> filtered_list = new ArrayList<Producto>();
                CharSequence filtro = (CharSequence) unaccent(constraint.toString());

                for (Producto item: complete_list)
                {
                    if (item.getNombreNormalizado().contains(filtro))
                        filtered_list.add(item);
                }

                result.values = filtered_list;
                result.count = filtered_list.size();
            }

            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else
            {
                public_list = (ArrayList<Producto>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    public void generarProductos(int total)
    {
        Producto producto;
        Random r = new Random();

        String[] lipsum = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris. Mauris vitae nisi at sem facilisis semper ac in est.",
            "Maecenas congue ligula ac quam viverra nec consectetur ante hendrerit. Donec et mollis dolor. Praesent et diam eget libero egestas mattis sit amet vitae augue.",
            "Nam tincidunt congue enim, ut porta lorem lacinia consectetur. Donec ut libero sed arcu vehicula ultricies a non tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Aenean ut gravida lorem. Ut turpis felis, pulvinar a semper sed, adipiscing id dolor. Pellentesque auctor nisi id magna consequat sagittis.",
            "Curabitur dapibus enim sit amet elit pharetra tincidunt feugiat nisl imperdiet. Ut convallis libero in urna ultrices accumsan. Donec sed odio eros.",
            "Donec viverra mi quis quam pulvinar at malesuada arcu rhoncus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In rutrum accumsan ultricies.",
        };
        String[] elems = {
            "Automóvil",
            "Botas",
            "Chaqueta de cuero",
            "Disco duro portátil",
            "Estufa eléctrica",
            "Frigobar",
            "Guantes de trabajo",
            "Horno eléctrico",
            "Impresora fotocopiadora",
            "Juego de sábanas",
            "Lámpara de escritorio",
            "Mochila de viaje",
            "Pantalón de vestir",
            "Radio-reloj despertador",
            "Smartphone",
            "Toallas de playa",
            "Utensilios de cocina",
            "Velador de madera",
            "Wurlitzer",
            "Zapatillas deportivas"
        };
        int n_elems = elems.length;

        for (int i = 0; i < total; i++)
        {
            producto = new Producto();
            producto.setId(i+1);
            producto.setNombre( elems[r.nextInt(n_elems)] );
            producto.setDescripcion( lipsum[r.nextInt(6)] );
            producto.setPrecio( "$" + ((r.nextInt(15) + 1) * 1000) );
            //producto.setCategoria();

            complete_list.add(producto);
            public_list.add(producto);
        }

        r = null;
        lipsum = null;
        elems = null;
        producto = null;
    }

    private String unaccent(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
