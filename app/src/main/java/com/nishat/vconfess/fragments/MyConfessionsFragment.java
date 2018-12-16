package com.nishat.vconfess.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nishat.vconfess.R;
import com.nishat.vconfess.adapters.ConfessionListAdapter;
import com.nishat.vconfess.models.Confession;
import com.nishat.vconfess.models.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MyConfessionsFragment extends Fragment {

    private RecyclerView myConfessionsRecyclerView;
    private TextView noConfessionTextView;

    private List<User> users;
    private ConfessionListAdapter adapter;
    private Context context;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    public MyConfessionsFragment() {
    }
    public static MyConfessionsFragment newInstance() {
        MyConfessionsFragment fragment = new MyConfessionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference("users").child(firebaseUser.getUid().toString());
        databaseReference.keepSynced(true);
        users = new ArrayList<User>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_confessions, container, false);

        myConfessionsRecyclerView = (RecyclerView) view.findViewById(R.id.myConfessionsRecyclerView);
        noConfessionTextView = (TextView) view.findViewById(R.id.noConfessionTextView);

        adapter = new ConfessionListAdapter(context, users);
        myConfessionsRecyclerView.setAdapter(adapter);
        myConfessionsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                fetchData(dataSnapshot);
                sortDataAccordingToTimeStamp();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        if(dataSnapshot.hasChild("confession")) {
            User tempUser = new User();
            tempUser = dataSnapshot.getValue(User.class);
            for (Map.Entry map : tempUser.getConfession().entrySet()){
                User user = new User();
                user.setDepartment(tempUser.getDepartment());
                user.setDivision(tempUser.getDivision());
                user.setGender(tempUser.getGender());
                user.setYear(tempUser.getYear());
                user.setUid(tempUser.getUid());
                Confession confession = (Confession) map.getValue();
                user.setConfess(confession);
                users.add(user);
            }
        } else {
            if (noConfessionTextView.getVisibility() == View.GONE){
                noConfessionTextView.setVisibility(View.VISIBLE);
            }
            if (myConfessionsRecyclerView.getVisibility() == View.VISIBLE){
                myConfessionsRecyclerView.setVisibility(View.GONE);
            }
        }
    }

    private void sortDataAccordingToTimeStamp(){
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o2.getConfess().getTimestamp(), o1.getConfess().getTimestamp());
            }
        });
    }
}
