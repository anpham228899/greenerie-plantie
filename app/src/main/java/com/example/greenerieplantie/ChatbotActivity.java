package com.example.greenerieplantie;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.greenerieplantie.databinding.ActivityChatbotBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatbotActivity extends AppCompatActivity {

    private ActivityChatbotBinding binding;
    private ChatAdapter chatAdapter;
    private FAQRepository faqRepo;
    private TextEmbeddingHelper embedHelper;
    private MyImageClassifier imageClassifier;
    private ExecutorService ioExecutor = Executors.newSingleThreadExecutor();
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private boolean isReady = false;
    private String lastDetectedTag = null;
    private final boolean isFirebaseReady = false; // Bật lại thành true khi tích hợp Firebase

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatbotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        copyAssetsToGallery();

        chatAdapter = new ChatAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(chatAdapter);
        chatAdapter.addMessage(new ChatMessage("Hello, how can I help you today?", false));
        binding.imgSendButton.setEnabled(false);

        loadUserProfile();

        ioExecutor.execute(() -> {
            try {
                File embedFile = new File(getFilesDir(), "faq_embeds.json");
                if (!embedFile.exists()) {
                    FAQEmbedUpdater.updateEmbeddings(this);
                    Log.d("Chatbot", "Đã tạo và lưu embeddings FAQ mới");
                }

                faqRepo = new FAQRepository(this);
                embedHelper = new TextEmbeddingHelper(this);
                imageClassifier = new MyImageClassifier(this);

                runOnUiThread(() -> {
                    isReady = true;
                    binding.imgSendButton.setEnabled(true);
                    Toast.makeText(this, "Initialization Successful", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                Log.e("Chatbot", "Initialization failed " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(this, "Data Initialization Failed", Toast.LENGTH_SHORT).show());
            }
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavMenuActivity.setupNavMenu(bottomNav, this, R.id.nav_chatbot);
        bottomNav.setSelectedItemId(R.id.nav_chatbot);

        binding.imgSendButton.setOnClickListener(v -> {
            if (!isReady) {
                Toast.makeText(this, "Loading data, please wait....", Toast.LENGTH_SHORT).show();
                return;
            }

            final String userMsg = binding.tvHintText.getText().toString().trim();
            if (userMsg.isEmpty()) return;

            chatAdapter.addMessage(new ChatMessage(userMsg, true));
            binding.tvHintText.setText("");
            binding.recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);

            ioExecutor.execute(() -> {
                try {
                    List<FAQItem> data = faqRepo.getFaqData();
                    List<List<Float>> embeds = faqRepo.getFaqEmbeds();
                    List<Float> qVec = embedHelper.embed(userMsg);
                    FAQItem matched;

                    if (lastDetectedTag != null) {
                        List<FAQItem> filteredData = new ArrayList<>();
                        List<List<Float>> filteredEmbeds = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getTag().equalsIgnoreCase(lastDetectedTag)) {
                                filteredData.add(data.get(i));
                                filteredEmbeds.add(embeds.get(i));
                            }
                        }
                        matched = !filteredData.isEmpty()
                                ? FAQMatcher.findBest(filteredData, filteredEmbeds, qVec, null)
                                : new FAQItem("?", "Sorry, no matching information found.", lastDetectedTag, "");
                        lastDetectedTag = null;
                    } else {
                        matched = FAQMatcher.findBest(data, embeds, qVec, null);
                    }

                    FAQItem finalMatched = matched;
                    runOnUiThread(() -> {
                        chatAdapter.addMessage(new ChatMessage(finalMatched.getAnswer(), false));
                        binding.recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                    });

                } catch (Exception e) {
                    Log.e("ChatbotError", "Error handling: " + e.getMessage(), e);
                    runOnUiThread(() -> Toast.makeText(ChatbotActivity.this, "An error occurred.", Toast.LENGTH_SHORT).show());
                }
            });
        });

        binding.imgCameraButton.setOnClickListener(v -> showImageChoiceDialog());
    }

    private void loadUserProfile() {
        if (!isFirebaseReady) {
            Log.d("Chatbot", "Firebase chưa được cấu hình. Bỏ qua loadUserProfile.");
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Log.d("Chatbot", "User chưa đăng nhập, giữ mặc định.");
            return;
        }

        String userId = currentUser.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String avatarUrl = documentSnapshot.getString("avatarUrl");

                        runOnUiThread(() -> {
                            if (name != null && !name.isEmpty()) {
                                chatAdapter.updateWelcomeMessage("Hello " + name + ", how can I help you today?");
                            }
                            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                                chatAdapter.setUserAvatarUrl(avatarUrl); // xem  ChatAdapter để hiển thị avatar
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> Log.w("Chatbot", "Lấy profile user thất bại: " + e.getMessage()));
    }

    private void showImageChoiceDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_image_picker, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        LinearLayout llTakePhoto = dialogView.findViewById(R.id.ll_take_photo);
        LinearLayout llChooseFromLibrary = dialogView.findViewById(R.id.ll_choose_from_library);
        ImageView imgClose = dialogView.findViewById(R.id.img_close_button);

        llTakePhoto.setOnClickListener(v -> {
            dialog.dismiss();
            startCamera();
        });

        llChooseFromLibrary.setOnClickListener(v -> {
            dialog.dismiss();
            openGallery();
        });

        imgClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!isReady) return;
        if (resultCode != RESULT_OK) return;

        Uri imageUri = null;

        try {
            if (requestCode == REQUEST_GALLERY && data != null) {
                imageUri = data.getData();
            } else if (requestCode == REQUEST_CAMERA && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                if (photo != null) {
                    File tempFile = File.createTempFile("captured_image", ".jpg", getCacheDir());
                    try (FileOutputStream out = new FileOutputStream(tempFile)) {
                        photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        imageUri = Uri.fromFile(tempFile);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (imageUri == null) return;

        Uri finalImageUri = imageUri;
        runOnUiThread(() -> {
            chatAdapter.addMessage(new ChatMessage(finalImageUri));
            binding.recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
        });

        ioExecutor.execute(() -> {
            try {
                MyImageClassifier.ClassificationResult result = imageClassifier.classifyImage(finalImageUri);
                String tag = result.label;
                float confidence = result.confidence;

                if (confidence < 0.6f) {
                    runOnUiThread(() -> Toast.makeText(this, "Low reliability, please try again", Toast.LENGTH_SHORT).show());
                    return;
                }

                lastDetectedTag = tag;
                List<FAQItem> dataList = faqRepo.getFaqData();
                StringBuilder reply = new StringBuilder();

                for (FAQItem item : dataList) {
                    if (item.getTag().equalsIgnoreCase(tag)) {
                        if ("symptom".equalsIgnoreCase(item.getContext())) {
                            reply.append("Symptoms: ").append(item.getAnswer()).append("\n");
                        } else if ("cause".equalsIgnoreCase(item.getContext())) {
                            reply.append("Causes: ").append(item.getAnswer());
                        }
                    }
                }

                if (reply.length() == 0) {
                    reply.append("Sorry, no information was found about the symptoms or causes of this condition.");
                }

                String finalReply = reply.toString();
                runOnUiThread(() -> {
                    chatAdapter.addMessage(new ChatMessage("Detected disease: " + tag + " (Confidence: " + (int)(confidence * 100) + "%)", false));
                    chatAdapter.addMessage(new ChatMessage(finalReply, false));
                    binding.recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Image classification error", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void copyAssetsToGallery() {
        try {
            String[] files = getAssets().list("images");
            if (files != null) {
                for (String filename : files) {
                    InputStream in = getAssets().open("images/" + filename);
                    File picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    if (!picturesDir.exists()) picturesDir.mkdirs();
                    File outFile = new File(picturesDir, filename);
                    OutputStream out = new FileOutputStream(outFile);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    in.close();
                    out.flush();
                    out.close();

                    MediaScannerConnection.scanFile(this,
                            new String[]{outFile.getAbsolutePath()}, null, null);
                }
            }
        } catch (IOException e) {
            Log.e("GalleryCopy", "Lỗi khi lấy ảnh: " + e.getMessage(), e);
        }
    }
}
