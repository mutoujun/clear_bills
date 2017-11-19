package com.android.account.clear_bills;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.account.clear_bills.Fragment.Resigete_Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setupNavButton();
    }

    private void init(){
        Toolbar toolbar= (Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupNavButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEnrollAccount(View view){
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.contrain,new Resigete_Fragment())
                .commit();

    }
}
