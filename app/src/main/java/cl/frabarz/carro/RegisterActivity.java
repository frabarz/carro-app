package cl.frabarz.carro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.RadioGroup;


public class RegisterActivity extends ActionBarActivity
{
    private EditText input_username, input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_username = (EditText) findViewById(R.id.register_username);
        input_password = (EditText) findViewById(R.id.register_password);
    }

    public void registrar()
    {
        EditText
                input_nombre = (EditText) findViewById(R.id.register_nombre),
                input_edad = (EditText) findViewById(R.id.register_edad);
        RadioGroup
                input_genero = (RadioGroup) findViewById(R.id.register_genero);

        String  nombre = input_nombre.getText().toString(),
                edad = input_edad.getText().toString(),
                genero = String.valueOf(input_genero.getCheckedRadioButtonId());

        // Aquí hay que enviar los datos al servicio web de registro
        finalizarRegistro();
    }

    private void finalizarRegistro()
    {
        Intent data = new Intent();
        data.putExtra("username", input_username.getText().toString());
        data.putExtra("password", input_password.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}