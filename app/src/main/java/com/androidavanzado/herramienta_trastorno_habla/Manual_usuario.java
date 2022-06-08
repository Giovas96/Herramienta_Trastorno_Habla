package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Manual_usuario extends AppCompatActivity {
    public final static long ONE_MEGABYTE = 1024*1024*20;

    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_usuario);

        pdfView = findViewById(R.id.pdfView);

        FirebaseStorage nFirebaseStorage = FirebaseStorage.getInstance();
        StorageReference nStorageReference = nFirebaseStorage.getReference().child("manual");

        nStorageReference.child("Manual de usuario-Herramienta_Transtornos_Habla.pdf").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                pdfView.fromBytes(bytes).load();
            }
        }).addOnFailureListener((e) -> {
            Toast.makeText(Manual_usuario.this, "Acceso exitoso", Toast.LENGTH_SHORT).show();
        });
    }

}