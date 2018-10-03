package com.sourcey.materiallogindemo;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class showLog extends AppCompatActivity implements ActionMode.Callback{
    MyRecyclerViewAdapter adapter;
    ArrayList<String> nameArray=new ArrayList<String>();
    MyArrayList<Operation> operations=new MyArrayList<Operation>();
    MyArrayList<Operation> alloperations=new MyArrayList<Operation>();
    private boolean isMultiSelect = false;
    private List<String> selectedIds = new ArrayList<>();
    private ActionMode actionMode;
    public File cacheDir;
    ArrayList<String> deviceName;
    ArrayList<String> operationName;
    ArrayList<String> statusName;
    private int whichDevice=-1;
    private int whichoperation=-1;
    private int whichstatus=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);

        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"MyCustomObject");
        else
            cacheDir= getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOfShowLog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, operations);
        // adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        final Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.my_divider));
        recyclerView.addItemDecoration(itemDecorator);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect){
                    //if multiple selection is enabled then select item on single click else perform normal click on item.
                    multiSelect(position);
                }
            }
            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect){
                    selectedIds = new ArrayList<>();
                    isMultiSelect = true;

                    if (actionMode == null){
                        actionMode = startActionMode((android.view.ActionMode.Callback) showLog.this); //show ActionMode.
                    }
                }

                multiSelect(position);
            }
        }));
        loadContent();
        /////////////////////////////////////

        deviceName=loadStringOfDevice();
        NoDefaultSpinner spinner2 = (NoDefaultSpinner) findViewById(R.id.spinnerOfDeviceFilter);
        ArrayAdapter<String> adapter2 = new     ArrayAdapter<String>(showLog.this, android.R.layout.simple_spinner_item,deviceName);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2,"دستگاه");
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                whichDevice=position;
                filterDevice();
//                whichTemplate=position;
//                operations.clear();
//                operations.addAll(templates.get(position).getOperations());
//                adapter.notifyDataSetChanged();
                // numberOfChannel=String.valueOf(position+1);
            }


            public void onNothingSelected(AdapterView<?> parent) {
                whichDevice=-1;
                filterDevice();
//                whichTemplate=-1;
                // numberOfChannel=String.valueOf(-1);
            }
        });

        ///////////////////////////////////////

        operationName=new ArrayList<>();
        operationName.add("بستن کانال");
        operationName.add("باز کردن کانال");
        operationName.add("تنظیمات دستگاه");
        operationName.add("گرفتن گزارش کانال");
        operationName.add("همه");
        NoDefaultSpinner spinner3 = (NoDefaultSpinner) findViewById(R.id.spinnerOfOperationFilter);
        ArrayAdapter<String> adapter3 = new     ArrayAdapter<String>(showLog.this, android.R.layout.simple_spinner_item,operationName);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3,"عملیات");
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                whichoperation=position;
                filterDevice();
//                whichTemplate=position;
//                operations.clear();
//                operations.addAll(templates.get(position).getOperations());
//                adapter.notifyDataSetChanged();
                // numberOfChannel=String.valueOf(position+1);
            }


            public void onNothingSelected(AdapterView<?> parent) {
                whichoperation=-1;
                filterDevice();
//                whichTemplate=-1;
                // numberOfChannel=String.valueOf(-1);
            }
        });
        ////////////////////////////////////////////////////////

        statusName=new ArrayList<>();
        statusName.add("موفق");
        statusName.add("ناموفق");
        statusName.add("همه");
        NoDefaultSpinner spinner4 = (NoDefaultSpinner) findViewById(R.id.spinnerOfStatusFilter);
        ArrayAdapter<String> adapter4 = new     ArrayAdapter<String>(showLog.this, android.R.layout.simple_spinner_item,statusName);

        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4,"نتیجه");
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                whichstatus=position;
                filterDevice();
