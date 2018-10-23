package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.consquencyandmonitoringsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PassChecker extends Activity {

    private EditText etUser, etPass;
    private Button btNext;
    private String username, passBaru;

    private String ganti_pass_checker = "http://depotlpgbanyuwangi.com/gantipasschecker.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_checker);

        etUser = (EditText)findViewById(R.id.checkID);
        etPass = (EditText)findViewById(R.id.passBaru);
        btNext = (Button)findViewById(R.id.btnNext);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUser.getText().toString();
                passBaru = etPass.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ganti_pass_checker, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");

                            Toast.makeText(PassChecker.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            etUser.setText("");
                            etPass.setText("");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PassChecker.this,"Connection to Server Lost",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("username",username);
                        params.put("passBaru",passBaru);
                        return params;
                    }
                };
                MySingleton.getmInstance(PassChecker.this).addToRequestQueue(stringRequest);
            }
        });

    }
}
