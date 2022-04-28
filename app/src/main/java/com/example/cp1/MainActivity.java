package com.example.cp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.cp1.Entity.Sala;
import com.example.cp1.service.SalaService;
import com.example.cp1.util.ConnectionRest;
import com.example.cp1.util.FunctionUtil;
import com.example.cp1.util.ValidacionUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    EditText txtIDSala, txtNumero, txtCapacidad, txtRecursos, txtfechaseparacion, txtfechaderegistro;
    Spinner spnPiso;
    Button btnregistrar;

    SalaService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIDSala = findViewById(R.id.txtIDSala);
        txtNumero = findViewById(R.id.txtNumero);
        txtCapacidad = findViewById(R.id.txtCapacidad);
        txtRecursos = findViewById(R.id.txtRecursos);
        txtfechaseparacion = findViewById(R.id.txtfechaseparacion);
        txtfechaderegistro = findViewById(R.id.txtfechaderegistro);
        spnPiso = findViewById(R.id.spnPiso);
        btnregistrar = findViewById(R.id.btnregistrar);


        service = ConnectionRest.getConnecion().create(SalaService.class);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ID = Integer.parseInt(txtIDSala.getText().toString());
                String Num = txtNumero.getText().toString();
                int Cap = Integer.parseInt(txtCapacidad.getText().toString());
                String Rec = txtRecursos.getText().toString();
                String Sep = txtfechaseparacion.getText().toString();
                String Reg = txtfechaderegistro.getText().toString();
                int Piso = Integer.parseInt(spnPiso.getSelectedItem().toString());



                   if (!Num.matches(ValidacionUtil.ID)) {
                    mensajeAlert("Numero es de 1 a 30");

                } else if (!Rec.matches(ValidacionUtil.TEXTO)) {
                    mensajeAlert("Recursos es de 1 a 30");

                } else if (!Sep.matches(ValidacionUtil.FECHA)) {
                    mensajeAlert("La fecha es yyyy-MM-dd");

                } else if (!Reg.matches(ValidacionUtil.FECHA)) {
                    mensajeAlert("La fecha es yyyy-MM-dd");

                } else {

                    Sala obj = new Sala();
                    obj.setIdSala(ID);
                    obj.setCapacidad(Cap);
                    obj.setRecursos(Rec);
                    obj.setFechaSeparacion(Sep);
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setPiso(Piso);
                    registra(obj);


                }


            }


        });

    }

    private void registra(Sala obj) {
        Call<Sala> call = service.insertaSala(obj);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if (response.isSuccessful()) {
                    Sala obj = response.body();
                    mensajeAlert("Editorial  : " + obj.getIdSala() + "== > " + obj.getNumero());
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                mensajeAlert("Error" + t.getMessage());
            }
        });
    }

    public void mensajeAlert(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}

