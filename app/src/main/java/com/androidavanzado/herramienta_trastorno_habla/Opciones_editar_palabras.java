package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class Opciones_editar_palabras extends AppCompatActivity {
    Spinner spin_fonema;
    Button jbtnPlay;
    String palabracol, fonema;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_editar_palabras);



            back = findViewById(R.id.back_pal);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent b = new Intent(Opciones_editar_palabras.this, Listar_actividades.class);
                    startActivity(b);
                }
            });

            spin_fonema = findViewById(R.id.spinner_op_fonema);
            jbtnPlay = findViewById(R.id.btn_Jugar_rana);

            String [] fonemas = {"/p/","/t/","/k/","/b/","/d/","/g/","/f/","/s/","/x/","/c^/","/m/","/n/","/ñ/","/l/","/ll/","/-r/","/r/"};
            ArrayAdapter<String> adapter_fonema = new ArrayAdapter<String>(Opciones_editar_palabras.this, android.R.layout.simple_spinner_item,fonemas);
            spin_fonema.setAdapter(adapter_fonema);

            jbtnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fonema = spin_fonema.getSelectedItem().toString();

                    if (fonema == "/p/") {
                        palabracol = "palabra_p";
                    } else {
                        if (fonema == "/t/") {
                            palabracol = "palabra_t";
                        } else {
                            if (fonema == "/k/") {
                                palabracol = "palabra_k";
                            } else {
                                if (fonema == "/b/") {
                                    palabracol = "palabra_b";
                                } else {
                                    if (fonema == "/d/") {
                                        palabracol = "palabra_d";
                                    } else {
                                        if (fonema == "/g/") {
                                            palabracol = "palabra_g";
                                        } else {
                                            if (fonema == "/f/") {
                                                palabracol = "palabra_f";
                                            } else {
                                                if (fonema == "/s/") {
                                                    palabracol = "palabra_s";
                                                } else {
                                                    if (fonema == "/x/") {
                                                        palabracol = "palabra_x";
                                                    } else {
                                                        if (fonema == "/c^/") {
                                                            palabracol = "palabra_ch";
                                                        } else {
                                                            if (fonema == "/m/") {
                                                                palabracol = "palabra_m";
                                                            } else {
                                                                if (fonema == "/n/") {
                                                                    palabracol = "palabra_n";
                                                                } else {
                                                                    if (fonema == "/ñ/") {
                                                                        palabracol = "palabra_ñ";
                                                                    } else {
                                                                        if (fonema == "/l/") {
                                                                            palabracol = "palabra_l";
                                                                        } else {
                                                                            if (fonema == "/-r/") {
                                                                                palabracol = "palabra_r";
                                                                            } else {
                                                                                if (fonema == "/r/") {
                                                                                    palabracol = "palabra_rr";
                                                                                } else {
                                                                                    palabracol = "palabra_ll";
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

                    Intent c = new Intent(Opciones_editar_palabras.this, Listar_palabras.class);
                    c.putExtras(bundle);
                    startActivity(c);

                }
            });


        }
    }