package com.example.myschedulingapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myschedulingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.DateFormat;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Objects;


public class UpdateFragment extends Fragment {

    private String key;

    private String task;
    private String description;

    private EditText mTask;
    private EditText mDescription;

    Button deleteBtn;
    Button updateBtn;

    private DatabaseReference reference;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        mTask = view.findViewById(R.id.et_task);
        mDescription = view.findViewById(R.id.et_description);

        deleteBtn = view.findViewById(R.id.btn_delete);
        updateBtn = view.findViewById(R.id.btn_update);

        task = NotesFragment.task;
        description = NotesFragment.description;
        key = NotesFragment.key;

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController controller = Navigation.findNavController(view);

        mTask.setText(task);
        mTask.setSelection(task.length());

        mDescription.setText(description);
        mDescription.setSelection(description.length());

        updateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                task = mTask.getText().toString().trim();
                description = mDescription.getText().toString().trim();

                String date = DateFormat.getDateInstance().format(new Date());

                Model model = new Model(task, description, key, date);

                reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Data updated", Toast.LENGTH_SHORT).show();
                        } else {
                            String error = task.getException().toString();
                            Toast.makeText(getActivity(), "Update Failed" + error, Toast.LENGTH_SHORT).show();
                        }
                       controller.navigate(R.id.action_updateFragment_to_notesFragment);
                    }
                });

            }

        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(key).removeValue().addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Task Deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(getActivity(), "Delete Failed"+error, Toast.LENGTH_SHORT).show();
                    }
                });

                controller.navigate(R.id.action_updateFragment_to_notesFragment);
            }
        });
    }

}
