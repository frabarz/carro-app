package cl.frabarz.carro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ProductActivity extends Activity
{
    private Long product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product_id = getIntent().getLongExtra("product_id", 0L);
        ProductsHelper db_proxy = new ProductsHelper(this);

        final Producto producto = db_proxy.getProduct(product_id);

        TextView
            nombre = (TextView) findViewById(R.id.producto_nombre),
            precio = (TextView) findViewById(R.id.producto_precio),
            descripcion = (TextView) findViewById(R.id.producto_descripcion);

        nombre.setText(producto.getNombre());
        precio.setText(producto.getPrecio());
        descripcion.setText(producto.getDescripcion());

        new LoadImage(findViewById(R.id.producto_imagen)).execute(producto.getImagen());
    }

    public void launchCartActivity(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void addProductToList(View view)
    {
        boolean insert;
        Intent intent;
        String aviso = "";
        ProductsHelper db_proxy = new ProductsHelper(this);

        int user_id = 0;
        SharedPreferences prefs = getSharedPreferences("carroapp", Context.MODE_PRIVATE);
        if (prefs != null)
            user_id = prefs.getInt("last_user_id", 0);

        if (view.getId() == R.id.producto_addcarro)
        {
            intent = new Intent(this, CartActivity.class);
            insert = db_proxy.insertCart(user_id, product_id);
            if (!insert)
                aviso = "Error al agregar este producto a tu carro.";
        }
        else
        {
            intent = new Intent(this, WishActivity.class);
            insert = db_proxy.insertWish(user_id, product_id);
            if (!insert)
                aviso = "Error al agregar este producto a tu wishlist.";
        }

        if (insert)
            startActivity(intent);
        else
            Toast.makeText(ProductActivity.this, aviso, Toast.LENGTH_SHORT).show();
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap>
    {
        ProgressDialog pDialog;
        ImageView img;

        public LoadImage(View holder)
        {
            img = (ImageView) holder;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductActivity.this);
            pDialog.setMessage("Cargando imagen...");
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args)
        {
            Bitmap bitmap = null;

            try {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inSampleSize = 1;
                FlushedInputStream in = new FlushedInputStream(new URL(args[0]).openStream());
                bitmap = BitmapFactory.decodeStream(in, null, opt);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                Toast.makeText(ProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap image)
        {
            if (image != null)
                img.setImageBitmap(image);
            else {
                img.setImageResource(R.drawable.placeholder);
                Toast.makeText(ProductActivity.this, "Error al cargar la imagen.", Toast.LENGTH_SHORT).show();
            }

            pDialog.dismiss();

            pDialog = null;
            img = null;
            image = null;
        }
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;

            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);

                if (bytesSkipped == 0L) {
                    int biyte = read();
                    if (biyte < 0)
                        break;  // we reached EOF
                    else
                        bytesSkipped = 1; // we read one byte
                }

                totalBytesSkipped += bytesSkipped;
            }

            return totalBytesSkipped;
        }
    }
}
