package com.example.minibrowser;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.webkit.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  WebView view;
  EditText filterInput;
  Button loadFiltersBtn;

  static { System.loadLibrary("mini"); }

  @Override protected void onCreate(Bundle s) {
    super.onCreate(s);
    setContentView(R.layout.activity_main);

    filterInput = findViewById(R.id.filter_input);
    loadFiltersBtn = findViewById(R.id.load_btn);
    view = findViewById(R.id.webview);

    view.getSettings().setJavaScriptEnabled(false);
    view.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest req) {
        String url = req.getUrl().toString();
        return !Mini.shouldLoadUrl(url);
      }
    });

    view.setDownloadListener((url, ua, cd, mt, cl) -> Mini.handleDownload(url));

    loadFiltersBtn.setOnClickListener(v -> {
      String url = filterInput.getText().toString();
      new Thread(() -> Mini.fetchAndLoadFilters(url)).start();
    });

    view.loadUrl("https://duckduckgo.com");
  }
}
