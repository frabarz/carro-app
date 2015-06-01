package cl.frabarz.carro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
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
