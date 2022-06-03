package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class Opciones_memorama extends AppCompatActivity {

    Spinner spin_fonema, spin_position;
    Button jbtnPlay;
    String palabracol, fonema, position;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_memorama);

        back = findViewById(R.id.back_list_actividad);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Opciones_memorama.this, Listar_actividades_ejercitacion.class);
                startActivity(b);
            }
        });


        spin_fonema = findViewById(R.id.spinner_op_fonema_memorama);
        spin_position = findViewById(R.id.spinner_op_position_memorama);
        jbtnPlay = findViewById(R.id.btn_Jugar_memorama);

        String[] fonemas = {"/p/", "/t/", "/k/", "/b/", "/d/", "/g/", "/f/", "/s/", "/x/", "/c^/", "/m/", "/n/", "/ñ/", "/l/", "/ll/", "/-r/", "/r/"};
        ArrayAdapter<String> adapter_fonema = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fonemas);
        spin_fonema.setAdapter(adapter_fonema);

        String[] positions = {"inicial", "sílaba directa", "sílaba inversa", "intervocálica", " sílaba trabada", "final", "grupo r", "grupo l", "grupo s"};
        ArrayAdapter<String> adapter_position = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, positions);
        spin_position.setAdapter(adapter_position);

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

                position = spin_position.getSelectedItem().toString();

                Bundle bundle = new Bundle();
                bundle.putString("fonema", palabracol);
                bundle.putString("position", position);

                Intent c = new Intent(Opciones_memorama.this, Game_memorama.class);
                c.putExtras(bundle);
                startActivity(c);


            }

        });

    }
}