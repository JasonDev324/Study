package io.tanjundang.study.knowledge.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.tanjundang.study.R;

public class RemoteActivity extends AppCompatActivity {

    private RemoteAidlInterface mBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder = RemoteAidlInterface.Stub.asInterface(iBinder);
            try {
                mBinder.calcMsg();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
    }

    public void StartRemoteService(View v) {
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    public void UnbindRemoteService(View v) {
        if (mBinder != null) {
            unbindService(connection);
            mBinder = null;
        }
    }
}
