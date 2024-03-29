package com.example.loginapp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.FireStoreActivity;
import com.example.loginapp.FireStoreShowActivity;
import com.example.loginapp.R;
import com.example.loginapp.model.FireStoreModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FireStoreAdapter extends RecyclerView.Adapter<FireStoreAdapter.MyViewHolder> {
    private FireStoreShowActivity activity;
    private List<FireStoreModel> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FireStoreAdapter(FireStoreShowActivity activity, List<FireStoreModel> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    // this method will pass data to FireStoreActivity
    public void updateData(int position) {
        FireStoreModel item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uTitle", item.getTitle());
        bundle.putString("uDescription", item.getDescription());
        Intent intent = new Intent(activity, FireStoreActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position) {
        FireStoreModel item = mList.get(position);
        db.collection("Documents").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_firestore, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getTitle());
        holder.description.setText(mList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_text);
            description = itemView.findViewById(R.id.description_text);
        }
    }
}
