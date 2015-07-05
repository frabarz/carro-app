package cl.frabarz.carro;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends Activity
{
    private EditText
        input_username,
        input_password;
    private UsersHelper
        db_proxy = new UsersHelper(this);

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

    private void iniciarSesion()
    {
        Toast aviso = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);

        String username = input_username.getText().toString();
        String password = input_password.getText().toString();

        if ( password.length() == 0 )
        {
            aviso.setText("No puedes dejar el campo de contraseña vacío.");
            aviso.show();
        }
        else
        {
            ContentValues user = db_proxy.autenticar(username, password);
            if ( user.containsKey("username") )
            {
                SharedPreferences prefs = getSharedPreferences("carroapp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("last_user_id", (int) user.get("id"));
                editor.commit();

                aviso.setText("Bienvenido");
                aviso.show();

                Intent landing = new Intent(LoginActivity.this, LandingActivity.class);
                startActivity(landing);

                this.finish();
            }
            else
            {
                aviso.setText("Contraseña incorrecta");
                aviso.show();
            }
        }
    }

    public void openRegisterForm(View view)
    {
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        register.putExtra("username", input_username.getText().toString());
        startActivityForResult(register, 0);
    }

    public void checkLoginData(View view)
    {
        iniciarSesion();
    }
}
