package com.romelapj.formregistro.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.romelapj.formregistro.PrincipalActivity;
import com.romelapj.formregistro.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class InsideActivity extends ActionBarActivity {
    @InjectView(R.id.tvSaludo)
    TextView tvSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);
        ButterKnife.inject(this);
        SharedPreferences prefs =
                getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);

        String usuario = prefs.getString("usuario", "");
        String password = prefs.getString("password", "");
        tvSaludo.setText(tvSaludo.getText()+"\n"+usuario);
    }
    public void logout(View v){
        SharedPreferences prefs =
                getSharedPreferences("SesionUsuario",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", "");
        editor.putString("password", "");
        editor.commit();
        Intent intent=new Intent(InsideActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inside, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
