package com.sourcey.materiallogindemo;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Controller 
{
	private boolean started=false;
	
	private RecordTask recordTask;	
	private RecognizerTask recognizerTask;	
	private StartDTMF_Decode mainActivity;
	BlockingQueue<DataBlock> blockingQueue;

	private Character lastValue;
		
	public Controller(StartDTMF_Decode mainActivity1)
	{
		this.mainActivity = mainActivity1;
	}

	public void changeState() 
	{
		if (started == false)
		{
			
			lastValue = ' ';
			
			blockingQueue = new LinkedBlockingQueue<DataBlock>();
			started = true;
			//mainActivity.start();
			recordTask = new RecordTask(this,blockingQueue);
			recognizerTask = new RecognizerTask(this,blockingQueue);
			recordTask.execute();
			recognizerTask.execute();


		} else {
			
			//mainActivity.stop();
			
			recognizerTask.cancel(true);
			recordTask.cancel(true);
			
			started = false;
		}
	}

	public void clear() {
		mainActivity.clearText();
	}

	public boolean isStarted() {
		return started;
	}


	


	public void keyReady(char key) 
	{

		
		if(key != ' ')
			if(lastValue != key)
				mainActivity.addText(key);
		
		lastValue = key;
		Log.i("changeState","caharcte: "+key);
	}
	public void showToast(String s){
		mainActivity.showToast(s);
	}

}
