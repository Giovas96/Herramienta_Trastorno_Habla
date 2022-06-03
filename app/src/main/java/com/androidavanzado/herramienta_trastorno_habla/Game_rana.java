package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class Game_rana extends AppCompatActivity {



    //game options
    String fonema;

    ImageView jiView11, jiView12, jiView13,
            jiView21, jiView22, jiView23,
            jiView31, jiView32, jiView33,
            jiView41, jiView42, jiView43,
            jiView51, jiView52, jiView53;

    Button btnPlay, btnSalir;
    Random r;

    TextView jtvTiempo, jtvFonema;

    String empty, fly, frog, lotus;

    int flyLocationRow1, flyLocationRow2, flyLocationRow3, flyLocationRow4, flyLocationRow5;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rana);

        Bundle bundle = getIntent().getExtras();
        fonema = bundle.getString("fonema");

        jiView11 = (ImageView) findViewById(R.id.iView11);
        jiView12 = (ImageView) findViewById(R.id.iView12);
        jiView13 = (ImageView) findViewById(R.id.iView13);

        jiView21 = (ImageView) findViewById(R.id.iView21);
        jiView22 = (ImageView) findViewById(R.id.iView22);
        jiView23 = (ImageView) findViewById(R.id.iView23);

        jiView31 = (ImageView) findViewById(R.id.iView31);
        jiView32 = (ImageView) findViewById(R.id.iView32);
        jiView33 = (ImageView) findViewById(R.id.iView33);

        jiView41 = (ImageView) findViewById(R.id.iView41);
        jiView42 = (ImageView) findViewById(R.id.iView42);
        jiView43 = (ImageView) findViewById(R.id.iView43);

        jiView51 = (ImageView) findViewById(R.id.iView51);
        jiView52 = (ImageView) findViewById(R.id.iView52);
        jiView53 = (ImageView) findViewById(R.id.iView53);

        btnPlay = (Button) findViewById(R.id.btn_Play_rana);
        btnSalir = (Button) findViewById(R.id.btn_Salir_rana);


        jtvTiempo = (TextView) findViewById(R.id.tv_tiempo);
        jtvTiempo.setText("Tiempo: "+ millisToTime(15000));

        jtvFonema = (TextView) findViewById(R.id.tv_fonema);
        jtvFonema.setText(fonema);

        r = new Random();

        loadImages();

        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                jtvTiempo.setText("Tiempo: "+ millisToTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                jtvTiempo.setText("Tiempo: "+ millisToTime(0));

                jiView31.setEnabled(false);
                jiView32.setEnabled(false);
                jiView33.setEnabled(false);

                btnPlay.setVisibility(View.VISIBLE);

                Picasso.get().load(empty).into(jiView11);
                Picasso.get().load(empty).into(jiView12);
                Picasso.get().load(empty).into(jiView13);

                Picasso.get().load(empty).into(jiView21);
                Picasso.get().load(empty).into(jiView22);
                Picasso.get().load(empty).into(jiView23);

                Picasso.get().load(empty).into(jiView31);
                Picasso.get().load(empty).into(jiView32);
                Picasso.get().load(empty).into(jiView33);

                Picasso.get().load(empty).into(jiView41);
                Picasso.get().load(empty).into(jiView42);
                Picasso.get().load(empty).into(jiView43);

                Picasso.get().load(empty).into(jiView51);
                Picasso.get().load(empty).into(jiView52);
                Picasso.get().load(empty).into(jiView53);

                Toast.makeText(Game_rana.this, "Terminaste!", Toast.LENGTH_SHORT).show();
            }
        };

        jiView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flyLocationRow3 == 1){
                    continueGame();
                }else{
                    endGame();
                }
            }
        });

        jiView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flyLocationRow3 == 2){
                    continueGame();
                }else{
                    endGame();
                }
            }
        });

        jiView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flyLocationRow3 == 3){
                    continueGame();
                }else{
                    endGame();
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGame();
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Game_rana.this, Listar_actividades_fijacion.class);
                startActivity(b);
            }
        });

    }

    private void initGame(){
        jiView31.setEnabled(true);
        jiView32.setEnabled(true);
        jiView33.setEnabled(true);

        btnPlay.setVisibility(View.INVISIBLE);

        timer.start();

        //row5 - nada
        //row4
        flyLocationRow4 = 2;
        Picasso.get().load(frog).into(jiView42);

        //row3
        flyLocationRow3 = 2;
        Picasso.get().load(fly).into(jiView32);

        //row2
        flyLocationRow2 = r.nextInt(3) + 1;
        setFlyLocation(flyLocationRow2, 2);

        //row1
        flyLocationRow1 = r.nextInt(3) + 1;
        setFlyLocation(flyLocationRow1, 1);

    }

    private void continueGame(){
        //Row5
        flyLocationRow5 = flyLocationRow4;
        setFlyLocation(flyLocationRow5, 5);

        //Row4
        flyLocationRow4 = flyLocationRow3;
        setFlyLocation(flyLocationRow4, 4);

        //Row3
        flyLocationRow3 = flyLocationRow2;
        setFlyLocation(flyLocationRow3, 3);

        //Row2
        flyLocationRow2 = flyLocationRow1;
        setFlyLocation(flyLocationRow2, 2);

        //Row1
        flyLocationRow1 = r.nextInt(3) + 1;
        setFlyLocation(flyLocationRow1, 1);
    }

    private void endGame(){
        timer.cancel();

        jiView31.setEnabled(false);
        jiView32.setEnabled(false);
        jiView33.setEnabled(false);

        btnPlay.setVisibility(View.VISIBLE);

        Picasso.get().load(empty).into(jiView11);
        Picasso.get().load(empty).into(jiView12);
        Picasso.get().load(empty).into(jiView13);

        Picasso.get().load(empty).into(jiView21);
        Picasso.get().load(empty).into(jiView22);
        Picasso.get().load(empty).into(jiView23);

        Picasso.get().load(empty).into(jiView31);
        Picasso.get().load(empty).into(jiView32);
        Picasso.get().load(empty).into(jiView33);

        Picasso.get().load(empty).into(jiView41);
        Picasso.get().load(empty).into(jiView42);
        Picasso.get().load(empty).into(jiView43);

        Picasso.get().load(empty).into(jiView51);
        Picasso.get().load(empty).into(jiView52);
        Picasso.get().load(empty).into(jiView53);

        Toast.makeText(Game_rana.this, "Fallaste!", Toast.LENGTH_SHORT).show();
    }

    private void setFlyLocation(int place, int row){
        if(row == 1){

            Picasso.get().load(empty).into(jiView11);
            Picasso.get().load(empty).into(jiView12);
            Picasso.get().load(empty).into(jiView13);


            switch (place){
                case 1:
                    Picasso.get().load(lotus).into(jiView11);
                    break;
                case 2:
                    Picasso.get().load(lotus).into(jiView12);
                    break;
                case 3:
                    Picasso.get().load(lotus).into(jiView13);
                    break;
            }
        }

        if(row == 2){

            Picasso.get().load(empty).into(jiView21);
            Picasso.get().load(empty).into(jiView22);
            Picasso.get().load(empty).into(jiView23);

            switch (place){
                case 1:
                    Picasso.get().load(lotus).into(jiView21);
                    break;
                case 2:
                    Picasso.get().load(lotus).into(jiView22);
                    break;
                case 3:
                    Picasso.get().load(lotus).into(jiView23);
                    break;
            }
        }

        if(row == 3){
            Picasso.get().load(empty).into(jiView31);
            Picasso.get().load(empty).into(jiView32);
            Picasso.get().load(empty).into(jiView33);

            switch (place){
                case 1:
                    Picasso.get().load(fly).into(jiView31);
                    break;
                case 2:
                    Picasso.get().load(fly).into(jiView32);
                    break;
                case 3:
                    Picasso.get().load(fly).into(jiView33);
                    break;
            }
        }

        if(row == 4){
            Picasso.get().load(empty).into(jiView41);
            Picasso.get().load(empty).into(jiView42);
            Picasso.get().load(empty).into(jiView43);

            switch (place){
                case 1:
                    Picasso.get().load(frog).into(jiView41);
                    break;
                case 2:
                    Picasso.get().load(frog).into(jiView42);
                    break;
                case 3:
                    Picasso.get().load(frog).into(jiView43);
                    break;
            }
        }

        if(row == 5){
            Picasso.get().load(empty).into(jiView51);
            Picasso.get().load(empty).into(jiView52);
            Picasso.get().load(empty).into(jiView53);

            switch (place){
                case 1:
                    Picasso.get().load(lotus).into(jiView51);
                    break;
                case 2:
                    Picasso.get().load(lotus).into(jiView52);
                    break;
                case 3:
                    Picasso.get().load(lotus).into(jiView53);
                    break;
            }
        }
    }

    private void loadImages(){


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("rana_res")
                .whereEqualTo("name", "empty")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot snapshot : task.getResult()){
                    empty = snapshot.getString("imageUrl");
                    System.out.println("url de empty: " + empty);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toastfaildata = Toast.makeText(getApplicationContext(), "Falló en cargar imagenes!!", Toast.LENGTH_LONG);
                toastfaildata.show();
            }
        });

        db.collection("rana_res")
                .whereEqualTo("name", "fly")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot snapshot : task.getResult()){
                    fly = snapshot.getString("imageUrl");
                    System.out.println("url de fly: " + fly);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toastfaildata = Toast.makeText(getApplicationContext(), "Falló en cargar imagenes!!", Toast.LENGTH_LONG);
                toastfaildata.show();
            }
        });

        db.collection("rana_res")
                .whereEqualTo("name", "frog")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot snapshot : task.getResult()){
                    frog = snapshot.getString("imageUrl");
                    System.out.println("url de frog: " + frog);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toastfaildata = Toast.makeText(getApplicationContext(), "Falló en cargar imagenes!!", Toast.LENGTH_LONG);
                toastfaildata.show();
            }
        });

        db.collection("rana_res")
                .whereEqualTo("name", "lotus")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot snapshot : task.getResult()){
                    lotus = snapshot.getString("imageUrl");
                    System.out.println("url de lotus: " + lotus);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toastfaildata = Toast.makeText(getApplicationContext(), "Falló en cargar imagenes!!", Toast.LENGTH_LONG);
                toastfaildata.show();
            }
        });

    }

    private int millisToTime(long millis){
        return (int) millis / 1000;
    }

}