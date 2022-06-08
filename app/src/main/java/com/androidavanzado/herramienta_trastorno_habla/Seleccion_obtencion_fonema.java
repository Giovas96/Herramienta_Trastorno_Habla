package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class Seleccion_obtencion_fonema extends AppCompatActivity {

    Spinner spin_fonema;
    Button jbtnPlay;
    String palabracol, fonema;
    ImageView back,edit;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_obtencion_fonema);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        a= getIntent().getIntExtra("anoni",0);

        back = findViewById(R.id.back_obten);
        edit=findViewById(R.id.add_obten);
        if(a==1){
            edit.setVisibility(View.GONE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent b = new Intent(Seleccion_obtencion_fonema.this, Listar_actividades_anonymous.class);
                    startActivity(b);
                }
            });

        }else{
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent b = new Intent(Seleccion_obtencion_fonema.this, Listar_actividades.class);
                    startActivity(b);
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent b = new Intent(Seleccion_obtencion_fonema.this, Load_obtencion.class);
                    startActivity(b);
                }
            });
        }


        spin_fonema = findViewById(R.id.spinner_op_fonema_obtencion);
        jbtnPlay = findViewById(R.id.btn_Jugar_obtencion);

        String [] fonemas = {"/p/","/t/","/k/","/b/","/d/","/g/","/f/","/s/","/x/","/c^/","/m/","/n/","/ñ/","/l/","/ll/","/-r/","/r/"};
        ArrayAdapter<String> adapter_fonema = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,fonemas);
        spin_fonema.setAdapter(adapter_fonema);

        jbtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fonema = spin_fonema.getSelectedItem().toString();

                if (fonema == "/p/") {
                    palabracol = "obtencion_p";
                } else {
                    if (fonema == "/t/") {
                        palabracol = "obtencion_t";
                    } else {
                        if (fonema == "/k/") {
                            palabracol = "obtencion_k";
                        } else {
                            if (fonema == "/b/") {
                                palabracol = "obtencion_b";
                            } else {
                                if (fonema == "/d/") {
                                    palabracol = "obtencion_d";
                                } else {
                                    if (fonema == "/g/") {
                                        palabracol = "obtencion_g";
                                    } else {
                                        if (fonema == "/f/") {
                                            palabracol = "obtencion_f";
                                        } else {
                                            if (fonema == "/s/") {
                                                palabracol = "obtencion_s";
                                            } else {
                                                if (fonema == "/x/") {
                                                    palabracol = "obtencion_x";
                                                } else {
                                                    if (fonema == "/c^/") {
                                                        palabracol = "obtencion_ch";
                                                    } else {
                                                        if (fonema == "/m/") {
                                                            palabracol = "obtencion_m";
                                                        } else {
                                                            if (fonema == "/n/") {
                                                                palabracol = "obtencion_n";
                                                            } else {
                                                                if (fonema == "/ñ/") {
                                                                    palabracol = "obtencion_ñ";
                                                                } else {
                                                                    if (fonema == "/l/") {
                                                                        palabracol = "obtencion_l";
                                                                    } else {
                                                                        if (fonema == "/-r/") {
                                                                            palabracol = "obtencion_r";
                                                                        } else {
                                                                            if (fonema == "/r/") {
                                                                                palabracol = "obtencion_rr";
                                                                            } else {
                                                                                palabracol = "obtencion_ll";
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
                bundle.putString("fon",fonema);
                Intent c = new Intent(Seleccion_obtencion_fonema.this, Obtencion_fonema.class);
                c.putExtras(bundle);
                startActivity(c);

            }
        });



    }
}