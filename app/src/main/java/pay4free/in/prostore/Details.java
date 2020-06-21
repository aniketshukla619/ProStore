package pay4free.in.prostore;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import info.hoang8f.widget.FButton;
import pay4free.in.prostore.Model.Phone;

public class Details extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imageView;
    Phone phone;
    TextView productname, amazonprice, flipkartprice, prostoreprice,amazontext,flipkart,prostore;
    String urla,urlf;
    FButton flip,amaze;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(getIntent().getStringExtra("Phone"));
        //  Toast.makeText(getApplicationContext(),"We highly recommend you to choose pay latter or Pay Via Agent",Toast.LENGTH_SHORT).show();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        imageView = (ImageView) findViewById(R.id.image);
        flipkartprice = (TextView) findViewById(R.id.rupees1);
        amazonprice = (TextView) findViewById(R.id.refundable);
        productname = (TextView) findViewById(R.id.carname);
        prostore=(TextView)findViewById(R.id.payable);
        flip=(FButton)findViewById(R.id.fbutton1);
        amaze=(FButton)findViewById(R.id.fbutton2);
       // prostoreprice = (TextView) findViewById(R.id.paymoney);
        amazontext=(TextView)findViewById(R.id.fare1);
        flipkart=(TextView)findViewById(R.id.fare);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Raleway-Light.ttf");
        flipkartprice.setTypeface(typeface);
        amazonprice.setTypeface(typeface);
//        prostoreprice.setTypeface(typeface);
        productname.setTypeface(typeface);
        amazontext.setTypeface(typeface);
        flipkart.setTypeface(typeface);
        prostore.setTypeface(typeface);


        if (getIntent() != null) {
            id = getIntent().getStringExtra("Id");
        }
        if (!id.isEmpty()) {
            getdetail(id);
        }
flip.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(urlf));
        startActivity(i);
    }
});

        amaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Details.this,Browse.class);
                intent.putExtra("url",urla);
                startActivity(intent);
            }
        });
    }

    private void getdetail(String autoid) {
        databaseReference.child(autoid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phone = dataSnapshot.getValue(Phone.class);
                if (phone != null) {
                    Picasso.with(getBaseContext()).load(phone.getImage()).into(imageView);
                    productname.setText(phone.getName());
                    if (phone.getUrla().length() > 3 && phone.getUrlf().length() > 2) {
                      urla=phone.getUrla();
                        urlf=phone.getUrlf();
                        new Amazon(phone.getUrla(), phone.getAmazon(), phone.getData()).execute();
                        new Title(phone.getUrlf(), phone.getFlipkart()).execute();
                        flip.setVisibility(View.VISIBLE);
                        amaze.setVisibility(View.VISIBLE);


                    } else if (phone.getUrlf().length() > 2 && phone.getUrla().length() == 2) {
                        urlf=phone.getUrlf();
                        new Title(phone.getUrlf(), phone.getFlipkart()).execute();
                        amazonprice.setText("Not available on Amazon");
                        flip.setVisibility(View.VISIBLE);
                    }
                    else if (phone.getUrla().length()>2&&phone.getUrlf().length()==2)
                    {
                        urla=phone.getUrla();
                        new Amazon(phone.getUrla(), phone.getAmazon(), phone.getData()).execute();
                        flipkartprice.setText("Not available on Flipkart");
                        amaze.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private class Title extends AsyncTask<Void, Void, Void> {
        String title = "Price";
        String url="";
        String flipkartdata="";
private Title(String url,String flipkartdata)
{
    this.url=url;
    this.flipkartdata=flipkartdata;
}
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                //Document document = Jsoup.connect(url).get();
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select(flipkartdata);
                title = elements.text();


                // Get the html document title
                ;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            if(!title.isEmpty())
                flipkartprice.setText(title);
            else
                flipkartprice.setText(title);


        }
    }
    private class Amazon extends AsyncTask<Void, Void, Void> {
        String title="price";
        String url="";
        String amazon="";
        String data="";

        private Amazon(String url, String amazon, String data) {
            this.url = url;
            this.amazon = amazon;
            this.data = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                //Document document = Jsoup.connect(url).get();
                Document document = Jsoup.connect(url).get();
                Elements elements=document.select(amazon);
                title = elements.attr(data);

                Log.v("price",title);


                // Get the html document title
                ;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            System.out.print(title);
            if(!title.isEmpty())
            amazonprice.setText(title);
            else
                amazonprice.setText("Not in Stock");

        }
    }
}

