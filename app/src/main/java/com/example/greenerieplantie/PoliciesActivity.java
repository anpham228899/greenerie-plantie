package com.example.greenerieplantie;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PoliciesActivity extends AppCompatActivity {
    private ImageButton backButton;  // Sử dụng ImageButton thay vì ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ẩn thanh ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_policies);

        TextView textPolicy = findViewById(R.id.text_policy_description);

        String fullText = "1. Privacy Policy\n" +
                "Greenary is committed to protecting users’ personal data—including full name, email, phone number, and shipping address and will use it solely to process orders, provide plant-care advice, and improve our services. We may share data with third parties such as shipping partners (Viettel Post, Giao Hàng Nhanh) or payment gateways (Momo, Napas) only to fulfill transactions; all partners must sign confidentiality agreements. Users have the right to access, correct, or request deletion of their data via the “Account Settings” section in the app or by contacting Greenary directly. This policy is reviewed periodically, and any changes will be announced to users via in-app pop-ups or email before taking effect.\n\n" +
                "2. Terms of Service\n" +
                "By using Greenary, you agree that the app is licensed to you for personal, non-commercial use only. Any copying, redistribution, or abuse of our services is strictly prohibited. You must not upload content that infringes copyright, is obscene, defamatory, or disruptive; violations may result in warnings or immediate account suspension without prior notice. Greenary reserves the right to terminate or suspend accounts suspected of fraud, abuse, or breach of these terms. All content, images, and trademarks in Greenary are owned by Greenary or our partners; you may not reuse any materials without written permission. Any disputes will be governed by Vietnamese law and resolved by the courts in Ho Chi Minh City.\n\n" +
                "3. Shipping Policy\n" +
                "Greenary offers Cash on Delivery (COD) or prepaid orders, with order processing taking 1–2 business days. Delivery time is typically 2–4 days within Ho Chi Minh City and 4–6 days to suburban areas. Shipping fees are calculated based on package weight and dimensions; orders over ₫500,000 within Ho Chi Minh City qualify for free shipping. Customers can track their orders in the “My Orders” section of the app or via the tracking number provided by our shipping partners. If delivery exceeds the promised timeframe, Greenary will cover any additional fees and assist in coordinating with the carrier.\n\n" +
                "4. Return & Refund Policy\n" +
                "Customers may request returns within 7 days of receipt if their seedlings arrive damaged, incorrect, or not as described (e.g., dead roots, cracked pots). To initiate a return, submit a claim via the in-app chat with photos showing the issue; Greenary will verify and provide a prepaid return label if the fault lies with us or our carrier. Once we receive and inspect the return, refunds will be issued to your original payment method or app wallet within 3–5 business days, or we can provide a coupon of equal value for your next purchase. If you change your mind (non-defective return), you’ll be responsible for return shipping costs; Greenary will guide you through the process.\n\n" +
                "5. Payment Policy\n" +
                "Greenary accepts COD, bank transfers (Vietcombank, Techcombank), e-wallets (Momo, VNPay), and international cards (Visa, MasterCard). All transactions are SSL-encrypted and PCI-DSS compliant to protect card and payment data. In case of any payment dispute, Greenary and our payment partners will cooperate to resolve and refund valid claims arising from system errors on our side.\n\n" +
                "6. Security Policy\n" +
                "All connections between the app and Greenary’s servers use HTTPS and AES-256 encryption to safeguard sensitive data. We enforce secure authentication with OTP or a 6-digit PIN, automatic logout after 15 minutes of inactivity, and account lockout after multiple failed login attempts. Regular vulnerability scans and patch updates are conducted in line with OWASP recommendations to defend against emerging threats.\n\n" +
                "7. Cookie Policy\n" +
                "Greenary uses cookies to maintain login sessions, remember user preferences, and analyze in-app behavior for ongoing improvement. You can disable or clear cookies at any time in Settings → Legal → Cookie Policy, but note that certain features may not function properly if cookies are blocked.\n\n" +
                "8. User Conduct & Content Policy\n" +
                "Users must not post content that is illegal, defamatory, pornographic, or infringes intellectual property rights; violations may result in content removal or account suspension without notice. Greenary reserves the right to permanently ban accounts or delete offending content after prior warning. To report inappropriate content, use the “Report” feature in the app; we commit to investigating and responding within 48 business hours.";

        SpannableStringBuilder builder = new SpannableStringBuilder(fullText);

        // In đậm các tiêu đề
        boldText(builder, fullText, "1. Privacy Policy");
        boldText(builder, fullText, "2. Terms of Service");
        boldText(builder, fullText, "3. Shipping Policy");
        boldText(builder, fullText, "4. Return & Refund Policy");
        boldText(builder, fullText, "5. Payment Policy");
        boldText(builder, fullText, "6. Security Policy");
        boldText(builder, fullText, "7. Cookie Policy");
        boldText(builder, fullText, "8. User Conduct & Content Policy");

        textPolicy.setText(builder);

        // Xử lý sự kiện click button quay lại
        backButton = findViewById(R.id.btn_policy_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình trước
                onBackPressed();
            }
        });
    }

    private void boldText(SpannableStringBuilder builder, String fullText, String textToBold) {
        int start = fullText.indexOf(textToBold);
        if (start >= 0) {
            builder.setSpan(new StyleSpan(Typeface.BOLD),
                    start,
                    start + textToBold.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}

