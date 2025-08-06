package com.naver.maps.map.style.types;

import com.naver.maps.map.internal.util.b;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class FormattedSection {
    private Number fontScale;
    private String[] fontStack;
    private final String text;
    private String textColor;

    public FormattedSection(String text) {
        this(text, null, null, null);
    }

    public FormattedSection(String text, Number fontScale, String[] fontStack, String textColor) {
        this.text = text;
        this.fontScale = fontScale;
        this.fontStack = fontStack;
        this.textColor = textColor;
    }

    @Deprecated
    public FormattedSection(String text, Number fontScale, String[] fontStack) {
        this(text, fontScale, fontStack, null);
    }

    @Deprecated
    public FormattedSection(String text, Number fontScale) {
        this(text, fontScale, null, null);
    }

    @Deprecated
    public FormattedSection(String text, String[] fontStack) {
        this(text, null, fontStack, null);
    }

    public String getText() {
        return this.text;
    }

    public Number getFontScale() {
        return this.fontScale;
    }

    public String[] getFontStack() {
        return this.fontStack;
    }

    public String getTextColor() {
        return this.textColor;
    }

    public void setFontScale(Number fontScale) {
        this.fontScale = fontScale;
    }

    public void setFontStack(String[] fontStack) {
        this.fontStack = fontStack;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = b.a(textColor);
    }

    void setTextColor(Object textColor) {
        setTextColor((String) textColor);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FormattedSection formattedSection = (FormattedSection) o;
        if (!this.text.equals(formattedSection.text)) {
            return false;
        }
        if (this.fontScale == null ? formattedSection.fontScale != null : !this.fontScale.equals(formattedSection.fontScale)) {
            return false;
        }
        if (!Arrays.equals(this.fontStack, formattedSection.fontStack)) {
            return false;
        }
        if (this.textColor != null) {
            return this.textColor.equals(formattedSection.textColor);
        }
        if (formattedSection.textColor == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((this.text.hashCode() * 31) + (this.fontScale != null ? this.fontScale.hashCode() : 0)) * 31) + Arrays.hashCode(this.fontStack)) * 31) + (this.textColor != null ? this.textColor.hashCode() : 0);
    }

    Object[] toArray() {
        HashMap map = new HashMap();
        map.put("font-scale", this.fontScale);
        map.put("text-font", this.fontStack);
        map.put("text-color", this.textColor);
        return new Object[]{this.text, map};
    }

    public String toString() {
        return "FormattedSection{text='" + this.text + "', fontScale=" + this.fontScale + ", fontStack=" + Arrays.toString(this.fontStack) + ", textColor='" + this.textColor + "'}";
    }
}
