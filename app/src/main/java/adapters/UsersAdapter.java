package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blood_donation.R;
import com.example.blood_donation.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    List<User> userList;
    private Context context;
    private static OnCallButtonClickListener onCallButtonClickListener;
    private static OnDeleteButtonClickListener onDeleteButtonClickListener;

    public UsersAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private TextView bloodType;
        private TextView phone;
        private final Button callButton;
        private final Button deleteButton;
        private User user;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            bloodType = itemView.findViewById(R.id.blood_type);
            phone = itemView.findViewById(R.id.phone_num);
            callButton = itemView.findViewById(R.id.call_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        void setName(String value) {

            name.setText(value);
        }
        void setBloodType(String value) {
            bloodType.setText(value);
        } void setPhone(String value) {
            phone.setText(value);
        }
        void setUser(User user) {

            this.user = user;
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View listViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null);
        return new MyViewHolder(listViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.setName(user.name);
        holder.setPhone(user.phone);
        holder.setUser(user);
        holder.setBloodType(user.bloodType);
        holder.callButton.setOnClickListener(v -> {
            if (onCallButtonClickListener != null) {
                onCallButtonClickListener.onCallButtonClick(user);
            }
        });
        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteButtonClickListener != null) {
                onDeleteButtonClickListener.onCallButtonClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setOnCallButtonClickListener(OnCallButtonClickListener clickListener) {
        UsersAdapter.onCallButtonClickListener = clickListener;
    }
    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener clickListener) {
        UsersAdapter.onDeleteButtonClickListener = clickListener;
    }

    public interface OnCallButtonClickListener {
        void onCallButtonClick(User user);
    }
    public interface OnDeleteButtonClickListener {
        void onCallButtonClick(User user);
    }


}
