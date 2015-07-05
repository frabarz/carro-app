package cl.frabarz.carro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;


public class RegisterActivity extends ActionBarActivity
{
    private EditText
        input_username,
        input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_username = (EditText) findViewById(R.id.register_username);
        input_password = (EditText) findViewById(R.id.register_password);

        String username = getIntent().getStringExtra("username");
        if (username.length() > 0)
            input_username.setText(username);

        username = null;
    }

    public void registrar(View view)
    {
        EditText
                input_nombre = (EditText) findViewById(R.id.register_nombre),
                input_edad = (EditText) findViewById(R.id.register_edad);
        RadioGroup
                input_genero = (RadioGroup) findViewById(R.id.register_genero);

        String  username = input_username.getText().toString(),
                password = input_password.getText().toString(),
                nombre   = input_nombre.getText().toString(),
                edad     = input_edad.getText().toString(),
                genero   = String.valueOf(input_genero.getCheckedRadioButtonId());

        UsersHelper admin = new UsersHelper(this);
        admin.registrar(username, password, nombre, edad, genero);

        finalizarRegistro(getIntent());
    }

    private void finalizarRegistro(Intent data)
    {
        data.putExtra("username", input_username.getText().toString());
        data.putExtra("password", input_password.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}
