package com.naver.maps.map.style.types;

import java.util.Arrays;

/* loaded from: classes.dex */
public class Formatted {
    private final FormattedSection[] formattedSections;

    public Formatted(FormattedSection... formattedSections) {
        this.formattedSections = formattedSections;
    }

    public FormattedSection[] getFormattedSections() {
        return this.formattedSections;
    }

    public Object[] toArray() {
        Object[] objArr = new Object[this.formattedSections.length];
        for (int i = 0; i < this.formattedSections.length; i++) {
            objArr[i] = this.formattedSections[i].toArray();
        }
        return objArr;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(this.formattedSections, ((Formatted) o).formattedSections);
    }

    public int hashCode() {
        return Arrays.hashCode(this.formattedSections);
    }

    public String toString() {
        return "Formatted{formattedSections=" + Arrays.toString(this.formattedSections) + '}';
    }
}
