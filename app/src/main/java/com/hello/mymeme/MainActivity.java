package com.hello.mymeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,MTAG {
private Button btn_next;
private TextView titleView;
private ImageView img_meme;
Context context=MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_next=findViewById(R.id.btn_nxt_id);
        img_meme=findViewById(R.id.imageView);
        titleView=findViewById(R.id.txt_title_id);
        btn_next.setOnClickListener(this);
    }
    public void onClick(View v){
       loadImg();
        Log.d(TAG, "onClick: ");
    }
    public void loadImg(){
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url="https://meme-api.herokuapp.com/gimme";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String nurl="";
                String title="";
                try {
                    JSONObject jsonObject=new JSONObject(response);
                   nurl=(String) jsonObject.get("url");
                   title=(String) jsonObject.get("title");
                   titleView.setText(title);
                       Glide.with(context).load(nurl).into(img_meme);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}