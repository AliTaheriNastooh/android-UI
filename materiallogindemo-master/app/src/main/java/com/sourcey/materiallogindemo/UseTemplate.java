
package com.sourcey.materiallogindemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class UseTemplate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    View view;
    int whichTemplate=-1;
    MyArrayList<TemplateOperation> templates;
    MyRecyclerViewAdapter adapter;
    MyArrayList<Operation> operations=new MyArrayList<Operation>();
    public UseTemplate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_use_template, container, false);
        ArrayList<String> zoom=loadString();
        NoDefaultSpinner spinner2 = (NoDefaultSpinner) view.findViewById(R.id.spinnerOfWhichTemplate);
        ArrayAdapter<String> adapter2 = new     ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,zoom);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2,"نام الگو مورد نظر را انتخاب کنید:");
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                whichTemplate=position;
                operations.clear();
                operations.addAll(templates.get(position).getOperations());
                adapter.notifyDataSetChanged();
               // numberOfChannel=String.valueOf(position+1);
            }


            public void onNothingSelected(AdapterView<?> parent) {
                whichTemplate=-1;
               // numberOfChannel=String.valueOf(-1);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewOfShowTemplate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyRecyclerViewAdapter(getActivity(), operations);
        // adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.my_divider));

        return view;
    }
    private ArrayList<String > loadString(){
        File cacheDir;
        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"MyCustomObject");
        else
            cacheDir= getActivity().getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

        MyArrayList<TemplateOperation> tmp=new MyArrayList<>();
        tmp=tmp.getObject(getActivity(),cacheDir,"operationsTemplate");

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
    public MyArrayList<Operation> getOperations(){
        return operations;
    }
}
