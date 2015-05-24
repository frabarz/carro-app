package cl.frabarz.carro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread() {
            public void run() {
                // La idea aquí es que la app revise si el usuario
                // guardó sus credenciales, para enviarlo a la
                // activity Login o a la activity Landing
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();

        //ImageView imagen = (ImageView) findViewById(R.id.landing_logo);
        //imagen.setImageResource(R.drawable.logo_big);
    }
}
