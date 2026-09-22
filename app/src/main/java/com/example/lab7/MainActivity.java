package com.example.lab7;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;
    List<Article> articleList = new ArrayList<>();

    private final ActivityResultLauncher<Intent> detailLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    int position = result.getData().getIntExtra("position", -1);
                    if (position >= 0 && position < articleList.size()) {
                        articleList.get(position).setViews(articleList.get(position).getViews() + 1);
                        articleAdapter.notifyItemChanged(position);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Khởi tạo danh sách Article
        articleList.add(new Article("Học Android Studio", "Android Studio là môi trường phát triển tích hợp được sử dụng để xây dựng các ứng dụng Android.", "https://images.unsplash.com/photo-1607252650355-f7fd0460ccdb?w=600&h=400&fit=crop", 10));
        articleList.add(new Article("Lập trình Java", "Java là một ngôn ngữ lập trình phổ biến, được sử dụng để phát triển nhiều loại ứng dụng.", "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=600&h=400&fit=crop", 25));
        articleList.add(new Article("Ứng dụng di động", "Ứng dụng di động cung cấp nhiều tiện ích và trải nghiệm cho người dùng trên điện thoại thông minh.", "https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?w=600&h=400&fit=crop", 18));
        articleList.add(new Article("Cơ sở dữ liệu", "Cơ sở dữ liệu được sử dụng để lưu trữ, quản lý và truy xuất dữ liệu một cách hiệu quả.", "https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=600&h=400&fit=crop", 32));
        articleList.add(new Article("Phát triển Web", "Phát triển Web bao gồm việc xây dựng và duy trì các trang Web và ứng dụng Web.", "https://images.unsplash.com/photo-1547658719-da2b51169166?w=600&h=400&fit=crop", 15));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. findViewById() để lấy RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // 3. Khởi tạo ArticleAdapter
        articleAdapter = new ArticleAdapter(articleList, new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Article article, int position) {
                Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
                intent.putExtra("article", article);
                intent.putExtra("position", position);
                detailLauncher.launch(intent);
            }
        });

        // 4. recyclerView.setLayoutManager()
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 5. recyclerView.setAdapter()
        recyclerView.setAdapter(articleAdapter);
    }
}
