package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game_memorama extends AppCompatActivity {

    //view
    ImageButton imb00;
    ImageButton imb01;
    ImageButton imb02;
    ImageButton imb03;
    ImageButton imb04;
    ImageButton imb05;
    ImageButton imb06;
    ImageButton imb07;
    ImageButton imb08;
    ImageButton imb09;
    ImageButton imb10;
    ImageButton imb11;
    ImageButton imb12;
    ImageButton imb13;
    ImageButton imb14;
    ImageButton imb15;
    ImageButton[] board = new ImageButton[16];
    Button game_reset;
    Button game_exit;
    int hits = 0;

    //images
    List<String> simpleArray;
    String[] images;
    int back;

    //gameplay
    ArrayList<Integer> untidyArray;
    ImageButton first;
    int firstNumber;
    int secondNumber;
    boolean lockFlag = false;
    final Handler handler = new Handler();

    //game options
    String fonema;
    String position;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_memorama);

        game_reset = findViewById(R.id.buttonGameReset);
        game_exit = findViewById(R.id.buttonGameExit);

        Bundle bundle = getIntent().getExtras();
        fonema = bundle.getString("fonema");
        position = bundle.getString("position");

        init();

        game_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

        game_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadBoard(){
        imb00 = findViewById(R.id.button00);
        imb01 = findViewById(R.id.button01);
        imb02 = findViewById(R.id.button02);
        imb03 = findViewById(R.id.button03);
        imb04 = findViewById(R.id.button04);
        imb05 = findViewById(R.id.button05);
        imb06 = findViewById(R.id.button06);
        imb07 = findViewById(R.id.button07);
        imb08 = findViewById(R.id.button08);
        imb09 = findViewById(R.id.button09);
        imb10 = findViewById(R.id.button10);
        imb11 = findViewById(R.id.button11);
        imb12 = findViewById(R.id.button12);
        imb13 = findViewById(R.id.button13);
        imb14 = findViewById(R.id.button14);
        imb15 = findViewById(R.id.button15);

        board[0] = imb00;
        board[1] = imb01;
        board[2] = imb02;
        board[3] = imb03;
        board[4] = imb04;
        board[5] = imb05;
        board[6] = imb06;
        board[7] = imb07;
        board[8] = imb08;
        board[9] = imb09;
        board[10] = imb10;
        board[11] = imb11;
        board[12] = imb12;
        board[13] = imb13;
        board[14] = imb14;
        board[15] = imb15;

    }

    private void loadImages(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        simpleArray = new ArrayList<String>();

        simpleArray.clear();

        db.collection(fonema)
                .whereEqualTo("position", position)
                .limit(8).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot snapshot : task.getResult()){

                    simpleArray.add(snapshot.getString("imageUrl"));

                }

                images = new String[ simpleArray.size() ];
                simpleArray.toArray(images);

                System.out.println(Arrays.toString(images));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toastfaildata = Toast.makeText(getApplicationContext(), "Falló en cargar imagenes!!", Toast.LENGTH_LONG);
                toastfaildata.show();
            }
        });

        back = R.drawable.fondo;

    }

    private ArrayList<Integer> shuffleArray(int longitud){
        ArrayList<Integer> result = new ArrayList<Integer>();

        for(int i = 0; i<longitud*2; i++){
            result.add(i % longitud);
        }

        Collections.shuffle(result);
        return result;
    }

    private void checkMatch(int i, final ImageButton imgb){
        if(first == null){
            first = imgb;
            first.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Picasso.get().load(images[untidyArray.get(i)]).into(first);

            //first.setImageResource(simpleArray[untidyArray.get(i)]);

            first.setEnabled(false);
            firstNumber = untidyArray.get(i);
        } else{
            lockFlag = true;
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Picasso.get().load(images[untidyArray.get(i)]).into(imgb);

            //imgb.setImageResource(simpleArray[untidyArray.get(i)]);

            imgb.setEnabled(false);
            secondNumber = untidyArray.get(i);

            if(firstNumber == secondNumber){
                first = null;
                lockFlag = false;
                hits++;

                if(hits == images.length){
                    Toast toast = Toast.makeText(getApplicationContext(), "Ganaste!!", Toast.LENGTH_LONG);
                    toast.show();
                }

            } else{
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        first.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        first.setImageResource(back);
                        first.setEnabled(true);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(back);
                        imgb.setEnabled(true);
                        lockFlag = false;
                        first = null;
                    }
                }, 750);
            }
        }

    }

    private void init(){
        loadBoard();
        loadImages();
        untidyArray = shuffleArray(8);

        System.out.println(Arrays.toString(untidyArray.toArray()));

        for(int i=0; i<board.length; i++){
            board[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            //board[i].setImageResource(images[untidyArray.get(i)]);
            board[i].setImageResource(back);
        }

        for(int i=0; i<board.length; i++){
            final int j = i;
            board[i].setEnabled(true);
            board[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!lockFlag){
                        checkMatch(j, board[j]);
                    }
                }
            });
        }

    }

}