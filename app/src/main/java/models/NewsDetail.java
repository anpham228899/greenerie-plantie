package models;

import java.io.Serializable;
import java.util.List;

    public class NewsDetail implements Serializable {

        private String title;
        private String author;
        private String date;
        private List<NewsBlock> blocks;
        private String image; // đổi từ imageResId (int) sang image (String)

        public NewsDetail() {
        } // constructor rỗng

        public NewsDetail(String title, String author, String date, List<NewsBlock> blocks, String image) {
            this.title = title;
            this.author = author;
            this.date = date;
            this.blocks = blocks;
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getDate() {
            return date;
        }

        public List<NewsBlock> getBlocks() {
            return blocks;
        }

        public String getImage() {
            return image;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setBlocks(List<NewsBlock> blocks) {
            this.blocks = blocks;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFirstImageName() {
            for (NewsBlock block : blocks) {
                if ("IMAGE".equals(block.getType())) {
                    return block.getContent();
                }
            }
            return null;
        }
    }

