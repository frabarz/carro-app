package cl.frabarz.carro;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class CategoryActivity extends Activity
{
    public ArrayList<Producto> Array_Productos = new ArrayList<Producto>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        cargarNoticias();

        ListView lista = (ListView) findViewById(R.id.categoria_listview);
        ProductosAdapter adapter = new ProductosAdapter(this, Array_Productos);
        lista.setAdapter(adapter);
    }

    private void cargarNoticias()
    {
        Producto producto;
        Random r = new Random();

        Resources res = getResources();
        //String lipsum = res.getString(R.string.lipsum);
        String[] elems = res.getStringArray(R.array.example_products);
        Integer n_elems = elems.length;

        for (int i = 0; i < 100; i++)
        {
            producto = new Producto();
            producto.setId(i);
            producto.setNombre( elems[r.nextInt(n_elems)] );
            //producto.setDescripcion(lipsum);
            producto.setPrecio( "$" + ((r.nextInt(15) + 1) * 1000) );

            Array_Productos.add(producto);
        }
    }
}
