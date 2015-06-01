package cl.frabarz.carro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryActivity extends Activity
{
    private ProductosAdapter
        adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        adapter = new ProductosAdapter(CategoryActivity.this);
        adapter.generarProductos(100);

        ListView lista = (ListView) findViewById(R.id.categoria_listview);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Producto producto = adapter.getItem(position);
                Intent intent = new Intent(CategoryActivity.this, ProductActivity.class);

                intent.putExtra("id", producto.getId());
                intent.putExtra("nombre", producto.getNombre());
                intent.putExtra("precio", producto.getPrecio());
                intent.putExtra("descripcion", producto.getDescripcion());
                intent.putExtra("imagen", producto.getImagen());

                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        TextView titulo = (TextView) findViewById(R.id.categoria_title);
        titulo.setText(intent.getStringExtra("titulo"));
    }

    public void launchCartActivity(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
