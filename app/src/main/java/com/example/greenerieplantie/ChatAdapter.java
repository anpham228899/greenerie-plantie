package com.example.greenerieplantie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<ChatMessage> messageList;
    private String userAvatarUrl = null;

    public ChatAdapter(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    public void setUserAvatarUrl(String url) {
        this.userAvatarUrl = url;
        notifyDataSetChanged();
    }

    public void updateWelcomeMessage(String newMessage) {
        if (!messageList.isEmpty()) {
            messageList.set(0, new ChatMessage(newMessage, false));
            notifyItemChanged(0);
        }
    }

    public void addMessage(ChatMessage message) {
        messageList.add(message);
        notifyItemInserted(messageList.size() - 1);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);

        if (message.isUserMessage()) {
            // Message người dùng
            holder.userMessageContainer.setVisibility(View.VISIBLE);
            holder.chatbotMessageContainer.setVisibility(View.GONE);
            holder.userAvatar.setVisibility(View.VISIBLE);
            holder.chatbotAvatar.setVisibility(View.GONE);

            // Avatar người dùng (lấy từ Firebase)
            if (userAvatarUrl != null && !userAvatarUrl.isEmpty()) {
                Glide.with(holder.userAvatar.getContext())
                        .load(userAvatarUrl)
                        .placeholder(R.mipmap.ic_user)
                        .into(holder.userAvatar);
            } else {
                holder.userAvatar.setImageResource(R.mipmap.ic_user);
            }

            if (message.isImage()) {
                holder.userImage.setVisibility(View.VISIBLE);
                holder.userMessageText.setVisibility(View.GONE);
                holder.userImage.setImageURI(message.getImageUri());
            } else {
                holder.userImage.setVisibility(View.GONE);
                holder.userMessageText.setVisibility(View.VISIBLE);
                holder.userMessageText.setText(message.getText());
            }

        } else {
            // Message của chatbot
            holder.chatbotMessageContainer.setVisibility(View.VISIBLE);
            holder.userMessageContainer.setVisibility(View.GONE);
            holder.chatbotAvatar.setVisibility(View.VISIBLE);
            holder.userAvatar.setVisibility(View.GONE);
            holder.chatbotMessage.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView userMessageText, chatbotMessage;
        ImageView userImage, userAvatar, chatbotAvatar;
        LinearLayout userMessageContainer, chatbotMessageContainer;

        public ChatViewHolder(View view) {
            super(view);
            userMessageText = view.findViewById(R.id.tv_msg_user);
            chatbotMessage = view.findViewById(R.id.tv_msg_ai);
            userImage = view.findViewById(R.id.img_msg_user);
            userAvatar = view.findViewById(R.id.img_user_avatar);
            chatbotAvatar = view.findViewById(R.id.img_ai_avatar);
            userMessageContainer = view.findViewById(R.id.ll_msg_container_user);
            chatbotMessageContainer = view.findViewById(R.id.ll_msg_container_ai);
        }
    }
}
