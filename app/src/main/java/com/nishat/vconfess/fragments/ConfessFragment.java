package com.nishat.vconfess.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nishat.vconfess.R;
import com.nishat.vconfess.models.Confession;
import com.nishat.vconfess.models.User;

import java.util.Stack;


public class ConfessFragment extends Fragment {

    private EditText confessionEditText;
    private Spinner confessionTypeSpinner;
    private FloatingActionButton addConfessionButton;
    private CoordinatorLayout confessCoordinatorLayout;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ArrayAdapter adapter;
    private Context context;

    public ConfessFragment() {
    }

    public static ConfessFragment newInstance() {
        ConfessFragment fragment = new ConfessFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        final String[] confessionType = {"Select your confession type",
                "Love confession",
                "Guilt confession",
                "Deed confession",
                "College confession"};

        adapter = new ArrayAdapter(context , R.layout.spinner_item, confessionType);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_confess, container, false);

        confessionEditText = (EditText) view.findViewById(R.id.confessionEditText);
        confessionTypeSpinner = (Spinner) view.findViewById(R.id.confessionTypeSpinner);
        addConfessionButton = (FloatingActionButton) view.findViewById(R.id.addConfessionButton);
        confessCoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.confessCoordinatorLayout);

        confessionTypeSpinner.setAdapter(adapter);

        addConfessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String confessionText = confessionEditText.getText().toString();
                final String confessionType = confessionTypeSpinner.getSelectedItem().toString();
                final long timestamp = System.currentTimeMillis();
                if(!TextUtils.isEmpty(confessionText) && !TextUtils.isEmpty(confessionType) && !confessionType.equals("Select your confession type")){
                    databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            String tag = user.getYear() + "_" + user.getDepartment() + "_" + user.getDivision() + "_" + user.getGender();
                            Confession confession = new Confession(confessionText, confessionType, timestamp, tag);
                            databaseReference.child(firebaseUser.getUid()).child("confession").push().setValue(confession);
                            confessionEditText.setText("");
                            Snackbar.make(confessCoordinatorLayout, "Great!!! You confessed", Snackbar.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Snackbar.make(confessCoordinatorLayout, "Select all the fields", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}