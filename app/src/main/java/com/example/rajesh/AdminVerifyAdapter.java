package com.example.rajesh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminVerifyAdapter extends RecyclerView.Adapter<AdminVerifyAdapter.ViewHolder>{
    Context context;
    FirebaseDatabase database;
    List<AdminComplaintModel> adminComplaintStatusModelList;

    public AdminVerifyAdapter(Context context, List<AdminComplaintModel> adminComplaintStatusModels) {
        this.context = context;
        this.adminComplaintStatusModelList = adminComplaintStatusModels;
        database = FirebaseDatabase.getInstance();
    }


    @NonNull
    @Override
    public AdminVerifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminverify,parent,false);
        return new AdminVerifyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdminComplaintModel adminComplaintStatusModel = adminComplaintStatusModelList.get(position);

        holder.date.setText("Date : "+adminComplaintStatusModel.getDate());
        holder.type.setText("Type : "+adminComplaintStatusModel.getType());
        holder.desc.setText("Description : "+adminComplaintStatusModel.getDescription());
        holder.status.setText("Status : "+adminComplaintStatusModel.getStatus());
        holder.id.setText("Id : "+adminComplaintStatusModel.getComplaintid());
        holder.name.setText("Name : "+adminComplaintStatusModel.getUsername());
        String imageURL = adminComplaintStatusModel.getImageURL();
        Picasso.get().load(imageURL).into(holder.imageView);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Confirmation Message");
                alert.setMessage("Do you to Accept complaint ? ");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // deleting from user complaints list
                        database.getReference("complaints")
                                .child(adminComplaintStatusModel.getComplaintid())
                                .child("status").setValue("Verified");

                        //deleting from police database
                        database.getReference().child("users")
                                .child(adminComplaintStatusModel.getUsername())
                                .child("complaintslist")
                                .child(adminComplaintStatusModel.getComplaintid())
                                .child("status")
                                .setValue("Verified");

                        Toast.makeText(context, "Verification Done", Toast.LENGTH_LONG).show();
                        Intent verifafter = new Intent();
                        verifafter.setClass(context,AdminHomePage.class);
                        context.startActivity(verifafter);

                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
                    }
                });
                alert.create().show();
            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Confirmation Message");
                alert.setMessage("Do you to Reject complaint ? ");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // deleting from user complaints list
                        database.getReference("complaints")
                                .child(adminComplaintStatusModel.getComplaintid())
                                .child("status").setValue("Rejected");

                        //deleting from police database
                        database.getReference().child("users")
                                .child(adminComplaintStatusModel.getUsername())
                                .child("complaintslist")
                                .child(adminComplaintStatusModel.getComplaintid())
                                .child("status")
                                .setValue("Rejected");

                        Toast.makeText(context, "Rejection Done", Toast.LENGTH_LONG).show();
                        Intent rejectafter = new Intent();
                        rejectafter.setClass(context,AdminHomePage.class);
                        context.startActivity(rejectafter);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
                    }
                });
                alert.create().show();
            }
        });

        holder.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference("complaints")
                        .child(adminComplaintStatusModel.getComplaintid())
                        .removeValue();
                Toast.makeText(context, "Deletion Done", Toast.LENGTH_LONG).show();
                Intent deleter = new Intent();
                deleter.setClass(context,AdminHomePage.class);
                context.startActivity(deleter);
            }
        });
    }


    @Override
    public int getItemCount() {
        return adminComplaintStatusModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,date,type,desc,status,id;
        Button button,button2,button3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.compaintimage);
            name = itemView.findViewById(R.id.admincompusername);
            date = itemView.findViewById(R.id.admincompdate);
            type = itemView.findViewById(R.id.admincomptype);
            desc = itemView.findViewById(R.id.admincompdesc);
            status = itemView.findViewById(R.id.admincompstatus);
            id=itemView.findViewById(R.id.admincompid);
            button = itemView.findViewById(R.id.admincompaccept);
            button2 = itemView.findViewById(R.id.admincompreject);
            button3 = itemView.findViewById(R.id.admincompdelete);
        }
    }
}
