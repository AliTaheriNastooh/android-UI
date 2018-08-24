package com.sourcey.materiallogindemo;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;

import java.util.concurrent.BlockingQueue;

public class RecordTask extends AsyncTask<Void, Object, Void> {
	
	
	int frequency = 16000;
	int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

	int blockSize = 1024;
	
	Controller controller;
	BlockingQueue<DataBlock> blockingQueue;

	public RecordTask(Controller controller, BlockingQueue<DataBlock> blockingQueue) {
		this.controller = controller;
		this.blockingQueue = blockingQueue;
	}
	MediaRecorder mediaRecorder;
	@Override
	protected Void doInBackground(Void... params) {

		int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);

		AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_DOWNLINK, frequency, channelConfiguration, audioEncoding, bufferSize);
		
		try {

			short[] buffer = new short[blockSize];

			audioRecord.startRecording();

			while (controller.isStarted())
			{
				int bufferReadSize = audioRecord.read(buffer, 0, blockSize);
				
				DataBlock dataBlock = new DataBlock(buffer, blockSize, bufferReadSize);
				
				blockingQueue.put(dataBlock);
			}
			
		} catch (Throwable t) {
			//Log.e("AudioRecord", "Recording Failed");
			t.printStackTrace();
		}
		
		try {
			audioRecord.stop();
		}catch (Exception er){
			er.printStackTrace();

		}


		return null;
	}
}