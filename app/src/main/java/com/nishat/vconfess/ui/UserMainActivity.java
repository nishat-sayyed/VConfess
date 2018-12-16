package com.nishat.vconfess.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nishat.vconfess.R;
import com.nishat.vconfess.fragments.AllConfessionsFragment;
import com.nishat.vconfess.fragments.ConfessFragment;
import com.nishat.vconfess.fragments.MyConfessionsFragment;

public class UserMainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private AllConfessionsFragment allConfessionsFragment = AllConfessionsFragment.newInstance();
    private Fragment selectedFragment = allConfessionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        LoginTypeSelectActivity.loginTypeSelectActivity.finish();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, AllConfessionsFragment.newInstance());
        transaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectedFragment = allConfessionsFragment;

                switch (item.getItemId()) {
                    case R.id.allConfessions:
                        selectedFragment = allConfessionsFragment;
                        break;
                    case R.id.confess:
                        selectedFragment = ConfessFragment.newInstance();
                        break;
                    case R.id.profile:
                        selectedFragment = MyConfessionsFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, selectedFragment);
                transaction.commit();
                return true;
            }
        };

        navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logoutButton){
            logoutUserAnonymously();
        }
        if (item.getItemId() == R.id.filterList){
            startActivityForResult(new Intent(UserMainActivity.this, FilterListActivity.class), REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectedFragment = allConfessionsFragment;
        selectedFragment.onActivityResult(requestCode, resultCode, data);
    }

    public void logoutUserAnonymously(){
        firebaseUser.delete();
//        databaseReference.child(firebaseUser.getUid()).removeValue();
        firebaseAuth.signOut();
        startActivity(new Intent(UserMainActivity.this, LoginTypeSelectActivity.class));
        finish();
    }

}
