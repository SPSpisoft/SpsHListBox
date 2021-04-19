package com.spisoft.spshlistbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.spisoft.splateralbox.SpLateBox;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpLateBox spLateBoxAdd = findViewById(R.id.splatboxAdd);
        spLateBoxAdd.SetHeadSrc(R.drawable.ic_baseline_location_on_24);

        LayoutInflater inflater0 = LayoutInflater.from(MainActivity.this);
        spLateBoxAdd.AddView(inflater0.inflate(R.layout.test_view, null));

        spLateBoxAdd.setOnAddClickTaskListener(new SpLateBox.OnAddClickTaskListener() {
            @Override
            public void onEvent(boolean editMode) {
                Toast.makeText(MainActivity.this, "SAVE ", Toast.LENGTH_SHORT).show();
            }
        });

        SpLateBox spLateBoxTel = findViewById(R.id.splatboxTel);
        spLateBoxTel.SetHeadSrc(R.drawable.ic_baseline_phone_enabled_24);

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.test_view, null);
        spLateBoxTel.AddView(inflatedLayout);
        EditText editText = inflatedLayout.findViewById(R.id.editTextNumber);
        Button button = inflatedLayout.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, editText.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        TestListAdapter testListAdapter = new TestListAdapter();
        List<ClsTest> mList = new ArrayList<>();
        mList.add(new ClsTest("1", "Title1", true));
        mList.add(new ClsTest("2", "Title2", false));
        mList.add(new ClsTest("3", "Title3", true));
        testListAdapter.updateList(mList);
        spLateBoxTel.SetList(testListAdapter);

        spLateBoxTel.setOnAddClickTaskListener(new SpLateBox.OnAddClickTaskListener() {
            @Override
            public void onEvent(boolean editMode) {
                if(editMode) {
                    Snackbar.make(spLateBoxTel, "CANCEL", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spLateBoxTel.setMode(false);
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }else {
                    editText.setText("");
                }
            }
        });

        spLateBoxTel.setOnEditClickTaskListener(new SpLateBox.OnEditClickTaskListener() {
            @Override
            public void onEvent(boolean editMode, int currentPosition) {
                if(!editMode){
                    editText.setText(mList.get(currentPosition).getOptTitle());
                }else {
                    Snackbar.make(spLateBoxTel, "SAVE", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spLateBoxTel.setMode(false);
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
            }
        });

    }
}