package com.example.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Xử lý insets để tránh bị status bar che
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int position = getIntent().getIntExtra("position", -1);

        // Nút quay lại - trả position về MainActivity
        TextView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        Article article = (Article) getIntent().getSerializableExtra("article");

        if (article != null) {
            ImageView ivCover = findViewById(R.id.ivDetailCover);
            TextView tvTitle = findViewById(R.id.tvDetailTitle);
            TextView tvViews = findViewById(R.id.tvDetailViews);
            TextView tvContent = findViewById(R.id.tvDetailContent);

            Glide.with(this)
                    .load(article.getImgCover())
                    .centerCrop()
                    .placeholder(R.drawable.ic_cover)
                    .into(ivCover);

            tvTitle.setText(article.getTitle());
            tvViews.setText("Views: " + (article.getViews() + 1));
            tvContent.setText(article.getContent());
        }
    }

    @Override
    public void onBackPressed() {
        int position = getIntent().getIntExtra("position", -1);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}
