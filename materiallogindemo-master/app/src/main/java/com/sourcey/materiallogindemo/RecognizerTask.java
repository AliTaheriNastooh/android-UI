package com.sourcey.materiallogindemo;

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.BlockingQueue;

public class RecognizerTask extends AsyncTask<Void, Object, Void> {
	
	private Controller controller;

	private BlockingQueue<DataBlock> blockingQueue;
	
	private Recognizer recognizer;

	public RecognizerTask(Controller controller, BlockingQueue<DataBlock> blockingQueue)
	{
		this.controller = controller;
		this.blockingQueue = blockingQueue;
		//controller.showToast("recognizer start");
		recognizer = new Recognizer();
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		while(controller.isStarted())
		{
			try {
				DataBlock dataBlock = blockingQueue.take();
								
				Spectrum spectrum = dataBlock.FFT();
				
				spectrum.normalize();				
				
				StatelessRecognizer statelessRecognizer = new StatelessRecognizer(spectrum);
				
				Character key = recognizer.getRecognizedKey(statelessRecognizer.getRecognizedKey());
				//controller.showToast("DoInBackground :"+key.toString());
				publishProgress(spectrum, key);
				
//				SpectrumFragment spectrumFragment = new SpectrumFragment(75, 100, spectrum);
//				publishProgress(spectrum, spectrumFragment.getMax());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
		
		return null;
	}
	
	protected void onProgressUpdate(Object... progress)
	{


		Character key = (Character)progress[1];
		Log.i("charactar : ",key.toString());
		controller.keyReady(key);
//		Integer key = (Integer)progress[1];
//		controller.debug(key.toString());
    }
}
