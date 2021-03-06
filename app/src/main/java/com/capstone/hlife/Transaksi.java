package com.capstone.hlife;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

public class Transaksi extends AppCompatActivity {
    //Views
    private Button btnTambah;
    private RecyclerView recyclerView;

    //Db Helper
    private DataHelper dbHelper;

    //actionBar
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Data Makananku");

        //Tombol Kembali
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init views
        btnTambah = findViewById(R.id.btnTambah);
        recyclerView = findViewById(R.id.recyclerView);

        //init db helper class
        dbHelper = new DataHelper(this);

        //Memuat record
        loadRecords();
    }
    private void searchRecords(String query){
        Adapter2 adapter = new Adapter2(Transaksi.this,
                dbHelper.searchRecords(query));

        recyclerView.setAdapter(adapter);
    }

    private void loadRecords() {
        Adapter2 adapter = new Adapter2(Transaksi.this,
                dbHelper.getAllRecords(Constants.K_ADDED_TIMESTAMP + " ASC "));

        recyclerView.setAdapter(adapter);

        //set angka dari record
        actionBar.setSubtitle("Barang : "+dbHelper.getRecordsCount());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //searchView
        MenuItem item = menu.findItem(R.id.action_cari);
        final SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //cari saat kita menekan tombol cari di keyboard
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //cari saat kita mengetik
                searchRecords(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //Back To Activity
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
