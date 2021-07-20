package com.young2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.InputStream;
//import com.microsoft.azure.storage.CloudBlobClient;


public class MainActivity extends AppCompatActivity {

    CloudBlobContainer containerA;

    ImageView imgV;

    final String connStr = "DefaultEndpointsProtocol=https;AccountName=youngstorageagainwho;AccountKey=+rrGvrg/i/GISZ8E++bkgYsuAIoKQGyNhzyP/t4DoK8sHOYDGw5/kXblH/p1tnNLfcEmeoXZZsfBN9O2iHGyqg==;EndpointSuffix=core.windows.net";
    int imgIDX =0;

    int [] imgArr = {
            R.drawable.t1_nayeon, R.drawable.t2_dahyeon, R.drawable.t3_jeongyeon,
            R.drawable.t4_sana, R.drawable.t5_momo, R.drawable.t6_chaeyoung,
            R.drawable.t7_mina, R.drawable.t8_jihyo, R.drawable.t9_zuwi
    };
    String[] nameArr= {
            "nayeon","dahyeon","Jeongyeon","sana","momo","chayoung","mina","lihyo","zuwi"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imgV = findViewById(R.id.imgV);
        this.imgV.setImageResource(R.drawable.t1_nayeon);

        Thread threadA = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    CloudStorageAccount sa = CloudStorageAccount.parse(connStr);
                    CloudBlobClient bc = sa.createCloudBlobClient();
                    containerA = bc.getContainerReference("cona");
                } catch (Exception e){
                    Log.d("Tag",e.toString());
                }
            }
        });
        threadA.start();

    }

    public void nextIMG(View v){
        imgIDX++;
        if(imgIDX > 8) imgIDX = 0;
        this.imgV.setImageResource(imgArr[imgIDX++]);
    }

    public void sendIMG(View v){
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getResources().openRawResource(imgArr[imgIDX]);
                    final int imageL = is.available();
                    containerA.createIfNotExists();
                    String imgN = nameArr[imgIDX] + ".jpg";
                    CloudBlockBlob imgA = containerA.getBlockBlobReference(imgN);
                    imgA.upload(is, imageL);

                } catch (Exception e){
                    Log.d("tag", e.toString());
                }
            }
        });
        threadA.start();
    }


}