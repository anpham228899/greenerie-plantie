package models;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private String origin;
    private String category;
    private String price;
    private int imageResId;

    public Product(String name, String origin, String category, String price, int imageResId) {
        this.name = name;
        this.origin = origin;
        this.category = category;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public static class Discount {
        private String code;
        private double discountPercentage;

        // Constructor để khởi tạo mã giảm giá và tỷ lệ giảm giá
        public Discount(String code, double discountPercentage) {
            this.code = code;
            this.discountPercentage = discountPercentage;
        }

        // Getter để lấy mã giảm giá
        public String getCode() {
            return code;
        }

        // Getter để lấy tỷ lệ giảm giá
        public double getDiscountPercentage() {
            return discountPercentage;
        }

        private static List<Discount> discountList = new ArrayList<>();

        public static void addDiscountCode(String code, double discountPercentage) {
            discountList.add(new Discount(code, discountPercentage));
        }

        // Lấy danh sách tất cả mã giảm giá
        public static List<Discount> getDiscountCodes() {
            return discountList;
        }

        // Kiểm tra mã giảm giá có hợp lệ hay không
        public static Discount getDiscountByCode(String code) {
            for (Discount discount : discountList) {
                if (discount.getCode().equalsIgnoreCase(code)) {
                    return discount;
                }
            }
            return null;
        }

        public static void initDiscounts() {
            addDiscountCode("DISCOUNT10", 10);
            addDiscountCode("DISCOUNT20", 20);
            addDiscountCode("DISCOUNT50", 50);
        }
    }
}
