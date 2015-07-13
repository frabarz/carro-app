package cl.frabarz.carro;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

public class SearchActivity extends FragmentActivity
{
    public EditText input;
    private TextWatcher filterTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final ProductListFragment fragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.search_listview);
        fragment.getListView().setTextFilterEnabled(true);

        filterTextWatcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ProductosCursorAdapter adapter = (ProductosCursorAdapter) fragment.getListAdapter();
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        input = (EditText) findViewById(R.id.search_input);
        input.addTextChangedListener(filterTextWatcher);
        if (input.requestFocus())
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        input.removeTextChangedListener(filterTextWatcher);
    }

    public void launchCartActivity(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}