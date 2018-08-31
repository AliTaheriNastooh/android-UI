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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class showLog extends AppCompatActivity implements ActionMode.Callback{
    MyRecyclerViewAdapter adapter;
    ArrayList<String> nameArray=new ArrayList<String>();
    MyArrayList<Operation> operations=new MyArrayList<Operation>();
    private boolean isMultiSelect = false;
    private List<String> selectedIds = new ArrayList<>();
    private ActionMode actionMode;
    public File cacheDir;
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
                    if (selectedIds.contains(operations.get(i - tmp).getId())) {
                        operations.remove(i - tmp);

                        nameArray.remove(i - tmp);

                        tmp++;
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
        boolean result =operations.saveObject(operations,cacheDir,"OperationLog");
    }
    @Override
    public void onStart(){
        super.onStart();
        loadContent();
    }
    public void loadContent(){

        MyArrayList<Operation> operationstmp = operations.getObject(getApplicationContext(),cacheDir,"OperationLog");

        if(operationstmp!= null &&  (operations.size()==0 )) {
            operations=operationstmp;
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
