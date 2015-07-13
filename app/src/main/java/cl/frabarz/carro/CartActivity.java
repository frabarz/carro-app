package cl.frabarz.carro;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class CartActivity extends ActionBarActivity
{
    private int
        despacho;
    private TextView
        field_subtotal,
        field_despacho,
        field_total;

    private String[] opciones = {"CorreosChile", "Chilexpress", "DHL", "TNT"};

    private AdapterView.OnItemSelectedListener selectedSpinner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            // parent.getItemAtPosition(pos)
            despacho = (pos + 1) * 1000;
            updatePrices();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        field_subtotal = (TextView) findViewById(R.id.subtotal);
        field_despacho = (TextView) findViewById(R.id.despacho);
        field_total = (TextView) findViewById(R.id.total);

        Spinner field_despacho_selector = (Spinner) findViewById(R.id.despacho_selector);

        ArrayAdapter<String> despacho_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        field_despacho_selector.setAdapter(despacho_adapter);
        field_despacho_selector.setOnItemSelectedListener(selectedSpinner);
    }

    private void updatePrices()
    {
        ProductListFragment fragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.cart_listview);
        ProductosCursorAdapter adapter = (ProductosCursorAdapter) fragment.getListAdapter();
        Cursor c = adapter.getCursor();
        int subtotal = 0;

        c.moveToPosition(-1);
        while (c.moveToNext())
            subtotal += c.getInt(c.getColumnIndex("precio"));

        field_subtotal.setText("" + subtotal);
        field_despacho.setText("" + despacho);
        field_total.setText("" + (subtotal + despacho));
    }
}
