package com.example.user.softwarestudioassignment8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputIP;
    private Button ipSend;
    private String ipAdd = "";

    private ClientThread client;

    private EditText inputNumTxt1;
    private EditText inputNumTxt2;

    private Button btnAdd;
    private Button btnSub;
    private Button btnMult;
    private Button btnDiv;
    private TextView text;

    private TextView textResult;
    private Button return_button;

    private String oper = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ip_page);
        inputIP = (EditText)findViewById(R.id.edIP);
        ipSend = (Button)findViewById(R.id.ipButton);


        ipSend.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipAdd = inputIP.getText().toString();
                Log.d("Client","Client Send");
                client = new ClientThread(ipAdd);
                client.start();
                jumpToMainLayout();
            }
        });
    }

    public  void jumpToMainLayout(){
        setContentView(R.layout.activity_main);

        inputNumTxt1 = (EditText) findViewById(R.id.etNum1);
        inputNumTxt2 = (EditText) findViewById(R.id.etNum2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        text = (TextView) findViewById(R.id.tvResult);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        float num1 = 0;
        float num2 = 0;
        float result = 0;

        if (TextUtils.isEmpty(inputNumTxt1.getText().toString())
                || TextUtils.isEmpty(inputNumTxt2.getText().toString())) {
            return;
        }
        num1 = Float.parseFloat(inputNumTxt1.getText().toString());
        num2 = Float.parseFloat(inputNumTxt2.getText().toString());

        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                result = num1 + num2;
                break;
            case R.id.btnSub:
                oper = "-";
                result = num1 - num2;
                break;
            case R.id.btnMult:
                oper = "*";
                result = num1 * num2;
                break;
            case R.id.btnDiv:
                oper = "/";
                result = num1 / num2;
                break;
            default:
                break;
        }

        Log.d("debug","ANS "+result);

        jumpToResultLayout(new String(num1 + " " + oper + " " + num2 + " = " + result));
    }

    public void jumpToResultLayout(String resultStr){
        setContentView(R.layout.result_page);
        this.client.sendMessage("The calculate from app is : "+resultStr);
        return_button = (Button) findViewById(R.id.return_button);
        textResult = (TextView) findViewById(R.id.textResult);

        if (textResult != null) {
            textResult.setText(resultStr);
        }

        if (return_button != null) {
            return_button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    jumpToMainLayout();
                }
            });
        }
    }

    public class ClientThread extends Thread{
        private Socket socket;
        private PrintWriter writer;
        private String server_ip;
        private int serPort;

        ClientThread(String ipAdd){
            this.server_ip = ipAdd;
            this.serPort = 8000;
        }

        public void run(){
            try{
                socket = new Socket(server_ip,serPort);
                writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            }
            catch (Exception e){
                System.out.println("Error" + e.getMessage());
            }
        }

        public void sendMessage(String message){
            writer.println(message);
            writer.flush();
        }
    }








}
