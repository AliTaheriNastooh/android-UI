package com.sourcey.materiallogindemo;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Agriculture_system extends AppCompatActivity implements ActionMode.Callback{

    public static final String EXTRA_NAME = "cheese_name";
    Device device;
    public static final String EXTRA_MESSAGE1 = "com.sourcey.materiallogindemo.MESSAGE";
    /////////////////////
    private Switch simpleSwitch;
    MyRecyclerViewAdapter adapter;
    ArrayList<String> nameArray=new ArrayList<String>();
    MyArrayList<Operation> operations=new MyArrayList<Operation>();
    public File cacheDir;
    CallingWithDevice callingWithDevice;
    private int RESULT_LOAD_IMAGE=73;
    private int RESULT_GET_DETAIL=75;
    private int positionIntent;
    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    private List<String> selectedIds = new ArrayList<>();
    private int whichRadioButton=-1;
    private boolean radioButtonGroupFlag=false;
    private CheckBox checkBoxEveryDay;
    private CheckBox checkBoxEvery2Day;
    private CheckBox checkBoxEveryWeek;
    private CheckBox checkBoxEveryMonth;
    private CheckBox checkBox;
    private static Button hourOfWakeUp;
    private TextView enterHour;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Alarm alarm=new Alarm();
    static int minuteOfReminder;
    static int houreOfReminder;
    static  int idOfHourOfWakeUp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agriculture_system);


        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"MyCustomObject");
        else
            cacheDir= getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

        Intent intent = getIntent();
        device= (Device) intent.getSerializableExtra("device");
        positionIntent= (int) intent.getSerializableExtra("positionDevice");
        TextView phoneNumber=(TextView)findViewById(R.id.devicePhoneNumber);
        TextView address=(TextView)findViewById(R.id.deviceAdress);

        phoneNumber.setText("شماره تماس دستگاه: " +device.getPhoneNumber());
        address.setText( "آدرس دستگاه: " +device.getAddress());
        final Toolbar toolbar = findViewById(R.id.toolbar2);
       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOfOperation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, operations);
       // adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
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
                        actionMode = startActionMode((android.view.ActionMode.Callback) Agriculture_system.this); //show ActionMode.
                    }
                }

                multiSelect(position);
            }
        }));
        ///////////////////////
