package cl.frabarz.carro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Bundle data = getIntent().getExtras();

        ImageView
            imagen = (ImageView) findViewById(R.id.producto_imagen);
        TextView
            nombre = (TextView) findViewById(R.id.producto_nombre),
            precio = (TextView) findViewById(R.id.producto_precio),
            descripcion = (TextView) findViewById(R.id.producto_descripcion);

        imagen.setImageResource(data.getInt("imagen"));
        nombre.setText(data.getString("nombre"));
        precio.setText(data.getString("precio"));
        descripcion.setText(data.getString("descripcion"));
    }

    public void launchCartActivity(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
