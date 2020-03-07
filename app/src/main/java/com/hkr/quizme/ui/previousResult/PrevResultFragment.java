package com.hkr.quizme.ui.previousResult;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hkr.quizme.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class PrevResultFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView textStatus;
    private LinkedList<Result> results;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_prev_result, container, false);
        textStatus = root.findViewById(R.id.text_status_prev_result);
        recyclerView = root.findViewById(R.id.recyclerView_prev_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PrevResultAdapter prevResultAdapter = new PrevResultAdapter(getContext(), loadResult());
        if (results.size() == 0) {
            textStatus.setText("No Previous result to show");
        } else {

        }
        prevResultAdapter.setHasStableIds(true);
        recyclerView.setAdapter(prevResultAdapter);

        return root;
    }


    public LinkedList<Result> loadResult() {
        results = new LinkedList<>();

        ObjectInputStream input;
        String filename = "PREVRESULT.srl";

        try {
            input = new ObjectInputStream(new FileInputStream(new File(new File(getContext().getFilesDir(), "") + File.separator + filename)));
            while (true) {
                results.add((Result) input.readObject());
            }

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Collections.reverse(results);
        return results;
    }

}