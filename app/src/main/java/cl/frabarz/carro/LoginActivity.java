package cl.frabarz.carro;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
    private EditText input_username, input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_username = (EditText) findViewById(R.id.login_username);
        input_password = (EditText) findViewById(R.id.login_password);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle valores = data.getExtras();

        input_username.setText( valores.getString("username") );
        input_password.setText( valores.getString("password") );

        iniciarSesion();
    }

    private boolean autenticar(String username, String password)
    {
        return password.equals("asdf");
    }

    private void almacenarCredenciales(String username, String password)
    {
        //lalala (8)
    }

    private void iniciarSesion()
    {
        Toast aviso = Toast.makeText(getBaseContext(), "Iniciando sesión", Toast.LENGTH_SHORT);
        aviso.show();

        String username = input_username.getText().toString();
        String password = input_password.getText().toString();

        if ( autenticar(username, password) )
        {
            almacenarCredenciales(username, password);
            Intent landing = new Intent(LoginActivity.this, LandingActivity.class);
            startActivity(landing);
            finish();
        }
        else
        {
            aviso.setText("Contraseña incorrecta");
            aviso.show();
        }
    }

    public void openRegisterForm(View view)
    {
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(register, 0);
    }

    public void checkLoginData(View view)
    {
        iniciarSesion();
    }
}
