package com.example.lottonumber;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edtNumber;
    private Button btnCreateNumber, btnQrScan;
    private ListView listView;

    private int[] lottoOriginNo = new int[45];
    private String resultNums;
    private ArrayList<LottoNumbers> lottoNumbersArrayList;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNumber = findViewById(R.id.edtNumber);
        btnCreateNumber = findViewById(R.id.btnCreateNumber);
        btnQrScan = findViewById(R.id.btnQrScan);
        listView = findViewById(R.id.listView);

        lottoNumbersArrayList = new ArrayList<>();

        for (int i = 0; i < 45; i++) {
            lottoOriginNo[i] = i + 1;
        }

        gridViewAdapter = new GridViewAdapter(this, lottoNumbersArrayList);
        listView.setAdapter(gridViewAdapter);

        btnCreateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                if (!lottoNumbersArrayList.isEmpty()) {
                    lottoNumbersArrayList.clear();
                }

                if (!edtNumber.getText().toString().equals("")) {
                    try {
                        count = Integer.parseInt(edtNumber.getText().toString());
                        if (count == 0) {
                            throw new NumberFormatException();
                        }
                        for (int i = 0; i < count; i++) {
                            for (int j = 0; j < 6; j++) {
                                int randNum = (int) (Math.random() * 45);
                                int temp = lottoOriginNo[j];
                                lottoOriginNo[j] = lottoOriginNo[randNum];
                                lottoOriginNo[randNum] = temp;
                            }

                            LottoNumbers lottoNumbers = new LottoNumbers(lottoOriginNo);
                            lottoNumbersArrayList.add(lottoNumbers);
                            Log.d("Created Numbers", lottoNumbersArrayList.toString());
                        }

                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "수량에 숫자만 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "수량을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                gridViewAdapter.notifyDataSetChanged();
            }

        });

        btnQrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setCaptureActivity(QRCodeScanActivity.class);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String strResult = intentResult.getContents();
        Log.d("Scan Result", strResult == null ? "Result Fail" : strResult);

        if (intentResult != null && strResult != null) {
            if (!strResult.contains("/m.dhlottery.co.kr/")) {
                Toast.makeText(MainActivity.this, "로또 QR코드가 아닙니다.", Toast.LENGTH_SHORT).show();
            } else {
                Uri uri = Uri.parse(strResult);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
