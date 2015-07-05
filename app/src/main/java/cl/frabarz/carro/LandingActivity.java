package cl.frabarz.carro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LandingActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        int user_id = 0;
        SharedPreferences prefs = getSharedPreferences("carroapp", Context.MODE_PRIVATE);
        if (prefs != null)
            user_id = prefs.getInt("last_user_id", 0);

        UsersHelper db_proxy = new UsersHelper(this);
        ContentValues user = db_proxy.recuperarUsuario(user_id);

        TextView placeholder_nombre = (TextView) findViewById(R.id.welcome);
        placeholder_nombre.setText("Bienvenido de vuelta, " + user.get("nombre"));
    }

    public void launchCartActivity(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void launchCategoryActivity(View view)
    {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("titulo", ((Button) view).getText());
        startActivity(intent);
    }

    public void launchSearchActivity(View view)
    {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
