package com.example.aplikasiresto;

//NAMA  : Mohammad Noor Ihsan Akbar
//NIM   : 10120077
//KELAS : IF-2

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tabIndicator;
    private Button btnNext;
    private int position = 0;
    private Button btnGetStarted;
    private Animation btnAnim;
    private TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // membuat aktivitas menjadi layar penuh
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // saat aktivitas ini akan diluncurkan, perlu dicek apakah sudah pernah dibuka sebelumnya atau belum
        if (restorePrefData()) {
            // Intro sudah pernah dibuka sebelumnya, langsung ke MainActivity
            openMainActivity();
        }

        setContentView(R.layout.activity_intro);

        // menyembunyikan action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // inisialisasi tampilan
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // mengisi list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Temukan Resto di Sekitar Anda", "Selamat datang di RestoApp! RestoApp adalah aplikasi yang didesain untuk memberikan pengalaman kuliner yang lebih baik kepada para pengguna. Dengan RestoApp, Anda dapat dengan mudah menemukan restoran terbaik di sekitar Anda, mengeksplorasi menu makanan yang lezat, dan memesan makanan favorit Anda dengan cepat dan mudah.", R.drawable.image1));
        mList.add(new ScreenItem("Fitur Pencarian", "Dalam RestoApp, kami menyediakan fitur pencarian yang kuat untuk membantu Anda menemukan restoran yang Anda inginkan dengan mudah. Fitur pencarian kami dirancang untuk memberikan pengalaman yang efisien dan sesuai dengan preferensi Anda.", R.drawable.image2));
        mList.add(new ScreenItem("Detail Restoran dan Navigasi", "Setelah menemukan restoran yang menarik, periksa semua informasinya, termasuk alamat, waktu buka, menu, dan ulasan pelanggan. Fitur navigasi aplikasi peta kami mudah digunakan. Dengan satu sentuh, Anda dapat mendapatkan petunjuk yang jelas. Nikmati perjalanan kuliner yang mudah ditemukan..", R.drawable.image3));

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        // listener untuk tombol next
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size() - 1) { // ketika mencapai halaman terakhir
                    // tampilkan tombol GETSTARTED dan sembunyikan indikator dan tombol next
                    loadLastScreen();
                }
            }
        });

        // listener untuk tablayout
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // listener untuk tombol Get Started
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // buka MainActivity
                openMainActivity();
                // juga perlu menyimpan nilai boolean ke penyimpanan sehingga saat pengguna menjalankan aplikasi lain kali
                // kita dapat mengetahui bahwa mereka telah melihat layar intro sebelumnya
                savePrefsData();
            }
        });

        // listener untuk tombol skip
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened", false);
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.apply();
    }

    // menampilkan tombol GETSTARTED dan menyembunyikan indikator dan tombol next
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : menambahkan animasi ke tombol getstarted
        btnGetStarted.setAnimation(btnAnim);
    }

    private void openMainActivity() {
        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
