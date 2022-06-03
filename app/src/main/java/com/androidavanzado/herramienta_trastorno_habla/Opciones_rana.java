package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class Opciones_rana extends AppCompatActivity {

    Spinner spin_fonema;
    Button jbtnPlay;
    String palabracol, fonema;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_rana);

        back = findViewById(R.id.back_list_actividad);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Opciones_rana.this, Listar_actividades_fijacion.class);
                startActivity(b);
            }
        });

        spin_fonema = findViewById(R.id.spinner_op_fonema_rana);
        jbtnPlay = findViewById(R.id.btn_Jugar_rana);

        String [] fonemas = {"/p/","/t/","/k/","/b/","/d/","/g/","/f/","/s/","/x/","/c^/","/m/","/n/","/ñ/","/l/","/ll/","/-r/","/r/"};
        ArrayAdapter<String> adapter_fonema = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,fonemas);
        spin_fonema.setAdapter(adapter_fonema);

        jbtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fonema = spin_fonema.getSelectedItem().toString();

                if (fonema == "/p/"){
                    palabracol = "P";
                }else{
                    if (fonema == "/t/"){
                        palabracol = "T";
                    }else{
                        if (fonema == "/k/"){
                            palabracol = "K";
                        }else{
                            if (fonema == "/b/"){
                                palabracol = "B";
                            }else{
                                if (fonema == "/d/"){
                                    palabracol = "D";
                                }else{
                                    if (fonema == "/g/"){
                                        palabracol = "G";
                                    }else{
                                        if (fonema == "/f/"){
                                            palabracol = "F";
                                        }else{
                                            if (fonema == "/s/"){
                                                palabracol = "S";
                                            }else{
                                                if (fonema == "/x/"){
                                                    palabracol = "X";
                                                }else{
                                                    if (fonema == "/c^/"){
                                                        palabracol = "CH";
                                                    }else{
                                                        if (fonema == "/m/"){
                                                            palabracol = "M";
                                                        }else{
                                                            if (fonema == "/n/"){
                                                                palabracol = "N";
                                                            }else{
                                                                if (fonema == "/ñ/"){
                                                                    palabracol = "Ñ";
                                                                }else{
                                                                    if (fonema == "/l/"){
                                                                        palabracol = "L";
                                                                    }else{
                                                                        if (fonema == "/-r/"){
                                                                            palabracol = "R";
                                                                        }else{
                                                                            if (fonema == "/r/"){
                                                                                palabracol = "RR";
                                                                            }else{
                                                                                palabracol = "LL";
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putString("fonema", palabracol);

                Intent c = new Intent(Opciones_rana.this, Game_rana.class);
                c.putExtras(bundle);
                startActivity(c);

            }
        });


    }
}