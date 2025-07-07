package com.example.minibrowser;

import android.util.Log;
import java.io.*;
import java.net.*;
import java.util.*;

public class Mini {
  public static native boolean shouldLoadUrl(String url);
  public static native void handleDownload(String url);
  public static native void onFilterDownloadComplete(String[] lines);

  public static void fetchAndLoadFilters(String url) {
    try {
      URL u = new URL(url);
      BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
      List<String> a = new ArrayList<>();
      String line;
      while ((line = in.readLine()) != null) {
        if (!line.startsWith("!") && !line.startsWith("[")) a.add(line.trim());
      }
      onFilterDownloadComplete(a.toArray(new String[0]));
      Log.i("Mini", "Loaded " + a.size() + " filters");
    } catch (Exception e) {
      Log.e("Mini", "Failed to load filters", e);
    }
  }
}
