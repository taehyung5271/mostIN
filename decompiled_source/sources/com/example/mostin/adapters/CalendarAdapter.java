package com.example.mostin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.models.DateModel;
import java.util.List;

/* loaded from: classes7.dex */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final List<DateModel> dates;

    public CalendarAdapter(List<DateModel> dates) {
        this.dates = dates;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        final DateModel date = this.dates.get(position);
        holder.textDay.setText(String.valueOf(date.getDay()));
        int columnIndex = position % 7;
        if (columnIndex == 0) {
            holder.textDay.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
        } else if (columnIndex == 6) {
            holder.textDay.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));
        } else {
            holder.textDay.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        }
        holder.textDay.setAlpha(date.isCurrentMonth() ? 1.0f : 0.4f);
        if (date.getClockInTime() != null) {
            holder.textClockIn.setText(date.getClockInTime());
            holder.textClockIn.setTextColor(-16776961);
        } else {
            holder.textClockIn.setText("");
        }
        if (date.getClockOutTime() != null) {
            holder.textClockOut.setText(date.getClockOutTime());
            holder.textClockOut.setTextColor(-16776961);
        } else {
            holder.textClockOut.setText("");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.adapters.CalendarAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Toast.makeText(view.getContext(), date.getDay() + "일 선택됨", 0).show();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dates.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView textClockIn;
        TextView textClockOut;
        TextView textDay;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            this.textDay = (TextView) itemView.findViewById(R.id.text_day);
            this.textClockIn = (TextView) itemView.findViewById(R.id.text_clock_in);
            this.textClockOut = (TextView) itemView.findViewById(R.id.text_clock_out);
        }
    }
}
