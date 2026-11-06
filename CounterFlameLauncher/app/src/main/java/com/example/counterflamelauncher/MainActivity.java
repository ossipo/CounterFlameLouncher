package com.example.counterflamelauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    private LinearLayout root;
    private TextView status;
    private String gamePackage;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(12, 13, 17));
        getWindow().setNavigationBarColor(Color.rgb(12, 13, 17));
        buildScreen();
        findGame();
    }

    private GradientDrawable rounded(int color, int radiusDp) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radiusDp * getResources().getDisplayMetrics().density);
        return d;
    }

    private TextView text(String value, float size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private void buildScreen() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(dp(26), dp(34), dp(26), dp(24));
        root.setBackgroundColor(Color.rgb(12, 13, 17));
        setContentView(root);

        TextView tag = text("CF  /  GAME HUB", 13, Color.rgb(235, 65, 75), true);
        tag.setLetterSpacing(0.14f);
        root.addView(tag);

        TextView title = text("COUNTERFLAME", 29, Color.WHITE, true);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, dp(20), 0, dp(8));
        root.addView(title);

        TextView subtitle = text("ANDROID LAUNCHER", 12, Color.rgb(150, 154, 166), true);
        subtitle.setLetterSpacing(0.18f);
        root.addView(subtitle);

        View spacer = new View(this);
        root.addView(spacer, new LinearLayout.LayoutParams(1, dp(54)));

        status = text("Ищем установленную игру…", 14, Color.rgb(190, 194, 204), false);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, 0, 0, dp(20));
        root.addView(status);

        TextView start = text("START GAME", 16, Color.WHITE, true);
        start.setGravity(Gravity.CENTER);
        start.setBackground(rounded(Color.rgb(205, 43, 55), 14));
        start.setPadding(0, dp(18), 0, dp(18));
        root.addView(start, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        start.setOnClickListener(v -> launchGame());

        TextView refresh = text("ПОВТОРИТЬ ПОИСК", 13, Color.rgb(220, 220, 225), true);
        refresh.setGravity(Gravity.CENTER);
        refresh.setPadding(0, dp(24), 0, dp(18));
        root.addView(refresh);
        refresh.setOnClickListener(v -> findGame());

        View bottomSpace = new View(this);
        root.addView(bottomSpace, new LinearLayout.LayoutParams(1, 0, 1));

        TextView footer = text("Независимый лаунчер • версия 1.0", 11,
                Color.rgb(105, 109, 120), false);
        root.addView(footer);
    }

    private void findGame() {
        gamePackage = null;
        PackageManager pm = getPackageManager();
        Intent query = new Intent(Intent.ACTION_MAIN, null);
        query.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = pm.queryIntentActivities(query, 0);

        for (ResolveInfo info : apps) {
            CharSequence label = info.loadLabel(pm);
            String name = label == null ? "" : label.toString().toLowerCase(Locale.ROOT);
            String pkg = info.activityInfo.packageName;
            if (name.contains("counterflame") || pkg.toLowerCase(Locale.ROOT).contains("counterflame")) {
                gamePackage = pkg;
                break;
            }
        }

        if (gamePackage != null) {
            status.setText("ИГРА НАЙДЕНА  •  " + gamePackage);
            status.setTextColor(Color.rgb(110, 220, 150));
        } else {
            status.setText("CounterFlame не найдена. Проверь, что игра установлена.");
            status.setTextColor(Color.rgb(235, 170, 90));
        }
    }

    private void launchGame() {
        if (gamePackage == null) findGame();
        if (gamePackage == null) {
            Toast.makeText(this, "CounterFlame не найдена", Toast.LENGTH_LONG).show();
            return;
        }
        Intent launch = getPackageManager().getLaunchIntentForPackage(gamePackage);
        if (launch == null) {
            Toast.makeText(this, "Не удалось открыть игру", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            startActivity(launch);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка запуска: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