//        listView.setOnTouchListener(new View.OnTouchListener() {
//            // Setting on Touch Listener for handling the touch inside ScrollView
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // Disallow the touch request for parent scroll on touch of child view
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
       // setListViewHeightBasedOnChildren(listView);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("  "+device.getName());
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.black));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primary));

        loadBackdrop();

        callingWithDevice=new CallingWithDevice(this,"agricultureSystem",device.getPhoneNumber());

        //////////////////////////
        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);




        FloatingActionButton fb_agriculture=(FloatingActionButton)findViewById(R.id.fb_agricultureSystem);
        fb_agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });


        RadioGroup rGroup = (RadioGroup)findViewById(R.id.radioGroupOfTemplate);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                String message="";
                switch (checkedId){
                    case R.id.radioButtonOfUseTemplate:

                        message="useTemplate";
                        whichRadioButton=R.id.radioButtonOfUseTemplate;
                        break;
                    case R.id.radioButtonOfNewOperation:
                        message="chooseTemplateOperation";
                        whichRadioButton=R.id.radioButtonOfNewOperation;
                        break;

                }
                if(!radioButtonGroupFlag) {
                    setIntentToNextPage(message, ChooseNewOperations.class);
                }else{
                    radioButtonGroupFlag=false;
                }

            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            device.setDetails(device.getName(),device.getModel(),device.getAddress(),device.getPhoneNumber(),0,false,picturePath);
            ImageView imageView = (ImageView) findViewById(R.id.backdrop);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Intent data1 = new Intent();
            data1.putExtra("device",device);
            data1.putExtra("positionDevice",positionIntent);
            setResult(RESULT_OK,data1);
        }
        if (requestCode == RESULT_GET_DETAIL && resultCode == RESULT_OK && null != data) {
//            RadioGroup radioGroup1 = (RadioGroup)findViewById(R.id.radioGroupOfTemplate);
//            radioGroup1.clearCheck();
            if (data.getStringExtra("whichOperation").equals("template")){
                Bundle bundle=data.getExtras();
                ArrayList<Operation> tmpOperation1=(ArrayList<Operation>)bundle.getSerializable("operations");
                for (int i=0;i<tmpOperation1.size();i++){
                    nameArray.add(tmpOperation1.get(i).getTitle());
                    tmpOperation1.get(i).setDeviceName(device.getName());
                    tmpOperation1.get(i).setNewId();
                }
                operations.addAll(tmpOperation1);
                adapter.notifyDataSetChanged();
            }else{
                if (data.getStringExtra("whichOperation").equals("newOperation")){
                    Operation tmp=(Operation) data.getSerializableExtra("operation");
                    nameArray.add(tmp.getTitle());
                    if (data.getIntExtra("getState",0)!=4) {
                        tmp.setTitle(tmp.getTitle() + data.getStringExtra("getChannelNumber"));
                    }
                    operations.add(tmp);
                    adapter.notifyDataSetChanged();
                }
            }
            unCheckedRadioButton();
//            RadioButton s=findViewById(whichRadioButton);
//            s.setChecked(t);

        }
    }


    public void unCheckedRadioButton(){
//        RadioGroup radioGroup1 = (RadioGroup)findViewById(R.id.radioGroupOfTemplate);
//        radioGroup1.clearCheck();
        radioButtonGroupFlag=true;
        RadioButton s=findViewById(whichRadioButton);
        s.setChecked(false);

    }

    //////////////////////////



    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        if (device.getDefaultImage()){
            Glide.with(this).load(device.getImage()).asBitmap().centerCrop().into(imageView);
        }else{
            Glide.with(this).load(device.getCustomImage()).asBitmap().centerCrop().into(imageView);
        }

    }
