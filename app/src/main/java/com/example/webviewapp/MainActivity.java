package com.example.webviewapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    EditText etUrl;
    Button btnGo, btnHistory, btnClear;
    ArrayList<String> historyList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        etUrl = findViewById(R.id.etUrl);
        btnGo = findViewById(R.id.btnGo);
        btnHistory = findViewById(R.id.btnHistory);
        btnClear = findViewById(R.id.btnClear);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        btnGo.setOnClickListener(v -> {
            String url = etUrl.getText().toString().trim();
            if (!url.startsWith("http")) {
                url = "https://www.google.com/search?q=" + url;
            }
            webView.loadUrl(url);
            historyList.add(url);
        });
        btnHistory.setOnClickListener(v -> {
            if (historyList.isEmpty()) {
                showDialog("History", "No history found");
                return;
            }
            StringBuilder history = new StringBuilder();
            for (String item : historyList) {
                history.append(item).append("\n\n");
            }
            showDialog("Browsing History", history.toString());
        });
        btnClear.setOnClickListener(v -> {
            historyList.clear();
            webView.clearHistory();
            showDialog("Success", "History cleared");
        });
    }
    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
