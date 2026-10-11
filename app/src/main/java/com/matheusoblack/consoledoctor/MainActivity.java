
package com.matheusoblack.consoledoctor;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    private LinearLayout body;
    private final int bg = 0xff0C1218;
    private final int green = 0xff4AF0A1;
    private final int white = Color.WHITE;

    private String model = "Slim / Super Slim";
    private String page = "home";

    private static final String SOURCE =
        "https://consolemods.org/wiki/Xbox_360:Error_Codes";

    // Código, categoria, descrição, causas, testes, soluções
    private final String[][] entries = {

        {"0001","Alimentação",
        "Sinal de alimentação de 12 V fora da condição esperada.",
        "Fonte, entrada de 12 V, proteção, capacitores e componentes da linha.",
        "Conferir fonte e conectores; inspecionar USB; verificar curto na entrada com tudo desligado; medir a linha somente com referência correta.",
        "Corrigir curto ou conexão; substituir fonte comprovadamente defeituosa; reparar componente identificado por medições."},

        {"0002","Vcore CPU",
        "Falha no sinal power-good da alimentação do processador.",
        "Regulador V_CPUCORE, MOSFETs, capacitores, controle e CPU.",
        "Checar resistência da linha com console desligado; verificar sequência de alimentação e power-good segundo esquema da placa.",
        "Reparar regulador, solda ou componente comprovadamente defeituoso; não presumir CPU ruim."},

        {"0003","Alimentação por modelo",
        "Fat: power-good da alimentação GPU. Slim/E: power-good da linha 3,3 V.",
        "Fat: V_GPUCORE. Slim/E: V_3P3, regulador, capacitores, MOSFETs, enable e power-good.",
        "Identificar placa; testar fonte; com energia desconectada verificar curto da linha correta; inspecionar capacitores removidos; medir 3,3 V e power-good em pontos confirmados no esquema.",
        "Substituir regulador ou componente somente após isolar a falha; restaurar filtros removidos com peças equivalentes verificadas."},

        {"0010","ANA / HANA",
        "Controlador de vídeo ANA/HANA não responde à comunicação.",
        "Clock, barramento SMBus, ANA/HANA, southbridge.",
        "Inspecionar oxidação, alimentação e clock; verificar barramento conforme esquema.",
        "Corrigir alimentação, trilha ou componente confirmado; substituição de CI exige equipamento adequado."},

        {"0011","Temperatura CPU",
        "Proteção térmica do processador.",
        "Ventoinha, dissipador, pasta térmica, sensores e circuito de leitura.",
        "Checar ventoinha, dissipador, montagem e sensores; em Corona, verificar resistores do circuito térmico.",
        "Limpeza e reparo da refrigeração; reparar sensor ou resistores comprovadamente alterados."},

        {"0012","Temperatura GPU",
        "Proteção térmica relacionada à GPU.",
        "Dissipador, pasta térmica, ventilação, sensores.",
        "Verificar ventilação, contato do dissipador e leitura térmica.",
        "Corrigir refrigeração ou sensor identificado."},

        {"0013","Temperatura eDRAM",
        "Proteção térmica relacionada à eDRAM.",
        "Refrigeração, sensores e circuito de monitoramento.",
        "Verificar montagem térmica e circuito de sensores conforme placa.",
        "Reparar causa térmica confirmada."},

        {"0020","Reset GPU",
        "Sinal GPU_RST_DONE ausente na sequência de inicialização.",
        "Clock GPU, reset, trilhas e GPU.",
        "Verificar alimentação e sinais de clock/reset conforme esquema e equipamento apropriado.",
        "Reparar trilha, clock ou circuito confirmado; GPU apenas após diagnóstico."},

        {"0021","PCIe",
        "Link PCIe não entra no estado esperado.",
        "GPU, southbridge, clock PCIe e trilhas.",
        "Inspecionar danos e conferir clock, alimentação e link conforme esquema.",
        "Reparar falha confirmada; não trocar chips por tentativa."},

        {"0022","Inicialização CPU",
        "CPU não concluiu a comunicação inicial esperada com o SMC.",
        "CPU, clock, reset, PLL, POST e modificações RGH.",
        "Se modificado, inspecionar pontos RGH; verificar POST, alimentação, reset e clock.",
        "Corrigir soldas, trilhas ou firmware somente com backup e diagnóstico."},

        {"0023","Clock / eDRAM",
        "Fat: falha de comunicação com clockgen. Slim/E: falha de alimentação CPU eDRAM.",
        "Fat: clockgen/ANA/HANA. Slim/E: V_CPUEDRAM e regulador.",
        "Identificar revisão antes de medir; conferir linha ou comunicação correspondente.",
        "Reparar circuito comprovadamente defeituoso."},

        {"0030","Sensores térmicos",
        "Leituras de temperatura inválidas ou zeradas.",
        "ANA/HANA/KSB e comunicação com sensores.",
        "Verificar alimentação e comunicação do circuito térmico.",
        "Corrigir falha de comunicação ou componente identificado."},

        {"0031","Linha 5 V",
        "Power-good da linha V_5P0 fora da condição esperada.",
        "Fonte, regulador de 5 V, capacitores e carga da linha.",
        "Checar curtos com energia desligada; medir 5 V em ponto validado da placa.",
        "Reparar curto, regulador ou componente identificado."},

        {"0032","V_CPUVCS",
        "Power-good da linha CPU VCS fora da condição esperada.",
        "Regulador V_CPUVCS e seus componentes.",
        "Checar resistência, sequência de alimentação e power-good no esquema.",
        "Reparar regulador ou carga defeituosa confirmada."},

        {"0033","Alimentação RAM",
        "Power-good da linha de memória V_MEM fora da condição esperada.",
        "Regulador de memória, capacitores e chips RAM.",
        "Verificar curto e tensão da linha de memória conforme revisão.",
        "Corrigir componente identificado; não substituir RAM sem testes."},

        {"0100","Treinamento RAM",
        "Identificação do fabricante da RAM incorreta ou ausente.",
        "RAM, CGPU/GPU, trilhas e alimentação.",
        "Checar tensões e sinais de memória; inspecionar danos físicos.",
        "Reparo de memória ou processador exige diagnóstico especializado."},

        {"0101","Treinamento RAM",
        "Falha na escrita dos dados de treinamento de leitura da RAM.",
        "RAM, interface de memória e CGPU/GPU.",
        "Verificar alimentação e integridade da interface RAM.",
        "Reparar defeito confirmado na memória/interface."},

        {"0102","Treinamento RAM",
        "Falha no ajuste de temporização de leitura da RAM.",
        "RAM, interface com GPU/CGPU, trilhas e alimentação.",
        "Verificar alimentação RAM, danos físicos e sinais da interface; não assumir solda BGA sem prova.",
        "Reparo especializado da falha confirmada; evitar aquecimento improvisado."},

        {"0103","Treinamento RAM",
        "Falha no ajuste de temporização de escrita da RAM.",
        "RAM, GPU/CGPU e interface.",
        "Conferir alimentação e integridade da interface de memória.",
        "Reparar componente ou conexão comprovadamente defeituoso."},

        {"0110","Endereçamento RAM",
        "Linha de endereço de memória falhou ou não responde.",
        "RAM, trilhas e interface GPU/CGPU.",
        "Verificar alimentação, integridade de trilhas e memória.",
        "Reparo especializado conforme componente confirmado."},

        {"0111","Dados RAM",
        "Linha de dados de memória falhou ou não responde.",
        "RAM, trilhas e interface GPU/CGPU.",
        "Verificar alimentação e integridade dos sinais de dados.",
        "Reparo especializado conforme diagnóstico."},

        {"1000 / E64","Leitor DVD",
        "Tempo de comunicação com o drive de DVD esgotado.",
        "Cabo SATA, alimentação e placa do leitor.",
        "Reencaixar cabos com console desligado; verificar leitor e conexões.",
        "Substituir cabo defeituoso; preservar identificação/chave do drive."},

        {"1001 / E65","Leitor DVD",
        "Não foi possível configurar DMA do drive DVD.",
        "Cabo SATA, placa do leitor e comunicação.",
        "Verificar conexão, cabo e alimentação do drive.",
        "Corrigir cabo ou placa confirmadamente defeituosa."},

        {"1002 / E66","Leitor DVD",
        "Não foi possível criar o dispositivo do drive DVD.",
        "Drive, firmware/chave, comunicação e cabos.",
        "Verificar integridade, cabos e histórico de troca do leitor.",
        "Restaurar compatibilidade e reparar defeito comprovado."},

        {"1003 / E67","Disco rígido",
        "Comunicação com HDD excedeu tempo.",
        "HDD, conector, cabo e southbridge.",
        "Testar inicialização sem HDD e verificar encaixe.",
        "Reparar conector ou substituir HDD defeituoso após preservar dados."},

        {"1010 / E68","Disco rígido",
        "Falha ao configurar DMA do HDD.",
        "HDD, porta e comunicação.",
        "Testar sem HDD e inspecionar conector.",
        "Substituir HDD ou reparar conexão comprovadamente defeituosa."},

        {"1011 / E69","Disco rígido",
        "Não foi possível criar instância do HDD.",
        "HDD e comunicação.",
        "Testar sem HDD e conferir contatos.",
        "Reparar ou substituir unidade identificada."},

        {"1012 / E70","Disco rígido",
        "HDD detectado, mas não encontrado corretamente.",
        "HDD, partições e comunicação.",
        "Testar sem HDD e em unidade compatível.",
        "Restaurar ou substituir HDD, preservando dados quando possível."},

        {"1013 / E71","Dashboard / NAND",
        "Sistema não encontrou dash.xex.",
        "NAND, sistema e armazenamento.",
        "Remover HDD para testar; verificar histórico de atualização e backup.",
        "Recuperar sistema/NAND com procedimento compatível e backup."},

        {"1020 / E72","SMC / NAND",
        "Configuração SMC corrompida.",
        "NAND e configuração de firmware.",
        "Verificar backup e histórico de gravação.",
        "Reconstruir firmware somente com dados corretos do console."},

        {"1021 / E73","Link eDRAM",
        "Falha de treinamento do link de alta velocidade GPU-eDRAM.",
        "GPU, eDRAM e interconexão.",
        "Descartar alimentação e danos externos antes de diagnóstico BGA.",
        "Reparo especializado após confirmação."},

        {"1022 / E74","Link eDRAM",
        "Falha de calibração do link de alta velocidade GPU-eDRAM.",
        "GPU/eDRAM e interface.",
        "Verificar alimentação e sinais da interface conforme placa.",
        "Reparo especializado após confirmação."},

        {"1023 / E75","Ethernet",
        "Falha de identificação/comunicação com PHY Ethernet.",
        "PHY Ethernet, barramento MDIO/MDC e southbridge.",
        "Inspecionar área Ethernet, alimentação e comunicação.",
        "Reparar PHY ou trilha confirmada."},

        {"1030 / E76","Ethernet",
        "PHY Ethernet não concluiu reset.",
        "PHY, reset e alimentação.",
        "Verificar circuito de reset e alimentação Ethernet.",
        "Reparar componente confirmado."},

        {"1031 / E77","Ethernet",
        "Falha de leitura/gravação no PHY Ethernet.",
        "PHY, comunicação e southbridge.",
        "Verificar alimentação e linhas de comunicação.",
        "Reparar componente ou trilha confirmada."},

        {"1032 / E78","GPU incompatível",
        "Revisão da GPU não é compatível com a placa.",
        "GPU e compatibilidade de revisão.",
        "Confirmar revisão da placa e histórico de troca do chip.",
        "Usar GPU compatível com a placa."},

        {"1033 / E79","NAND / sistema",
        "Arquivo xam.xex não encontrado na NAND.",
        "NAND, firmware e HDD.",
        "Testar sem HDD; verificar backup de NAND e histórico de atualização.",
        "Recuperar NAND com imagem compatível e dados originais."},

        {"1100 / E80","NAND / eFuses",
        "Incompatibilidade de LDV com a NAND.",
        "Firmware, atualização e eFuses.",
        "Verificar histórico de atualização/modificação e backup.",
        "Corrigir firmware apenas com conhecimento da revisão e LDV."},

        {"1101 / E81","Segurança / NAND",
        "Falha ao salvar configurações de segurança.",
        "NAND e armazenamento de configurações.",
        "Verificar integridade da NAND e backups.",
        "Reparar ou substituir NAND após avaliação especializada."},

        {"1102 / E82","CGPU / eDRAM",
        "Falha de calibração do link CGPU-eDRAM.",
        "CGPU, eDRAM e interconexão.",
        "Verificar alimentação e sinais; diagnóstico avançado de chip.",
        "Reparo especializado após confirmação."}
    };

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(Color.BLACK);

        home();
    }

    private void screen(String title) {
        ScrollView sc = new ScrollView(this);
        sc.setBackgroundColor(bg);
        sc.setFillViewport(true);
        sc.setClipToPadding(false);

        if (android.os.Build.VERSION.SDK_INT >= 30) {
            sc.setOnApplyWindowInsetsListener((v, insets) -> {
                android.graphics.Insets bars = insets.getInsets(
                    android.view.WindowInsets.Type.statusBars()
                    | android.view.WindowInsets.Type.navigationBars()
                );
                sc.setPadding(0, bars.top, 0, bars.bottom);
                return insets;
            });
        }

        body = new LinearLayout(this);
        body.setOrientation(LinearLayout.VERTICAL);
        body.setPadding(24, 28, 24, 40);

        sc.addView(body);
        setContentView(sc);

        TextView heading = new TextView(this);
        heading.setText(title);
        heading.setTextSize(27);
        heading.setTypeface(
            Typeface.create(
                "sans-serif-condensed",
                Typeface.BOLD
            )
        );
        heading.setLetterSpacing(0.045f);
        heading.setTextColor(green);
        heading.setPadding(10, 18, 10, 22);
        body.addView(heading);

        View line = new View(this);
        line.setBackgroundColor(0xff285846);

        LinearLayout.LayoutParams lp =
            new LinearLayout.LayoutParams(-1, 2);
        lp.setMargins(8, 0, 8, 20);
        body.addView(line, lp);
    }

    private void label(String s, int size, int color) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(
            color == white ? 0xffF0F7F4 : color
        );
        t.setTypeface(
            Typeface.create(
                "sans-serif-medium",
                Typeface.NORMAL
            )
        );
        t.setLineSpacing(5, 1.08f);
        t.setPadding(12, 12, 12, 16);
        body.addView(t);
    }

    private void button(String s, Runnable r) {
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER_VERTICAL);
        container.setPadding(18, 6, 16, 6);

        GradientDrawable shape = new GradientDrawable();
        shape.setColor(0xff1C3035);
        shape.setCornerRadius(22);
        shape.setStroke(2, 0xff315B4C);

        container.setBackground(shape);
        container.setElevation(5f);

        TextView text = new TextView(this);
        text.setText(s);
        text.setTextSize(16);
        text.setTextColor(0xffF3FFF8);
        text.setTypeface(
            Typeface.create(
                "sans-serif-medium",
                Typeface.BOLD
            )
        );
        text.setPadding(6, 20, 8, 20);

        container.addView(
            text,
            new LinearLayout.LayoutParams(0, -2, 1)
        );

        TextView arrow = new TextView(this);
        arrow.setText("›");
        arrow.setTextSize(30);
        arrow.setTextColor(green);
        container.addView(arrow);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 7, 0, 7);

        body.addView(container, params);
        container.setOnClickListener(v -> r.run());
    }

    private void open(String url) {
        startActivity(
            new Intent(Intent.ACTION_VIEW, Uri.parse(url))
        );
    }

    private void home() {
        page = "home";
        screen("CONSOLE DOCTOR");

        label(
            "Diagnóstico • Reparo • Desbloqueio\nby Matheusoblack",
            16, white
        );

        button("NÃO LIGA", () -> pending());
        button("LIGA E DESLIGA", () -> pending());
        button("LUZ VERMELHA — CÓDIGOS E SOLUÇÕES", () -> codes());
        button("DESBLOQUEIO", () -> pending());
        button("IDENTIFICAR PLACA", () -> pending());
        button("SEM IMAGEM", () -> pending());
        button("LEITOR DE DISCO", () -> pending());
        button("DIAGNÓSTICO AVANÇADO", () -> pending());
    }

    private void pending() {
        Toast.makeText(
            this,
            "Função em desenvolvimento",
            Toast.LENGTH_SHORT
        ).show();
    }

    private void codes() {
        page = "codes";
        screen("LUZ VERMELHA");

        label(
            "Selecione a família do console antes de consultar o erro.",
            14, white
        );

        Spinner spin = new Spinner(this);

        String[] models = {
            "Slim / Super Slim",
            "Fat"
        };

        ArrayAdapter<String> a = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            models
        );

        spin.setAdapter(a);
        spin.setSelection(model.equals("Fat") ? 1 : 0);
        body.addView(spin);

        spin.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onNothingSelected(
                    AdapterView<?> p
                ) {}

                public void onItemSelected(
                    AdapterView<?> p,
                    View v,
                    int pos,
                    long id
                ) {
                    model = models[pos];
                }
            }
        );

        label(
            "Pesquisar pelo código secundário ou E-code:",
            15, white
        );

        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setHint("Ex.: 0003, 0102, E74");
        input.setTextColor(white);
        input.setHintTextColor(Color.LTGRAY);
        body.addView(input);

        LinearLayout list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        body.addView(list);

        fill(list, "");

        input.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(
                CharSequence s, int st, int c, int a
            ) {}

            public void onTextChanged(
                CharSequence s, int st, int b, int c
            ) {
                fill(list, s.toString());
            }

            public void afterTextChanged(Editable e) {}
        });

        button("COMO LER O CÓDIGO", () -> howTo());
        button("VOLTAR", () -> home());
    }

    private void fill(LinearLayout list, String query) {
        list.removeAllViews();

        for (String[] e : entries) {
            if (!(e[0] + " " + e[1])
                .toLowerCase()
                .contains(query.trim().toLowerCase())) {
                continue;
            }

            Button b = new Button(this);
            b.setText(e[0] + " — " + e[1]);
            b.setAllCaps(false);
            b.setTextColor(white);
            b.setTypeface(
                Typeface.create(
                    "sans-serif-medium",
                    Typeface.BOLD
                )
            );
            b.setBackgroundTintList(
                ColorStateList.valueOf(0xff294338)
            );

            list.addView(b);
            b.setOnClickListener(v -> detail(e));
        }
    }

    private void section(String title, String content) {
        label(title, 19, green);
        label(content, 16, white);
    }

    private void detail(String[] e) {
        page = "detail";
        screen("ERRO " + e[0]);

        label(
            "Modelo selecionado: " + model,
            15, 0xffB7C9BE
        );

        section("O QUE SIGNIFICA", e[2]);

        if (e[0].equals("0003")) {
            section(
                "DIFERENÇA IMPORTANTE",
                model.equals("Fat")
                    ? "Neste Fat, investigar power-good e alimentação da GPU (V_GPUCORE)."
                    : "Neste Slim/Super Slim, investigar a linha de 3,3 V (V_3P3) e seu sinal power-good. Não confundir com a linha da GPU dos Fat."
            );
        }

        if (e[0].equals("0023")) {
            section(
                "ATENÇÃO À REVISÃO",
                model.equals("Fat")
                    ? "Nos Fat, investigar o gerador de clock e a comunicação com ANA/HANA."
                    : "Nos Slim/E, investigar a alimentação V_CPUEDRAM."
            );
        }

        section("POSSÍVEIS ÁREAS COM DEFEITO", e[3]);
        section("SEQUÊNCIA DE TESTES", e[4]);
        section("POSSÍVEIS SOLUÇÕES", e[5]);

        section(
            "SEGURANÇA",
            "Desligue da tomada antes de medir resistência/continuidade. " +
            "Nunca meça resistência com a placa energizada. " +
            "Para tensão, use escala DC e pontos confirmados no esquema da revisão. " +
            "Resistência baixa em Vcore nem sempre significa curto. " +
            "Não faça reflow com soprador doméstico, forno ou toalha."
        );

        button(
            "ABRIR REFERÊNCIA TÉCNICA",
            () -> open(SOURCE)
        );

        button(
            "PESQUISAR VÍDEOS",
            () -> open(
                "https://www.youtube.com/results?search_query=" +
                Uri.encode(
                    "Xbox 360 " + model +
                    " error " + e[0] + " repair"
                )
            )
        );

        button("VOLTAR AOS CÓDIGOS", () -> codes());
    }

    private void howTo() {
        page = "howto";
        screen("LER CÓDIGO SECUNDÁRIO");

        section(
            "Xbox 360 Fat / S",
            "Com o erro vermelho ativo, segure Sync e pressione Eject quatro vezes. " +
            "A cada pressão conte os quadrantes: 4 luzes = 0, " +
            "1 = 1, 2 = 2, 3 = 3. " +
            "Anote os quatro dígitos. " +
            "No Xbox 360 E, conte os flashes entre pausas conforme o painel."
        );

        section(
            "IMPORTANTE",
            "Códigos na tela (E64–E82) e códigos secundários " +
            "são apresentados juntos nesta biblioteca. " +
            "Se não houver código ou o console não ligar, " +
            "use o diagnóstico por sintomas quando disponível."
        );

        button("VOLTAR", () -> codes());
    }

    @Override
    public void onBackPressed() {
        if (page.equals("home")) {
            super.onBackPressed();
        } else if (
            page.equals("detail") ||
            page.equals("howto")
        ) {
            codes();
        } else {
            home();
        }
    }
}
