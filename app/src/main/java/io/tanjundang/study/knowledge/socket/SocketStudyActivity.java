package io.tanjundang.study.knowledge.socket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.tools.Functions;

public class SocketStudyActivity extends BaseActivity implements View.OnClickListener {

    private Button btnSend;
    private Button btnConnect;
    private TextView tvContent;
    private EditText etIP;
    private EditText etContent;
    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;
    private final int PORT = 4000;
    private final int MSG_TOAST = 0XFF;
    private final String ERROR_MSG = "ERROR_MSG";
    private final String SUCCESS_MSG = "SUCCESS_MSG";
    //在非UI线程中使用Handler更新UI
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = msg.getData().getString(ERROR_MSG);
            if (!TextUtils.isEmpty(result)) {
                Functions.toast(result);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_socket_study);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(this);
        tvContent = (TextView) findViewById(R.id.tvContent);
        etIP = (EditText) findViewById(R.id.etIP);
        etContent = (EditText) findViewById(R.id.etContent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                sendErrorMsg(e);
                e.printStackTrace();
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        if (v.equals(btnConnect)) {
            connect();
        } else if (v.equals(btnSend)) {
            send();
        }
    }

    public void connect() {
        ConnectTask task = new ConnectTask(etIP.getText().toString());
        task.execute();
    }

    /**
     * 创建一个线程，用于接收和发送数据
     */
    class ConnectTask extends AsyncTask<Void, String, Void> {
        private String ipAddress;

        public ConnectTask(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket = new Socket(ipAddress, PORT);
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                publishProgress(SUCCESS_MSG);
                String msg = "";
                while ((msg = br.readLine()) != null) {
                    publishProgress(msg);//将数据发送到onProgressUpdate
                }
            } catch (IOException e) {
                sendErrorMsg(e);
                e.printStackTrace();
            }
            return null;
        }

        //与publishProgress结合使用
        @Override
        protected void onProgressUpdate(String... values) {
            if (SUCCESS_MSG.equals(values[0])) {
                etIP.setText("连接服务器成功");
                tvContent.append("开始进行通信\n");
            } else {
                tvContent.append(values[0] + "\n");
            }
        }
    }

    /**
     * @param e 当出现异常的时候发送消息更新UI
     */
    private void sendErrorMsg(Exception e) {
        Message errorMsg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(ERROR_MSG, e.getMessage());
        errorMsg.setData(bundle);
        handler.sendMessage(errorMsg);
    }

    /**
     * 发送消息到服务器
     */
    public void send() {
        try {
            if (bw == null) return;
            bw.write(String.format("%s\n", etContent.getText()));
            bw.flush();
            etContent.setText(null);
        } catch (IOException e) {
            sendErrorMsg(e);
            e.printStackTrace();
        }
    }

}
