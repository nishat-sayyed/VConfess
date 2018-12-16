package com.nishat.vconfess.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nishat.vconfess.R;
import com.nishat.vconfess.models.User;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Nishat on 9/23/2017.
 */

public class ConfessionListAdapter extends RecyclerView.Adapter<ConfessionListAdapter.MyViewHolder> {

    private Context context;
    private List<User> users;

    public ConfessionListAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindViewHolder(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView infoTextView;
        private TextView genderTextView;
        private TextView confessionTextView;
        private TextView timeTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            infoTextView = (TextView) itemView.findViewById(R.id.infoTextView);
            genderTextView = (TextView) itemView.findViewById(R.id.genderTextView);
            confessionTextView = (TextView) itemView.findViewById(R.id.confessionTextView);
            timeTextView = (TextView) itemView.findViewById(R.id.timeTextView);
        }

        public void bindViewHolder(User user){
            infoTextView.setText(user.getYear() + " " + user.getDepartment() + " " + user.getDivision());
            genderTextView.setText("A " + user.getGender() + " confessed...");
            confessionTextView.setText(user.getConfess().getConfession());
            timeTextView.setText(getFormattedTime(user.getConfess().getTimestamp()));
        }

        public String getFormattedTime(long timestamp){
            PrettyTime prettyTime = new PrettyTime();
            String formattedTime = prettyTime.format(new Date(timestamp));
            return formattedTime;
        }
    }

}
