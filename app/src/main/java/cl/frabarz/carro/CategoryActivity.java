package cl.frabarz.carro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public class CategoryActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String label_categoria = getIntent().getStringExtra("titulo");

        TextView pholder_titulo = (TextView) findViewById(R.id.categoria_title);
        pholder_titulo.setText(label_categoria);
    }

    public void launchCartActivity(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
