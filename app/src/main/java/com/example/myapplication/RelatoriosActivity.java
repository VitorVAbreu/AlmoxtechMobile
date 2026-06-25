package com.example.myapplication;



import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.almoxtech.R;
import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Requisicao;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatoriosActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        db = AppDatabase.getInstance(this);

        montarGraficoProdutosMaisSolicitados();
        montarGraficoMovimentacao();
    }

    private void montarGraficoProdutosMaisSolicitados() {
        List<Requisicao> saidas = db.requisicaoDAO().listarSaidas();

        // Conta quantidade por produto
        Map<String, Integer> mapa = new HashMap<>();
        for (Requisicao r : saidas) {
            mapa.put(r.nomeProduto,
                    mapa.getOrDefault(r.nomeProduto, 0) + r.quantidade);
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(
                Color.parseColor("#1565C0"),
                Color.parseColor("#C62828"),
                Color.parseColor("#1B5E20"),
                Color.parseColor("#E65100"),
                Color.parseColor("#4A148C")
        );
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setData(new PieData(dataSet));
        pieChart.getDescription().setText("Produtos mais solicitados");
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void montarGraficoMovimentacao() {
        List<Requisicao> entradas = db.requisicaoDAO().listarEntradas();
        List<Requisicao> saidas   = db.requisicaoDAO().listarSaidas();

        int totalEntradas = 0;
        for (Requisicao r : entradas) totalEntradas += r.quantidade;

        int totalSaidas = 0;
        for (Requisicao r : saidas) totalSaidas += r.quantidade;

        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0f, totalEntradas));
        barEntries.add(new BarEntry(1f, totalSaidas));

        BarDataSet dataSet = new BarDataSet(barEntries, "Movimentação");
        dataSet.setColors(
                Color.parseColor("#1565C0"),
                Color.parseColor("#C62828")
        );
        dataSet.setValueTextSize(12f);

        BarChart barChart = findViewById(R.id.barChart);
        barChart.setData(new BarData(dataSet));
        barChart.getDescription().setText("Entradas x Saídas");
        barChart.getXAxis().setValueFormatter(
                new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(
                        new String[]{"Entradas", "Saídas"}
                )
        );
        barChart.animateY(1000);
        barChart.invalidate();
    }
}