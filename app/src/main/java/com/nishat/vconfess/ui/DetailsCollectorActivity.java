package com.nishat.vconfess.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nishat.vconfess.R;
import com.nishat.vconfess.models.User;

public class DetailsCollectorActivity extends AppCompatActivity {

    private FloatingActionButton saveFab;
    private RadioGroup departmentRadioGroup;
    private RadioGroup yearRadioGroup;
    private RadioGroup divisionRadioGroup;
    private RadioGroup genderRadioGroup;
    private RadioButton departmentRadioButton;
    private RadioButton yearRadioButton;
    private RadioButton divisionRadioButton;
    private RadioButton genderRadioButton;
    private CoordinatorLayout detailsCoordinatorLayout;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersNodeReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_collector);

        saveFab = (FloatingActionButton) findViewById(R.id.saveFab);
        departmentRadioGroup = (RadioGroup) findViewById(R.id.departmentRadioGroup);
        yearRadioGroup = (RadioGroup) findViewById(R.id.yearRadioGroup);
        divisionRadioGroup = (RadioGroup) findViewById(R.id.divisionRadioGroup);
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        detailsCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.detailsCoordinatorLayout);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sit back and relax...");
        progressDialog.setCancelable(false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        usersNodeReference = firebaseDatabase.getReference("users");

        if(firebaseUser != null){
            startActivity(new Intent(DetailsCollectorActivity.this, UserMainActivity.class));
            finish();
        }


        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                int departmentId = departmentRadioGroup.getCheckedRadioButtonId();
                int yearId = yearRadioGroup.getCheckedRadioButtonId();
                int divisionId = divisionRadioGroup.getCheckedRadioButtonId();
                int genderId = genderRadioGroup.getCheckedRadioButtonId();

                departmentRadioButton = (RadioButton) findViewById(departmentId);
                yearRadioButton = (RadioButton) findViewById(yearId);
                divisionRadioButton = (RadioButton) findViewById(divisionId);
                genderRadioButton = (RadioButton) findViewById(genderId);

                if (departmentRadioButton != null &&
                        yearRadioButton != null &&
                        divisionRadioButton != null &&
                        genderRadioButton != null) {

                    final String department = departmentRadioButton.getText().toString();
                    final String year = yearRadioButton.getText().toString();
                    final String division = divisionRadioButton.getText().toString();
                    final String gender = genderRadioButton.getText().toString().equals("Male") ? "boy" : "girl";

                    firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(DetailsCollectorActivity.this, UserMainActivity.class));
                                //user = new User(department, year, division, gender, null);
                                User user = new User();
                                user.setDepartment(department);
                                user.setYear(year);
                                user.setDivision(division);
                                user.setGender(gender);
                                user.setUid(firebaseAuth.getCurrentUser().getUid().toString());
                                usersNodeReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                                progressDialog.dismiss();
                                finish();
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Snackbar.make(detailsCoordinatorLayout, "Select all the fields", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
