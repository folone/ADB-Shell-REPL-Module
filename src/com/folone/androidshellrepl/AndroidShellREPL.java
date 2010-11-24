package com.folone.androidshellrepl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.folone.Evaluator;

public class AndroidShellREPL extends Service {

	private Evaluator.Stub evaluator = new Evaluator.Stub() {

		public String evaluate(String script) throws RemoteException {
			String result = "";
			try {
				Process process = Runtime.getRuntime().exec(script);
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				StringBuffer strBuff = new StringBuffer();
				String tmp = "";
				while ((tmp = reader.readLine()) != null) {
					strBuff.append(tmp + "\n");
				}
				reader.close();
				result = strBuff.toString();
			} catch (IOException e) {
				result = e.getMessage();
			}
			return result;
		}
	};
	@Override
	public IBinder onBind(Intent intent) {
		return evaluator;
	}

}
