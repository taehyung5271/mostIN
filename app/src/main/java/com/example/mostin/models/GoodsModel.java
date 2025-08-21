package com.example.mostin.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class GoodsModel {
    @JsonAdapter(Category.CategoryAdapter.class)
    public enum Category {
        IMPORTED_BEER("수입맥주"),
        DOMESTIC_BEER("국산맥주"),
        NON_ALCOHOL_BEER("무알코올맥주");

        private final String displayName;

        Category(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static class CategoryAdapter extends TypeAdapter<Category> {
            @Override
            public void write(JsonWriter out, Category value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.name()); // IMPORTED_BEER, DOMESTIC_BEER, NON_ALCOHOL_BEER
                }
            }

            @Override
            public Category read(JsonReader in) throws IOException {
                if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String value = in.nextString();
                try {
                    return Category.valueOf(value);
                } catch (IllegalArgumentException e) {
                    // 알 수 없는 값이 오면 null 반환
                    return null;
                }
            }
        }
    }

    @SerializedName("barcode")
    private String barcode;
    @SerializedName("goodsName")
    private String name;
    @SerializedName("category")
    private Category category;

    public GoodsModel(String barcode, String name) {
        this.barcode = barcode;
        this.name = name;
    }

    public GoodsModel(String barcode, String name, Category category) {
        this.barcode = barcode;
        this.name = name;
        this.category = category;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
} 