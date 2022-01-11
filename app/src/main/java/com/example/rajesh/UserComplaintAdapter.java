package com.example.rajesh;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.rajesh.MainActivity.globalusername;

public class UserComplaintAdapter extends RecyclerView.Adapter<UserComplaintAdapter.ViewHolder> {
    List<UserComplaintModel> userComplaintModelList;
    Context context;
    FirebaseDatabase database;

    public UserComplaintAdapter(List<UserComplaintModel> userComplaintModelList, Context context) {
        this.userComplaintModelList = userComplaintModelList;
        this.context = context;
        database = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public UserComplaintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usercomplaint,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.complaintdate.setText("Date : "+userComplaintModelList.get(position).getDate());
        holder.complaintype.setText("Type : "+userComplaintModelList.get(position).getType());
        holder.complaintstatus.setText("Source : "+userComplaintModelList.get(position).getStatus());
        holder.complaintId.setText("ID : "+userComplaintModelList.get(position).getComplaintid());
        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("users").child(globalusername).child("complaintslist")
                        .child(userComplaintModelList.get(position).getComplaintid()).removeValue();
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                Intent deleteafter = new Intent();
                deleteafter.setClass(context,HomePage.class);
                context.startActivity(deleteafter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userComplaintModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView complaintId,complaintype,complaintstatus,complaintdate;
        Button deletebutton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintdate = itemView.findViewById(R.id.complaintDATE);
            complaintId = itemView.findViewById(R.id.complaintID);
            complaintstatus = itemView.findViewById(R.id.complaintSTATUS);
            complaintype = itemView.findViewById(R.id.complaintTYPE);
            deletebutton = itemView.findViewById(R.id.complaintDELETE);
        }
    }
}
