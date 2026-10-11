
package com.matheusoblack.consoledoctor;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.View;
import android.widget.*;
import android.text.TextWatcher;
import android.text.Editable;

public class MainActivity extends Activity {

    LinearLayout layout;
    int fundo = Color.rgb(15, 20, 24);
    int verde = Color.rgb(30, 100, 65);
    int branco = Color.WHITE;

    String[][] erros = {
        {"0001", "Falha de alimentação", "Investigar alimentação principal e possíveis curtos."},
        {"0002", "Falha de alimentação", "Verificar reguladores e circuitos de alimentação."},
        {"0003", "Falha elétrica", "Investigar os circuitos de alimentação conforme a revisão da placa."},
        {"0010", "Falha de hardware", "Identificar a revisão da placa antes de realizar testes."},
        {"0011", "Possível superaquecimento", "Verificar refrigeração e sensores."},
        {"0012", "Possível superaquecimento", "Verificar dissipação térmica e ventilação."},
        {"0013", "Possível superaquecimento", "Verificar refrigeração e condições térmicas."},
        {"0020", "Falha de hardware", "Investigar inicialização e componentes relacionados."},
        {"0021", "Falha de hardware", "Verificar inicialização e armazenamento conforme o modelo."},
        {"0022", "Falha de inicialização", "Investigar hardware e firmware."},
        {"0033", "Falha a investigar", "Confirmar o significado conforme o modelo da placa."},
        {"0102", "Falha de hardware", "Investigar memória e processadores conforme o modelo."},
        {"0110", "Falha relacionada à memória", "Verificar circuitos e conexões de memória."},
        {"1022", "Possível falha de vídeo", "Investigar circuito de saída de vídeo conforme o modelo."}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(fundo);
        getWindow().setNavigationBarColor(Color.BLACK);
        mostrarMenu();
    }

    void iniciarTela(String titulo) {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(fundo);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(24, 35, 24, 30);

        scroll.addView(layout);
        setContentView(scroll);

        TextView cabecalho = texto(titulo, 25, 0xFF43D58B);
        cabecalho.setGravity(17);
        cabecalho.setPadding(0, 10, 0, 25);
        layout.addView(cabecalho);
    }

    TextView texto(String valor, int tamanho, int cor) {
        TextView t = new TextView(this);
        t.setText(valor);
        t.setTextSize(tamanho);
        t.setTextColor(cor);
        t.setPadding(12, 12, 12, 12);
        return t;
    }

    void botao(String nome, Runnable acao) {
        Button b = new Button(this);
        b.setText(nome);
        b.setTextColor(branco);
        b.setBackgroundTintList(
            ColorStateList.valueOf(verde)
        );

        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(-1, 110);
        p.setMargins(0, 7, 0, 7);

        layout.addView(b, p);
        b.setOnClickListener(v -> acao.run());
    }

    void mostrarMenu() {
        iniciarTela("CONSOLE DOCTOR");

        TextView subtitulo = texto(
            "Diagnóstico • Reparo • Desbloqueio\nby Matheusoblack",
            15, branco
        );
        subtitulo.setGravity(17);
        subtitulo.setPadding(0, 0, 0, 25);
        layout.addView(subtitulo);

        botao("NÃO LIGA", () -> emDesenvolvimento());
        botao("LIGA E DESLIGA", () -> emDesenvolvimento());
        botao("LUZ VERMELHA", () -> mostrarErros());
        botao("DESBLOQUEIO", () -> emDesenvolvimento());
        botao("IDENTIFICAR PLACA", () -> emDesenvolvimento());
        botao("SEM IMAGEM", () -> emDesenvolvimento());
        botao("LEITOR DE DISCO", () -> emDesenvolvimento());
        botao("DIAGNÓSTICO AVANÇADO", () -> emDesenvolvimento());
    }

    void emDesenvolvimento() {
        Toast.makeText(
            this,
            "Função em desenvolvimento",
            Toast.LENGTH_SHORT
        ).show();
    }

    void mostrarErros() {
        iniciarTela("LUZ VERMELHA");

        botao("VOLTAR AO MENU", () -> mostrarMenu());

        EditText busca = new EditText(this);
        busca.setHint("Pesquisar código: 0003...");
        busca.setSingleLine(true);
        busca.setTextColor(branco);
        busca.setHintTextColor(Color.LTGRAY);
        busca.setInputType(2);
        layout.addView(busca);

        LinearLayout lista = new LinearLayout(this);
        lista.setOrientation(LinearLayout.VERTICAL);
        layout.addView(lista);

        preencherLista(lista, "");

        busca.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(
                CharSequence s, int start, int count, int after
            ) {}

            public void onTextChanged(
                CharSequence s, int start, int before, int count
            ) {
                preencherLista(lista, s.toString());
            }

            public void afterTextChanged(Editable e) {}
        });
    }

    void preencherLista(LinearLayout lista, String filtro) {
        lista.removeAllViews();

        for (String[] erro : erros) {
            if (!erro[0].contains(filtro.trim())) continue;

            Button b = new Button(this);
            b.setText(erro[0] + " - " + erro[1]);
            b.setTextColor(branco);
            b.setAllCaps(false);
            b.setBackgroundTintList(
                ColorStateList.valueOf(0xFF253B32)
            );

            LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(-1, 105);
            p.setMargins(0, 5, 0, 5);

            lista.addView(b, p);
            b.setOnClickListener(v -> mostrarDetalhes(erro));
        }
    }

    void mostrarDetalhes(String[] erro) {
        iniciarTela("ERRO " + erro[0]);

        layout.addView(texto(
            erro[1], 22, branco
        ));

        layout.addView(texto(
            "POSSÍVEIS CAUSAS\n\n" + erro[2],
            17, branco
        ));

        layout.addView(texto(
            "ATENÇÃO\n\n"
            + "O código não identifica sozinho um componente "
            + "defeituoso. Confirme o modelo da placa e "
            + "realize medições antes de substituir peças.",
            15, Color.LTGRAY
        ));

        layout.addView(texto(
            "Mapas de componentes, medições e vídeos "
            + "serão adicionados nas próximas versões.",
            15, 0xFF43D58B
        ));

        botao("VOLTAR AOS CÓDIGOS", () -> mostrarErros());
        botao("MENU PRINCIPAL", () -> mostrarMenu());
    }

    @Override
    public void onBackPressed() {
        mostrarMenu();
    }
}
