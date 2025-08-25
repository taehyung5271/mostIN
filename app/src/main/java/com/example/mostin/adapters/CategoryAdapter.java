package com.example.mostin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mostin.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<String> {
    private final LayoutInflater inflater;
    private final List<String> items;
    
    public CategoryAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, R.layout.dropdown_item_category, objects);
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 드롭박스 겉모양은 기본 Android 레이아웃 사용
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        
        TextView textView = (TextView) convertView;
        textView.setText(items.get(position));
        
        // 첫 번째 아이템 ("선택")의 스타일 조정
        if (position == 0) {
            textView.setTextColor(getContext().getColor(android.R.color.darker_gray));
            textView.setAlpha(0.7f);
        } else {
            textView.setTextColor(getContext().getColor(android.R.color.black));
            textView.setAlpha(1.0f);
        }
        
        return convertView;
    }
    
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dropdown_item_category, parent, false);
        }
        
        TextView textView = convertView.findViewById(android.R.id.text1);
        View divider = convertView.findViewById(R.id.divider);
        
        textView.setText(items.get(position));
        
        // 드롭다운 아이템 스타일 설정
        if (position == 0) {
            textView.setTextColor(getContext().getColor(android.R.color.darker_gray));
            textView.setAlpha(0.7f);
        } else {
            textView.setTextColor(getContext().getColor(android.R.color.black));
            textView.setAlpha(1.0f);
        }
        
        // 마지막 아이템이면 구분선 숨기기
        if (position == items.size() - 1) {
            divider.setVisibility(View.GONE);
        } else {
            divider.setVisibility(View.VISIBLE);
        }
        
        return convertView;
    }
}