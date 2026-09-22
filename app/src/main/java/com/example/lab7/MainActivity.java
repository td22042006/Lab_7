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
                        Article article = articleList.get(position);
                        article.setViews(article.getViews() + 1);
                        articleAdapter.notifyItemChanged(position);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Khởi tạo danh sách Article (views ban đầu luôn = 0, ảnh local từ drawable)
        articleList.add(new Article("Cần Thơ", "Cần Thơ là thành phố trực thuộc Trung ương và là một trong những trung tâm kinh tế, văn hóa quan trọng của vùng Đồng bằng sông Cửu Long.", "can_tho", 0));
        articleList.add(new Article("Bến Tre", "Bến Tre nổi tiếng với những vườn dừa xanh mát, các sản phẩm từ dừa và cảnh quan sông nước đặc trưng của miền Tây.", "ben_tre", 0));
        articleList.add(new Article("An Giang", "An Giang có nhiều cảnh quan thiên nhiên và địa điểm văn hóa đặc sắc, nổi bật với vùng Bảy Núi và các lễ hội truyền thống.", "an_giang", 0));
        articleList.add(new Article("Kiên Giang", "Kiên Giang có nhiều điểm đến nổi tiếng với biển đảo, trong đó Phú Quốc là một địa điểm du lịch được nhiều du khách biết đến.", "kien_giang", 0));
        articleList.add(new Article("Cà Mau", "Cà Mau nằm ở cực Nam của Việt Nam, nổi bật với hệ sinh thái rừng ngập mặn và vùng đất Mũi Cà Mau.", "ca_mau", 0));

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
