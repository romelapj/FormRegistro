package com.romelapj.formregistro.activity;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.romelapj.formregistro.R;
import com.romelapj.formregistro.activity.utils.ProgressGenerator;
import com.romelapj.formregistro.bd.ActividadDosBD;
import com.romelapj.formregistro.model.Usuario;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AccesoActivity extends ActionBarActivity implements ProgressGenerator.OnCompleteListener {
    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    ActividadDosBD actdbh =
            new ActividadDosBD(this);
    private SQLiteDatabase db ;
    public Usuario user;

    ProgressGenerator progressGenerator;


    @InjectView(R.id.etUsuarioAccess)
    EditText etUsuario;
    @InjectView(R.id.etPasswordAccess)
    EditText etPassword;
    @InjectView(R.id.tvErrorAccess)
    TextView tvErrorAccess;

    @InjectView(R.id.btnAcess)
    ActionProcessButton btnAcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);

        progressGenerator = new ProgressGenerator(this);
        ButterKnife.inject(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE)) {
            btnAcess.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
            btnAcess.setMode(ActionProcessButton.Mode.PROGRESS);
        }
        btnAcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvErrorAccess.setVisibility(View.GONE);
                String usuario=etUsuario.getText().toString();
                String password=etPassword.getText().toString();
                user=new Usuario(usuario,password);
                progressGenerator.start(btnAcess);
                btnAcess.setEnabled(false);
                etUsuario.setEnabled(false);
                etPassword.setEnabled(false);
                if(!buscarUsuario(usuario, password)){
                    progressGenerator.error();
                }

            }
        });
    }

    public boolean buscarUsuario(String usuario, String password) {
            db = actdbh.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM usuarios Where  usuario= '"+user.getUsuario()+"' AND password = '"+user.getPassword()+"';", null);
            if( cursor.getCount() == 1){
                return true;
            }
            db.close();
            return false;


    }


    @Override
    public void onComplete() {
        SharedPreferences prefs =
                getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", user.getUsuario());
        editor.putString("password", user.getPassword());
        editor.commit();
        Intent intent=new Intent(AccesoActivity.this, InsideActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError() {
        tvErrorAccess.setVisibility(View.VISIBLE);
        tvErrorAccess.setText("El usuario o contraseña no se encuentra bien");
        btnAcess.setEnabled(true);
        etUsuario.setEnabled(true);
        etPassword.setEnabled(true);
        btnAcess.setError("");
    }
}
