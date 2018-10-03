package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

public class ShowTemplateSetting extends AppCompatActivity {
    int whichTemplate=-1;
    MyArrayList<TemplateOperation> templates;
    MyRecyclerViewAdapter adapter;
    MyArrayList<Operation> operations=new MyArrayList<Operation>();
    File cacheDir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_template_setting);
        final Toolbar toolbar = findViewById(R.id.toolbarInShowTemplate);
        Button deletTemplats=findViewById(R.id.buttonOfDeleteTemplate);
        deletTemplats.setVisibility(View.INVISIBLE);
        Button renameTemplats=findViewById(R.id.buttonOfRenameTemplate);
        renameTemplats.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<String> zoom=loadString();
        NoDefaultSpinner spinner2 = (NoDefaultSpinner) findViewById(R.id.spinnerOfWhichTemplateInSetting);
        //spinner2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        ArrayAdapter<String> adapter2 = new     ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,zoom);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2,"نام الگو مورد نظر را انتخاب کنید:");
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                whichTemplate=position;
                operations.clear();
                operations.addAll(templates.get(position).getOperations());
                adapter.notifyDataSetChanged();
                Button deletTemplats=findViewById(R.id.buttonOfDeleteTemplate);
                deletTemplats.setVisibility(View.VISIBLE);
                Button renameTemplats=findViewById(R.id.buttonOfRenameTemplate);
                renameTemplats.setVisibility(View.VISIBLE);
                // numberOfChannel=String.valueOf(position+1);
            }


            public void onNothingSelected(AdapterView<?> parent) {
                whichTemplate=-1;
                Button deletTemplats=findViewById(R.id.buttonOfDeleteTemplate);
                deletTemplats.setVisibility(View.INVISIBLE);
                Button renameTemplats=findViewById(R.id.buttonOfRenameTemplate);
                renameTemplats.setVisibility(View.INVISIBLE);
                // numberOfChannel=String.valueOf(-1);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerViewOfShowTemplateInSetting);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowTemplateSetting.this));
        adapter = new MyRecyclerViewAdapter(ShowTemplateSetting.this, operations);
        // adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.my_divider));

    }

    public void deleteTemplateFromList(View view){
        templates.remove(whichTemplate);
        saveTemplate();
    }
    public void renameTemplate(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShowTemplateSetting.this);
        alertDialog.setMessage("نام جدید قالب را وارد کنید");
        final EditText input = new EditText(ShowTemplateSetting.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("تایید",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /////////
                        if(!input.getText().toString().isEmpty()){
                            templates.get(whichTemplate).setName(input.getText().toString());
                            saveTemplate();
                            dialog.cancel();
                            finish();
                        }
                    }
                });

        alertDialog.setNegativeButton("انصراف",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
    public void saveTemplate(){
        templates.saveObject(templates,cacheDir,"operationsTemplate");
        finish();
    }
    private ArrayList<String > loadString(){
        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"MyCustomObject");
        else
            cacheDir= getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

        MyArrayList<TemplateOperation> tmp=new MyArrayList<>();
        tmp=tmp.getObject(getApplicationContext(),cacheDir,"operationsTemplate");

        if (tmp!=null){
            templates=tmp;
            ArrayList<String> myArray=new ArrayList<>();
            for (int i=0;i<tmp.size();i++){
                myArray.add(tmp.get(i).getName());
            }
            return myArray;
        }
        return new ArrayList<String>();
    }
}
