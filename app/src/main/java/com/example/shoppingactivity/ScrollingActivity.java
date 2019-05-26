package com.example.shoppingactivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


import static android.app.PendingIntent.getActivity;
import static com.example.shoppingactivity.R.layout.*;

public class ScrollingActivity extends AppCompatActivity {


    public class ShopData {
        public ShopData(String name, int imageResource, String description, int cost) {
            m_name = name;
            m_imageResource = imageResource;
            m_description = description;
            m_cost = cost;
            m_purchased = false;
        }
        public String m_name;
        public int m_imageResource;
        public String m_description;
        public int m_cost;
        public boolean m_purchased;


    };

    public static ScrollingActivity instance = null;
    public int di;
    public static Boolean flag = false;

    ShopData m_data[] =
            {
                    new ShopData("Bone",R.drawable.bone,"Good bone",1),
                    new ShopData("Carrot",R.drawable.carrot,"Carrot",6),
                    new ShopData("Dog",R.drawable.dog,"Dog",600),
                    new ShopData("Flame",R.drawable.flame,"Flame",2),
                    new ShopData("Yak",R.drawable.yak,"Yak",50),
                    new ShopData("Grapes",R.drawable.grapes,"Grapes",100),
                    new ShopData("House",R.drawable.house,"House",200000),
                    new ShopData("Lamp",R.drawable.lamp,"Lamp",20),
                    new ShopData("Mouse",R.drawable.mouse,"Mouse",5),
                    new ShopData("Nail",R.drawable.nail,"Flame",3),
                    new ShopData("Rocks",R.drawable.rocks,"Rocks",0),
                    new ShopData("Wheat",R.drawable.wheat,"Wheat",1),
                    new ShopData("Penguin",R.drawable.penguin,"Penguin",100),
                    new ShopData("Van",R.drawable.van,"Van",30000),
                    new ShopData("Toad",R.drawable.toad,"Toad",2),
                    new ShopData("Star",R.drawable.star,"Start",90),
            };

    public CustomDialogClass customdialogclass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("admin","admin");
        editor.commit();

        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(log_in);
        Button b1 = findViewById(R.id.submitbutton);
        customdialogclass = new CustomDialogClass(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView getheuseremail = findViewById(R.id.useremail);
                TextView getthepassword = findViewById(R.id.userpassword);
                String email = getheuseremail.getText().toString();
                String password = getthepassword.getText().toString();
                SharedPreferences pref = getSharedPreferences("credentials", MODE_PRIVATE);
                if (email.equals("admin") & password.equals("admin")) {

                    System.out.println("Triggered");
                    setContentView(activity_scrolling);
                    Thread load = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater inflator = LayoutInflater.from(getApplicationContext());
                            for (int i = 0; i < m_data.length; i++) {
                                final View myshopintem = inflator.inflate(shop_item, null);
                                final int tmp = i;
                                View.OnClickListener click = new View.OnClickListener() {
                                    public int id = tmp;
                                    @Override
                                    public void onClick(View v) {
                                        ScrollingActivity.instance.ShopClicked(v, id);
                                    }
                                };
                                Button tb = myshopintem.findViewById(R.id.button0);
                                tb.setText(m_data[i].m_name);
                                tb.setOnClickListener(click);
                                ImageButton ib = myshopintem.findViewById(R.id.imageButton0);
                                ib.setImageResource(m_data[i].m_imageResource);
                                ib.setOnClickListener(click);
                                final LinearLayout layout = findViewById(R.id.myView);
                                ScrollingActivity.instance.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        layout.addView(myshopintem);
                                    }
                                });
                            }
                        }
                    }
                    );
                    load.start();

                }
            }
        });
    }



    class CustomDialogClass extends Dialog
    {
        public CustomDialogClass(Context context) {
            super(context);
        }


        public void fix(){
            TextView tv = this.findViewById(R.id.imagename);
            TextView pi = this.findViewById(R.id.priceofimage);
            TextView id = this.findViewById(R.id.imagedescription);
            ImageView iv = this.findViewById(R.id.imagedataview);
            tv.setText(m_data[di].m_name);
            pi.setText(String.valueOf(m_data[di].m_cost));
            id.setText(String.valueOf(m_data[di].m_description));
            iv.setImageResource(m_data[di].m_imageResource);
        }

        @Override
        protected void onCreate(Bundle savedInstance) {
            super.onCreate(savedInstance);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.shop_dialog);
            fix();

        }
    }


    public void ShopClicked(View v, int dataindex) {
        LinearLayout layout = findViewById(R.id.myView);
        int layoutindex = layout.indexOfChild((View)v.getParent());
        di = dataindex;
        customdialogclass.show();
        customdialogclass.fix();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