//                whichTemplate=position;
//                operations.clear();
//                operations.addAll(templates.get(position).getOperations());
//                adapter.notifyDataSetChanged();
                // numberOfChannel=String.valueOf(position+1);
            }


            public void onNothingSelected(AdapterView<?> parent) {
                whichstatus=-1;
                filterDevice();
//                whichTemplate=-1;
                // numberOfChannel=String.valueOf(-1);
            }
        });
    }
    public void filterDevice(){
        MyArrayList<Operation> tmpOperation=new MyArrayList<>();
        if (whichDevice==-1 || whichDevice==deviceName.size()-1){
            tmpOperation.addAll(alloperations);
            filterOperation(tmpOperation);
        }else {
            for (int i=0;i<alloperations.size();i++){
                if (deviceName.get(whichDevice).equals(alloperations.get(i).getDeviceName())){
                    tmpOperation.add(alloperations.get(i));
                }
            }
            filterOperation(tmpOperation);
        }
    }
    public void filterOperation(MyArrayList<Operation> tmpOperation){
        MyArrayList<Operation> tmpOperation2=new MyArrayList<>();
        if (whichoperation==-1 || whichoperation==operationName.size()-1) {
            filterStatus(tmpOperation);
        }else {
            for (int i=0;i<tmpOperation.size();i++){
                if (operationName.get(whichoperation).equals(tmpOperation.get(i).getTitle().substring(0,operationName.get(whichoperation).length()))){
                    tmpOperation2.add(tmpOperation.get(i));
                }
            }
            filterStatus(tmpOperation2);
        }
    }
    public void filterStatus(MyArrayList<Operation> tmpOperation){
        MyArrayList<Operation> tmpOperation2=new MyArrayList<>();
        if (whichstatus==-1 || whichstatus==statusName.size()-1) {
            filterFinal(tmpOperation);
        }else {
            for (int i=0;i<tmpOperation.size();i++){
                if (statusName.get(whichstatus).equals(tmpOperation.get(i).getTextOfImage())){
                    tmpOperation2.add(tmpOperation.get(i));
                }
            }
            filterFinal(tmpOperation2);
        }
    }
    public void filterFinal(MyArrayList<Operation> tmpOperation){
        operations.clear();
        operations.addAll(tmpOperation);
        adapter.notifyDataSetChanged();
    }
    public ArrayList<String> loadStringOfDevice(){
        HashSet<String> set=new HashSet<String>();
        for (int i=0;i<alloperations.size();i++){
            set.add(alloperations.get(i).getDeviceName());
        }
        ArrayList<String>tmp=new ArrayList<>();
        tmp.addAll(set);
        tmp.add("همه");
        return tmp;
    }
    private void multiSelect(int position) {
        Operation data = adapter.getItem(position);
        if (data != null){
            if (actionMode != null) {
                if (selectedIds.contains(data.getId()))
                    selectedIds.remove(data.getId());
                else
                    selectedIds.add(data.getId());

                if (selectedIds.size() > 0)
                    actionMode.setTitle(String.valueOf(selectedIds.size())); //show selected item count on action mode.
                else{
                    actionMode.setTitle(""); //remove item count from action mode.
                    actionMode.finish(); //hide action mode.
                }
                adapter.setSelectedIds(selectedIds);

            }
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.list_context_menu, menu);
        return true;
    }
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_item_delete:
                //just to show selected items.
                int tmp=0;
                int n=operations.size();
                for (int i=0;i<n;i++) {
                    if (selectedIds.contains(operations.get(i - tmp).getId()) ) {

                        operations.remove(i - tmp);

                        nameArray.remove(i - tmp);

                        tmp++;
                    }
                }
                int tmp2=0;
                int n2=alloperations.size();
                for (int i=0;i<n2;i++) {
                    if (selectedIds.contains(alloperations.get(i - tmp2).getId()) ) {
                        alloperations.remove(i - tmp2);
                        tmp2++;
                    }
                }
                adapter.notifyDataSetChanged();
                selectedIds.clear();
                actionMode.finish();
                //Toast.makeText(this, "Selected items are :" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        isMultiSelect = false;
        selectedIds = new ArrayList<>();
        adapter.setSelectedIds(new ArrayList<String>());
    }
    @Override
    public void onStop() {
        super.onStop();
        boolean result =alloperations.saveObject(alloperations,cacheDir,"OperationLog");
    }
    @Override
    public void onStart(){
        super.onStart();
        //loadContent();
    }
    public void loadContent(){

        MyArrayList<Operation> operationstmp = operations.getObject(getApplicationContext(),cacheDir,"OperationLog");

        if(operationstmp!= null &&  (operations.size()==0 )) {
            for (int i=0;i<operationstmp.size();i++){
                if (!operationstmp.get(i).getShowDeviceName()) {
                    operationstmp.get(i).setTitle(operationstmp.get(i).getTitle() + "   دستگاه  " + operationstmp.get(i).getDeviceName());
                    operationstmp.get(i).setShowDeviceName(true);
                }
            }
            operations=operationstmp;
            alloperations.addAll(operations);
            adapter.setOperations(operations);
            //Toast.makeText(this, "Retrieved object", Toast.LENGTH_LONG).show();
            for(int i=0;i<operations.size();i++){
                nameArray.add(operations.get(i).getTitle());
            }
            adapter.notifyDataSetChanged();
        }else {

            //Toast.makeText(this, "Error retrieving object", Toast.LENGTH_LONG).show();

        }
    }
}
