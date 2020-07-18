package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txtGetData;
    private Button btnGetAll;
    private String allKickBoxer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        txtGetData= findViewById(R.id.txtGetData);
        btnGetAll = findViewById(R.id.btnGetAll);

        //method
        getDataClick();
        getAllData();
    }

//    public void helloClick(View view){
//        ParseObject boxer = new ParseObject("Boxer"); //CREATE TABLE (CLASS)
//        boxer.put("punch_speed",200); //COLUMN
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    Toast.makeText(MainActivity.this,"boxer object saved",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(MainActivity.this,"failed : "+e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    //get data by parse query
    public void getDataClick(){
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("4x2COHoGFk", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e==null) txtGetData.setText(object.get("name")+" "+object.get("kick_power"));
                        else FancyToast.makeText(MainActivity.this,"failed : "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public void getAllData(){
        allKickBoxer = "";
        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null){
                            if(objects.size()>0){
                                for(ParseObject kickBoxer : objects){
                                    allKickBoxer = allKickBoxer+ kickBoxer.get("name") + "\n";
                                }
                                FancyToast.makeText(MainActivity.this,
                                        allKickBoxer,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            }else{
                                FancyToast.makeText(MainActivity.this,"Fail : " +e.getMessage()
                                        ,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                            }
                        }
                    }
                });
            }
        });
    }

    //insert retrieve data from parse
    public void helloClick2(View view){
        final ParseObject kickBoxer = new ParseObject("KickBoxer");
        kickBoxer.put("name","Leonardo");
        kickBoxer.put("punch_power",500);
        kickBoxer.put("punch_speed",400);
        kickBoxer.put("kick_power",1000);
        kickBoxer.put("kick_speed",800);
        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null) FancyToast.makeText(MainActivity.this,
                        kickBoxer.get("name")+" is saved to the server",FancyToast.LENGTH_LONG,FancyToast.DEFAULT,true).show();
                else FancyToast.makeText(MainActivity.this,"failed : "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
