package cl.frabarz.carro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;

public class ProductListFragment extends ListFragment implements View.OnClickListener
{
    private int user_id = 0;
    public ProductsHelper db_proxy;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity parent_activity = getActivity();

        SharedPreferences prefs = parent_activity.getSharedPreferences("carroapp", Context.MODE_PRIVATE);
        if (prefs != null)
            user_id = prefs.getInt("last_user_id", 0);

        Cursor cursor = null;
        String label;
        db_proxy = new ProductsHelper(parent_activity);

        switch (this.getId())
        {
            case R.id.category_listview:
                label = parent_activity.getIntent().getStringExtra("titulo");
                cursor = db_proxy.getProductsCategoryCursor(label);
                break;

            case R.id.search_listview:
                cursor = db_proxy.getProductsSearchCursor("");
                break;

            case R.id.cart_listview:
                cursor = db_proxy.getCartList(user_id);
                break;

            case R.id.wish_listview:
                cursor = db_proxy.getWishList(user_id);
                break;
        }

        ProductosCursorAdapter adapter = new ProductosCursorAdapter(parent_activity, cursor)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup container)
            {
                View view = super.getView(position, convertView, container);

                View popupButton = view.findViewById(R.id.producto_ellipsis);
                popupButton.setTag(getItem(position).getId());
                popupButton.setOnClickListener(ProductListFragment.this);

                return view;
            }
        };

        if (this.getId() == R.id.search_listview)
        {
            adapter.setFilterQueryProvider(new FilterQueryProvider()
            {
                public Cursor runQuery(CharSequence constraint)
                {
                    return db_proxy.getProductsSearchCursor((String) constraint);
                }
            });
        }

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id)
    {
        Producto producto = (Producto) listView.getItemAtPosition(position);

        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("product_id", producto.getId());
        startActivity(intent);
    }

    @Override
    public void onClick(final View view)
    {
        view.post(new Runnable() {
            @Override
            public void run() {
                showPopupMenu(view);
            }
        });
    }

    private void showPopupMenu(View view)
    {
        final Long product_id = (Long) view.getTag();

        int menuref;
        switch (this.getId())
        {
            case R.id.cart_listview:
                menuref = R.menu.product_cartedit;
                break;

            case R.id.wish_listview:
                menuref = R.menu.product_wishedit;
                break;

            case R.id.category_listview:
            case R.id.search_listview:
                menuref = R.menu.product_options;
                break;

            default:
                menuref = R.menu.product_options;
                break;
        }

        PopupMenu popup = new PopupMenu(getActivity(), view);
        popup.getMenuInflater().inflate(menuref, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = null;
                boolean result = false;
                String e = "";
                Activity parent_activity = getActivity();

                switch (menuItem.getItemId()) {
                    case R.id.option_cart_add:
                        result = db_proxy.insertCart(user_id, product_id);
                        intent = new Intent(parent_activity, CartActivity.class);
                        e = "Error al agregar este producto a tu carro.";
                        break;

                    case R.id.option_wish_add:
                        result = db_proxy.insertWish(user_id, product_id);
                        intent = new Intent(parent_activity, WishActivity.class);
                        e = "Error al agregar este producto a tu wishlist.";
                        break;

                    case R.id.option_cart_remove:
                        result = db_proxy.removeCart(user_id, product_id);
                        e = "No se pudo eliminar este producto de tu carro.";
                        break;

                    case R.id.option_wish_remove:
                        result = db_proxy.removeWish(user_id, product_id);
                        e = "No se pudo eliminar este producto de tu wishlist.";
                        break;
                }

                ((ProductosCursorAdapter) getListAdapter()).notifyDataSetChanged();

                if (!result)
                    Toast.makeText(parent_activity, e, Toast.LENGTH_SHORT).show();
                else if (intent != null)
                    startActivity(intent);

                return result;
            }
        });
        popup.show();
    }
}
