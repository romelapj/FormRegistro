package com.romelapj.formregistro.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.romelapj.formregistro.R;
import com.romelapj.formregistro.activity.utils.ProgressGenerator;
import com.romelapj.formregistro.bd.ActividadDosBD;
import com.romelapj.formregistro.model.Usuario;

import java.sql.SQLDataException;
import java.sql.SQLException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegistroActivity extends ActionBarActivity implements ProgressGenerator.OnCompleteListener {

    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    ActividadDosBD actdbh =
            new ActividadDosBD(this);
    private SQLiteDatabase db ;

    ProgressGenerator progressGenerator;


    @InjectView(R.id.etUsuario)
    EditText etUsuario;
    @InjectView(R.id.etPassword)
    EditText etPassword;
    @InjectView(R.id.tvErrorRegistro)
    TextView tvErrorRegistro;

    @InjectView(R.id.btnSignIn)
    ActionProcessButton btnSignIn;

    public Usuario user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        progressGenerator = new ProgressGenerator(this);
        ButterKnife.inject(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE)) {
            btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvErrorRegistro.setVisibility(View.GONE);
                String usuario=etUsuario.getText().toString();
                String password=etPassword.getText().toString();
                user=new Usuario(usuario,password);
                progressGenerator.start(btnSignIn);
                btnSignIn.setEnabled(false);
                etUsuario.setEnabled(false);
                etPassword.setEnabled(false);
                registrarUsuario(user.getUsuario(), user.getPassword());

            }
        });
    }

    public void registrarUsuario(String usuario, String password) {
        try {
            db = actdbh.getWritableDatabase();
            if (db != null) {
                ContentValues valores = new ContentValues();
                valores.put("usuario", usuario);
                valores.put("password", password);
                db.insertOrThrow("usuarios", null, valores);
                db.close();
            }
        }catch (SQLiteConstraintException ex){
            Log.e("prueba","error");
            progressGenerator.error();
        }
    }


    @Override
    public void onComplete() {
        SharedPreferences prefs =
                getSharedPreferences("SesionUsuario",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", user.getUsuario());
        editor.putString("password", user.getPassword());
        editor.commit();

        Intent intent=new Intent(RegistroActivity.this, InsideActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onError() {
        tvErrorRegistro.setVisibility(View.VISIBLE);
        tvErrorRegistro.setText("El usuario "+user.getUsuario()+" ya se encuentra registrado");
        btnSignIn.setEnabled(true);
        etUsuario.setEnabled(true);
        etPassword.setEnabled(true);
        btnSignIn.setError("");
    }
}
