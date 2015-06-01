package cl.frabarz.carro;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

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

        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(LoginActivity.this).getAccounts();
        for (Account account: accounts)
        {
            if (emailPattern.matcher(account.name).matches())
            {
                input_username.setText(account.name);
                break;
            }
        }

        emailPattern = null;
        accounts = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            Bundle valores = data.getExtras();

            input_username.setText( valores.getString("username") );
            input_password.setText( valores.getString("password") );

            iniciarSesion();
        }
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
        Toast aviso = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);

        String username = input_username.getText().toString();
        String password = input_password.getText().toString();

        if ( autenticar(username, password) )
        {
            aviso.setText("Bienvenido");
            aviso.show();

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
