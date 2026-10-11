
package com.matheusoblack.consoledoctor;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mostrarMenu();
    }

    void mostrarMenu() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(15, 20, 24));

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(28, 35, 28, 35);

        scroll.addView(layout);

        TextView titulo = new TextView(this);
        titulo.setText("CONSOLE DOCTOR");
        titulo.setTextSize(28);
        titulo.setTextColor(Color.rgb(65, 220, 130));
        titulo.setGravity(17);
        layout.addView(titulo);

        TextView subtitulo = new TextView(this);
        subtitulo.setText("Diagnóstico • Reparo • Desbloqueio\nby Matheusoblack");
        subtitulo.setTextSize(14);
        subtitulo.setTextColor(Color.WHITE);
        subtitulo.setGravity(17);
        subtitulo.setPadding(0, 12, 0, 35);
        layout.addView(subtitulo);

        adicionarBotao("NÃO LIGA");
        adicionarBotao("LIGA E DESLIGA");
        adicionarBotao("LUZ VERMELHA");
        adicionarBotao("DESBLOQUEIO");
        adicionarBotao("IDENTIFICAR PLACA");
        adicionarBotao("SEM IMAGEM");
        adicionarBotao("LEITOR DE DISCO");
        adicionarBotao("DIAGNÓSTICO AVANÇADO");

        setContentView(scroll);
    }

    void adicionarBotao(String nome) {
        Button botao = new Button(this);
        botao.setText(nome);
        botao.setTextColor(Color.WHITE);
        botao.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(
                Color.rgb(30, 100, 65)
            )
        );

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                -1, 120
            );

        params.setMargins(0, 8, 0, 8);
        layout.addView(botao, params);

        botao.setOnClickListener(v -> {
            Toast.makeText(
                this,
                nome + " - Em desenvolvimento",
                Toast.LENGTH_SHORT
            ).show();
        });
    }
}
