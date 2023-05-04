package com.example.busticketinguser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class Add_Money extends AppCompatActivity {

    private EditText amount_field;
    private TextView label;
    private Button add_money_button;
    private FirebaseUser mauth;


    private DatabaseReference InsertMoney;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
//        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
//       String  currentusers =
        mauth = FirebaseAuth.getInstance().getCurrentUser();

        String User = mauth.getUid();


        amount_field = findViewById(R.id.amount_field);
        add_money_button = findViewById(R.id.add_money_button);
        label = findViewById(R.id.amount_label);



        InsertMoney = FirebaseDatabase.getInstance("https://ebus-3067a-default-rtdb.firebaseio.com/").getReference("Wallet").child(User).push();
//        .getReference("Users").child(currentusers);




        add_money_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMoneyData();

            }
        });

    }

    private void AddMoneyData(){

        String Money = amount_field.getText().toString();

        NewAddMoney newaddmoney = new NewAddMoney(Money);

        InsertMoney.push().setValue(newaddmoney);
        InsertMoney.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String money = (String) dataSnapshot.child("money").getValue();
                        Toast.makeText(Add_Money.this, money, Toast.LENGTH_SHORT).show();
                Toast.makeText(Add_Money.this, "Money Added", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


}


}