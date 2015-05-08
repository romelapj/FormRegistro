package com.romelapj.formregistro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.romelapj.formregistro.activity.AccesoActivity;
import com.romelapj.formregistro.activity.InsideActivity;
import com.romelapj.formregistro.activity.RegistroActivity;

import butterknife.OnClick;


public class PrincipalActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        SharedPreferences prefs =
                getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);

        String usuario = prefs.getString("usuario", "");
        if(!usuario.equals("")){
            Intent intent=new Intent(PrincipalActivity.this, InsideActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
    public void acceder(View v){
        Intent intent=new Intent(PrincipalActivity.this,AccesoActivity.class);
        startActivity(intent);
    }

    public void registrar(View v){
        Intent intent=new Intent(PrincipalActivity.this,RegistroActivity.class);
        startActivity(intent);
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