//apply(RequestOptions.centerCropTransform()).into(imageView)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    //////////////////////////


    /////////////////////////
    public void buttonToSaveOperationAsTemplate(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Agriculture_system.this);
        alertDialog.setMessage("نام الگو را وارد کنید");
        final EditText input = new EditText(Agriculture_system.this);
        hourOfWakeUp = new Button(getApplicationContext());
        hourOfWakeUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");

            }
        });
        enterHour=new TextView(getApplicationContext());
        enterHour.setText("ساعت یادآوری را وارد کنید:بین 1 تا 24");
        hourOfWakeUp.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        hourOfWakeUp.setHint("ساعت را انتخاب کنید");
        hourOfWakeUp.setTextColor(getResources().getColor(R.color.colorAccent));
        hourOfWakeUp.setVisibility(View.INVISIBLE);
        idOfHourOfWakeUp=22222;
        hourOfWakeUp.setId(idOfHourOfWakeUp);
        enterHour.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams params = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin_left);
        params.topMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin_top);
        params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin_bottom);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin_left);
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        checkBox=new CheckBox(Agriculture_system.this);
        checkBox.setText("یادآوری این الگو");
        checkBox.setLayoutParams(params);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   if (checkBox.isChecked()){
                       checkBoxEveryDay.setVisibility(View.VISIBLE);
                       checkBoxEvery2Day.setVisibility(View.VISIBLE);
                       checkBoxEveryWeek.setVisibility(View.VISIBLE);
                       checkBoxEveryMonth.setVisibility(View.VISIBLE);
                       hourOfWakeUp.setVisibility(View.VISIBLE);
                       enterHour.setVisibility(View.VISIBLE);

                   }else {
                       checkBoxEveryDay.setVisibility(View.INVISIBLE);
                       checkBoxEvery2Day.setVisibility(View.INVISIBLE);
                       checkBoxEveryWeek.setVisibility(View.INVISIBLE);
                       checkBoxEveryMonth.setVisibility(View.INVISIBLE);
                        hourOfWakeUp.setVisibility(View.INVISIBLE);
                        enterHour.setVisibility(View.INVISIBLE);
                   }
               }
           }
        );
        int p=1;
        checkBoxEveryDay=new CheckBox(Agriculture_system.this);
        checkBoxEveryDay.setText("هر روز یادآوری کن");
        checkBoxEveryDay.setLayoutParams(params);
        checkBoxEveryDay.setVisibility(View.INVISIBLE);
        checkBoxEveryDay.setOnClickListener(mListener);
        checkBoxEveryDay.setId(p++);
        checkBoxEvery2Day=new CheckBox(Agriculture_system.this);
        checkBoxEvery2Day.setText("یک روز در میان یادآوری کن");
        checkBoxEvery2Day.setLayoutParams(params);
        checkBoxEvery2Day.setVisibility(View.INVISIBLE);
        checkBoxEvery2Day.setOnClickListener(mListener);
        checkBoxEvery2Day.setId(p++);
        checkBoxEveryWeek=new CheckBox(Agriculture_system.this);
        checkBoxEveryWeek.setText("هر هفته یادآوری کن");
        checkBoxEveryWeek.setLayoutParams(params);
        checkBoxEveryWeek.setOnClickListener(mListener);
        checkBoxEveryWeek.setVisibility(View.INVISIBLE);
        checkBoxEveryWeek.setId(p++);
        checkBoxEveryMonth=new CheckBox(Agriculture_system.this);
        checkBoxEveryMonth.setText("هر ماه یادآوری کن");
        checkBoxEveryMonth.setLayoutParams(params);
        checkBoxEveryMonth.setOnClickListener(mListener);
        checkBoxEveryMonth.setVisibility(View.INVISIBLE);
        checkBoxEveryMonth.setId(p++);
        enterHour.setLayoutParams(params);
        hourOfWakeUp.setLayoutParams(params);
        layout.addView(input);
        layout.addView(checkBox);
        layout.addView(checkBoxEveryDay);
        layout.addView(checkBoxEvery2Day);
        layout.addView(checkBoxEveryWeek);
        layout.addView(checkBoxEveryMonth);
        layout.addView(enterHour);
        layout.addView(hourOfWakeUp);
        alertDialog.setView(layout);
        alertDialog.setPositiveButton("تایید",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /////////
                        if(!input.getText().toString().isEmpty()){
                            TemplateOperation tmpTemplate=saveOperationInTemplate(input.getText().toString());
                            String tmpPeriod=getPeriodOfReminder();
                            if (tmpPeriod.equals("error")){
                                Toast.makeText(getApplicationContext(),"یادآوری و قالب ذخیره نشد . یکی از حالت های یادآوری(هر روز ، هر هفته ...) را انتخاب کنید و بعد تایید را بفشاری",Toast.LENGTH_LONG).show();
                                dialog.cancel();
                                return;
                            }
                            ReminderTemplate tmpReminder=new ReminderTemplate(device,tmpTemplate,tmpPeriod,houreOfReminder,minuteOfReminder,positionIntent);
                            saveReminderTemplate(tmpReminder);
                           // alarm.cancelAlarm(getApplicationContext());
                            alarm.setAlarm(getApplicationContext(),tmpReminder);
                            dialog.cancel();
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
    public String getPeriodOfReminder(){
        if(checkBoxEveryDay.isChecked()){
            return  "everyDay";
        }else{
            if (checkBoxEvery2Day.isChecked()){
                return  "every2Day";
            }else{
                if (checkBoxEveryWeek.isChecked()){
                    return  "everyWeek";
                }else{
                    if (checkBoxEveryMonth.isChecked()){
                        return  "everyMonth";
                    }else {
                        return "error";
                    }
                }
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            Agriculture_system.minuteOfReminder=minute;
            Agriculture_system.houreOfReminder = hourOfDay;
            hourOfWakeUp.setText(houreOfReminder+":"+minuteOfReminder);
        }
    }

    private View.OnClickListener mListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            if (checkBoxEveryDay.getId() == checkedId) {
                checkBoxEveryDay.setChecked(true);
            } else {
                checkBoxEveryDay.setChecked(false);
            }

            if (checkBoxEvery2Day.getId() == checkedId) {
                checkBoxEvery2Day.setChecked(true);
            } else {
                checkBoxEvery2Day.setChecked(false);
            }
            if (checkBoxEveryMonth.getId() == checkedId) {
                checkBoxEveryMonth.setChecked(true);
            } else {
                checkBoxEveryMonth.setChecked(false);
            }
            if (checkBoxEveryWeek.getId() == checkedId) {
                checkBoxEveryWeek.setChecked(true);
            } else {
                checkBoxEveryWeek.setChecked(false);
            }
        }
    };
    public void CallButtonForSetOperation(View view){
        simpleSwitch.setClickable(false);
        String setMessage="";
        for (int i=0;i<operations.size();i++){
            setMessage+=(operations.get(i).getTextForCallDevice()+"\n");

        }
        callingWithDevice.connectWithDevice(simpleSwitch.isChecked(),setMessage);
    }

    public void getResultOfCalling(String recievedMassage){
        if(recievedMassage.equals("complete")||recievedMassage.equals("notcomplete") ){
            for (int i=0;i<operations.size();i++){
                if(recievedMassage.equals("notcomplete")){
                    operations.get(i).setNewId();
                }
                operations.get(i).setDetails(operations.get(i).getTitle(),operations.get(i).getInfo(),recievedMassage,operations.get(i).getTime(),operations.get(i).getDate(),operations.get(i).getTextForCallDevice());
            }
            saveOperationInLog();
            if(recievedMassage.equals("notcomplete")){
                setIntentToNextPage("showOperationWrong",GetDetails.class);
            }
            if(recievedMassage.equals("complete")){
                setIntentToNextPage("showOperationCorrect",GetDetails.class);
            }
        }else{
            Toast.makeText(getApplicationContext(),"wrong message",Toast.LENGTH_SHORT).show();
        }
    }
    public void getResultOfSMS(String recievedMassage){
        if(recievedMassage.length()==(2*operations.size())-1){
            int counter=0;
            boolean flag=true;
            for (int i=0;i<recievedMassage.length();i++){
                if (recievedMassage.charAt(i)!='\n'){
                    if (recievedMassage.charAt(i)=='1'){
                        operations.get(counter).setDetails(operations.get(counter).getTitle(),operations.get(counter).getInfo(),"complete",operations.get(counter).getTime(),operations.get(counter).getDate(),operations.get(counter).getTextForCallDevice());
                    }
                    if (recievedMassage.charAt(i )=='0'){
                         operations.get(counter).setDetails(operations.get(counter).getTitle(),operations.get(counter).getInfo(),"notcomplete",operations.get(counter).getTime(),operations.get(counter).getDate(),operations.get(counter).getTextForCallDevice());
                        flag=false;
                    }
                counter++;
                }
            }

            saveOperationInLog();
            if(flag==false){
                setIntentToNextPage("showOperationWrong",GetDetails.class);
            }else{
                setIntentToNextPage("showOperationCorrect",GetDetails.class);
            }
        }
    }
    public void setIntentToNextPage(String sMessage,Class destination){
        Intent intent1 = new Intent(this,destination);
        String message = sMessage;
        intent1.putExtra(EXTRA_MESSAGE1+"1", message);
        intent1.putExtra(EXTRA_MESSAGE1+"2",device);
        startActivityForResult(intent1,RESULT_GET_DETAIL);
    }


//        if(state==1){
//            if(channelState.equals("0")){
//                setIntentToNextPage("Channeloff");
//            }
//            if(channelState.equals("1")){
//                setIntentToNextPage("Channelon");
//            }
//        }
//        if(state==2){
//            if(channelState.equals("0")){
//                setIntentToNextPage("ShowChannelStateOff");
//            }
//            if(channelState.equals("1")){
//                setIntentToNextPage("ShowChannelStateOn");
//            }
//        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    @Override
    public void onPause() {
        super.onPause();
        boolean result =operations.saveObject(operations,cacheDir,"Operation"+device.getUniqueID());
        callingWithDevice.unRegesteredSMS();
    }
    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onResume(){
        super.onResume();
        callingWithDevice.setRegisteredSMS();
    }
    @Override
    public void onStart(){
        super.onStart();

        loadContent();
    }
    public void loadContent(){

        MyArrayList<Operation> operationstmp = operations.getObject(getApplicationContext(),cacheDir,"Operation"+device.getUniqueID());

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
    public void saveOperationInLog(){
        MyArrayList<Operation> operationstmp = operations.getObject(getApplicationContext(),cacheDir,"OperationLog");
        if (operationstmp!=null){
            //operations.get(i).setDetails(operations.get(i).getTitle(),operations.get(i).getInfo(),operationStatus,operations.get(i).getTime(),operations.get(i).getDate(),operations.get(i).getTextForCallDevice());
            operationstmp.addAll(operations);
            operationstmp.saveObject(operationstmp,cacheDir,"OperationLog");
        }else{
//            for (int i=0;i<operations.size();i++){
//                operations.get(i).setDetails(operations.get(i).getTitle(),operations.get(i).getInfo(),operationStatus,operations.get(i).getTime(),operations.get(i).getDate(),operations.get(i).getTextForCallDevice());
//            }
            boolean result =operations.saveObject(operations,cacheDir,"OperationLog");
        }
        int tm=0;
        for (int i=0;i<operations.size()+tm;i++) {
            if (operations.get(i-tm).getStatus().equals("complete")) {
                operations.remove(i-tm);
                nameArray.remove(i-tm);
                tm++;
            }
        }
        adapter.notifyDataSetChanged();
    }
    public TemplateOperation saveOperationInTemplate(String name){
        MyArrayList<Operation>tmp=new MyArrayList<Operation>();
        tmp.addAll(operations);

        TemplateOperation newTemplate=new TemplateOperation(name,tmp);
        MyArrayList<TemplateOperation> templateOperations=new MyArrayList<TemplateOperation>();
        MyArrayList<TemplateOperation> operationstmp = templateOperations.getObject(getApplicationContext(),cacheDir,"operationsTemplate");
        if (operationstmp!=null){
            //operations.get(i).setDetails(operations.get(i).getTitle(),operations.get(i).getInfo(),operationStatus,operations.get(i).getTime(),operations.get(i).getDate(),operations.get(i).getTextForCallDevice());
            operationstmp.add(newTemplate);
            operationstmp.saveObject(operationstmp,cacheDir,"operationsTemplate");
        }else{
//            for (int i=0;i<operations.size();i++){
//                operations.get(i).setDetails(operations.get(i).getTitle(),operations.get(i).getInfo(),operationStatus,operations.get(i).getTime(),operations.get(i).getDate(),operations.get(i).getTextForCallDevice());
//            }
            templateOperations.add(newTemplate);
            templateOperations.saveObject(templateOperations,cacheDir,"operationsTemplate");
        }
        return newTemplate;
    }
    public void saveReminderTemplate(ReminderTemplate newReminderTemplate){

        MyArrayList<ReminderTemplate> reminderTemplates=new MyArrayList<ReminderTemplate>();
        MyArrayList<ReminderTemplate> remindertmp = reminderTemplates.getObject(getApplicationContext(),cacheDir,"reminderTemplate");
        if (remindertmp!=null){
            //operations.get(i).setDetails(operations.get(i).getTitle(),operations.get(i).getInfo(),operationStatus,operations.get(i).getTime(),operations.get(i).getDate(),operations.get(i).getTextForCallDevice());
            remindertmp.add(newReminderTemplate);
            remindertmp.saveObject(remindertmp,cacheDir,"reminderTemplate");
        }else{
//            for (int i=0;i<operations.size();i++){
//                operations.get(i).setDetails(operations.get(i).getTitle(),operations.get(i).getInfo(),operationStatus,operations.get(i).getTime(),operations.get(i).getDate(),operations.get(i).getTextForCallDevice());
//            }
            reminderTemplates.add(newReminderTemplate);
            reminderTemplates.saveObject(reminderTemplates,cacheDir,"reminderTemplate");
        }

    }

    ////////////////////////////////////////////////////////////////////////1





    ///////////////////////////////////////////////////////////////////////
}
