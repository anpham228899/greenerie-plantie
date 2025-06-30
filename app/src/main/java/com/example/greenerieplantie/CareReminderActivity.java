package com.example.greenerieplantie;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Set;

public class CareReminderActivity extends AppCompatActivity {

    // Các view cần thiết cho dropdown
    private ImageView productDropdown, timeDropdown, contentDropdown;
    private TextView selectedProduct, selectedTime, selectedContent;

    // Các TextView cho các ngày trong tuần
    private TextView mon, tue, wed, thurs, fri, sat, sun;

    // Set để theo dõi các ngày được chọn
    private Set<TextView> selectedDays = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_care_reminder);

        // Áp dụng window insets cho layout chính (để tránh bị che bởi system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.plant_news), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các view
        productDropdown = findViewById(R.id.img_dropdown_product);
        timeDropdown = findViewById(R.id.img_dropdown_time);
        contentDropdown = findViewById(R.id.img_dropdown_content);

        selectedProduct = findViewById(R.id.tv_carereminder_product);
        selectedTime = findViewById(R.id.tv_time);
        selectedContent = findViewById(R.id.tv_content);

        // Khởi tạo các ngày trong tuần
        mon = findViewById(R.id.tv_carereminder_mon);
        tue = findViewById(R.id.tv_carereminder_thue);
        wed = findViewById(R.id.tv_carereminder_wed);
        thurs = findViewById(R.id.tv_carereminder_thurs);
        fri = findViewById(R.id.tv_carereminder_fri);
        sat = findViewById(R.id.tv_carereminder_sat);
        sun = findViewById(R.id.tv_carereminder_sun);

        // Thêm sự kiện click cho dropdown
        setDropdownListeners();

        // Thêm sự kiện click cho các ngày trong tuần
        setDayListeners();
    }

    private void setDropdownListeners() {
        // Dropdown cho sản phẩm
        productDropdown.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(CareReminderActivity.this, v);
            getMenuInflater().inflate(R.menu.menu_carereminder_product, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                selectedProduct.setText(item.getTitle());  // Cập nhật TextView với sản phẩm đã chọn
                return true;
            });

            popupMenu.show();
        });

        // Dropdown cho thời gian
        timeDropdown.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(CareReminderActivity.this, v);
            getMenuInflater().inflate(R.menu.menu_carereminder_time, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                selectedTime.setText(item.getTitle());  // Cập nhật TextView với thời gian đã chọn
                return true;
            });

            popupMenu.show();
        });

        // Dropdown cho nội dung nhắc nhở
        contentDropdown.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(CareReminderActivity.this, v);
            getMenuInflater().inflate(R.menu.menu_carereminder_content, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                selectedContent.setText(item.getTitle());  // Cập nhật TextView với nội dung đã chọn
                return true;
            });

            popupMenu.show();
        });
    }

    private void setDayListeners() {
        // Thêm sự kiện click cho các ngày trong tuần
        mon.setOnClickListener(v -> toggleDaySelection(mon));
        tue.setOnClickListener(v -> toggleDaySelection(tue));
        wed.setOnClickListener(v -> toggleDaySelection(wed));
        thurs.setOnClickListener(v -> toggleDaySelection(thurs));
        fri.setOnClickListener(v -> toggleDaySelection(fri));
        sat.setOnClickListener(v -> toggleDaySelection(sat));
        sun.setOnClickListener(v -> toggleDaySelection(sun));
    }

    private void toggleDaySelection(TextView day) {
        // Kiểm tra nếu ngày đã được chọn
        if (selectedDays.contains(day)) {
            // Nếu đã chọn, bỏ chọn (reset lại màu)
            selectedDays.remove(day);
            day.setTextColor(Color.parseColor("#000000"));
            day.setTypeface(null, android.graphics.Typeface.NORMAL);
        } else {
            // Nếu chưa chọn, chọn ngày (highlight)
            selectedDays.add(day);
            day.setTextColor(Color.parseColor("#388E3C"));
            day.setTypeface(null, android.graphics.Typeface.BOLD);
        }
    }
}
