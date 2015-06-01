package cl.frabarz.carro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity
{
    public EditText input;

    private ProductosAdapter adapter;

    private TextWatcher filterTextWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.getFilter().filter(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        adapter = new ProductosAdapter(SearchActivity.this);
        adapter.generarProductos(100);

        ListView lista = (ListView) findViewById(R.id.activity_listview);
        lista.setAdapter(adapter);
        lista.setTextFilterEnabled(true);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Producto producto = adapter.getItem(position);
                Intent intent = new Intent(SearchActivity.this, ProductActivity.class);

                intent.putExtra("id", producto.getId());
                intent.putExtra("nombre", producto.getNombre());
                intent.putExtra("precio", producto.getPrecio());
                intent.putExtra("descripcion", producto.getDescripcion());
                intent.putExtra("imagen", producto.getImagen());

                startActivity(intent);
            }
        });

        input = (EditText) findViewById(R.id.search_input);
        input.addTextChangedListener(filterTextWatcher);
        if (input.requestFocus())
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        input.removeTextChangedListener(filterTextWatcher);
    }

    public void launchCartActivity(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}