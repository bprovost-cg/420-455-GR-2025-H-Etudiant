package com.example.stockage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        if (navHostFragment == null) {
            return;
        }

        NavController navController = navHostFragment.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Button btnStockageInterne = findViewById(R.id.btn_stockage_interne);
        Button btnStockageExterne = findViewById(R.id.btn_stockage_externe);
        Button btnStockagePartage = findViewById(R.id.btn_stockage_partage);

        btnStockageInterne.setOnClickListener(v -> navigateTo(R.id.go_to_stockage_interne));
        btnStockageExterne.setOnClickListener(v -> navigateTo(R.id.go_to_stockage_externe));
        btnStockagePartage.setOnClickListener(v -> navigateTo(R.id.go_to_stockage_partage));
    }

    @SuppressLint("NonConstantResourceId")
    private void navigateTo(int navigationId) {
        var navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        navController.navigate(navigationId);
    }
}
