package cl.frabarz.carro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Random;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        generarProductos(10);

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
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void generarProductos(int total)
    {
        ProductsHelper db_proxy = new ProductsHelper(this);
        ArrayList<Producto> productos = new ArrayList<>();
        Producto producto;
        String nombre, bg, fg;
        Random r = new Random();

        String[] lipsum = {
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris. Mauris vitae nisi at sem facilisis semper ac in est.",
                "Maecenas congue ligula ac quam viverra nec consectetur ante hendrerit. Donec et mollis dolor. Praesent et diam eget libero egestas mattis sit amet vitae augue.",
                "Nam tincidunt congue enim, ut porta lorem lacinia consectetur. Donec ut libero sed arcu vehicula ultricies a non tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "Aenean ut gravida lorem. Ut turpis felis, pulvinar a semper sed, adipiscing id dolor. Pellentesque auctor nisi id magna consequat sagittis.",
                "Curabitur dapibus enim sit amet elit pharetra tincidunt feugiat nisl imperdiet. Ut convallis libero in urna ultrices accumsan. Donec sed odio eros.",
                "Donec viverra mi quis quam pulvinar at malesuada arcu rhoncus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In rutrum accumsan ultricies.",
        };
        String[] elems = {
                "Automóvil",
                "Botas",
                "Chaqueta de cuero",
                "Disco duro portátil",
                "Estufa eléctrica",
                "Frigobar",
                "Guantes de trabajo",
                "Horno eléctrico",
                "Impresora fotocopiadora",
                "Juego de sábanas",
                "Lámpara de escritorio",
                "Mochila de viaje",
                "Pantalón de vestir",
                "Radio-reloj despertador",
                "Smartphone",
                "Toallas de playa",
                "Utensilios de cocina",
                "Velador de madera",
                "Wurlitzer",
                "Zapatillas deportivas"
        };
        String[] categ = {
                "Automovil",
                "Cuidado personal",
                "Outdoor",
                "Línea banca",
                "Menaje",
                "Cocina"
        };
        int n_elems = elems.length;

        for (int i = 0; i < total; i++)
        {
            nombre = elems[r.nextInt(n_elems)];
            bg = Integer.toHexString(r.nextInt(0xFFFFFF));
            fg = Integer.toHexString(r.nextInt(0xFFFFFF));

            producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio( (r.nextInt(15) + 1) * 1000 );
            producto.setDescripcion( lipsum[r.nextInt(6)] );
            producto.setCategoria( categ[r.nextInt(6)] );
            producto.setImagen("http://placehold.it/320x240/"+ bg +"/"+ fg +"?text="+ nombre.replace(" ", "+"));

            productos.add(producto);
        }

        db_proxy.insertProducts(productos);

        r = null;
        lipsum = null;
        elems = null;
        db_proxy = null;
    }
}
