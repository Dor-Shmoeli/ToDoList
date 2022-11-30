package com.example.myschedulingapp.ui.main;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myschedulingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;


public class InputFragment extends Fragment {

    private EditText task;
    private EditText description;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;

    private ProgressDialog loader;

    Button save;
    Button cancel;

    public InputFragment() {
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
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        task = view.findViewById(R.id.task);
        description = view.findViewById(R.id.description);
        save = view.findViewById(R.id.btn_save);
        cancel = view.findViewById(R.id.btn_cancel);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);

        loader = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController controller = Navigation.findNavController(view);

        cancel.setOnClickListener(v -> {
            controller.navigate(R.id.action_inputFragment_to_notesFragment);
        });
        save.setOnClickListener(v -> {
            String mTask = task.getText().toString().trim();
            String mDescription = description.getText().toString().trim();
            String id = reference.push().getKey();
            String date = DateFormat.getDateInstance().format(new Date());

            if (TextUtils.isEmpty(mTask)) {
                task.setError("Task is empty");
                return;
            } else if (TextUtils.isEmpty(mDescription)) {
                description.setError("Description is empty");
                return;
            } else {
                loader.setMessage("Adding your data");
                loader.setCanceledOnTouchOutside(false);
                loader.show();

                Model model = new Model(mTask, mDescription, id, date);
                reference.child(id).setValue(model).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(getActivity(), "Task inserted ", Toast.LENGTH_SHORT).show();
                    } else {
                        String error = task1.getException().toString();
                        Toast.makeText(getActivity(), "Failed: " + error, Toast.LENGTH_SHORT).show();
                    }
                    loader.dismiss();
                });
            }

            controller.navigate(R.id.action_inputFragment_to_notesFragment);
        });
    }


}