package com.example.poster.firebaserealtimedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recucleView;
    private List<UserModel> result;
    private UserAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        emptyText = (TextView) findViewById(R.id.text_no_data);

        result = new ArrayList<>();

        recucleView = (RecyclerView) findViewById(R.id.user_list);
        recucleView.setHasFixedSize(true);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);

        recucleView.setLayoutManager(lim);

        adapter = new UserAdapter(result);
        recucleView.setAdapter(adapter);

        updateList();
        checkIfEmpty();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                removeUser(item.getGroupId());
                break;
            case 1:
                changeUser(item.getGroupId());
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void updateList(){
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                result.add(dataSnapshot.getValue(UserModel.class));
                adapter.notifyDataSetChanged();
                checkIfEmpty();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                UserModel model = dataSnapshot.getValue(UserModel.class);
                int index = getItemIndex(model);

                result.set(index, model);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                UserModel model = dataSnapshot.getValue(UserModel.class);
                int index = getItemIndex(model);

                result.remove(index);
                adapter.notifyItemRemoved(index);
                checkIfEmpty();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getItemIndex(UserModel user){
        int index = -1;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).key.equals(user.key)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void removeUser(int position){
        reference.child(result.get(position).key).removeValue();
    }

    private void changeUser(int position){
        UserModel user = result.get(position);
        user.age = 100;

        Map<String, Object> userValues = user.toMap();
        Map<String, Object> newUser = new HashMap<>();

        newUser.put(user.key, userValues);
        reference.updateChildren(newUser);
    }

    private void checkIfEmpty(){
        if (result.size() == 0){
            recucleView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        }else {
            recucleView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.INVISIBLE);
        }
    }
}
