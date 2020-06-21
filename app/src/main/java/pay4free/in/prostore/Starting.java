package pay4free.in.prostore;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
import java.util.HashMap;

import pay4free.in.prostore.Fashion.Fashion;
import pay4free.in.prostore.Interface.ItemClickListener;
import pay4free.in.prostore.Model.Dummy;
import pay4free.in.prostore.ViewHolder.MenuViewHolder;

public class Starting extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextSliderView text;
    FirebaseDatabase database;
    DatabaseReference categories;
    HashMap<String,String> imagelist;
    SliderLayout sliderLayout;
    FirebaseRecyclerAdapter<Dummy, MenuViewHolder> adapter;
    String categoryId;
    String url = "https://www.flipkart.com/samsung-galaxy-j7-duo-black-32-gb/p/itmf4gggjggpygry?pid=MOBF47DRA4RG4R2R&lid=LSTMOBF47DRA4RG4R2RIWAPID&marketplace=FLIPKART&fm=neo/merchandising&iid=M_541f094f-6363-4622-ada3-315aaf3bce6f_1_6HJK5H34O1_MC.MOBF47DRA4RG4R2R&otracker=clp_pmu_v2_Latest+Samsung+mobiles+_1_samsu-categ-a3c72_Samsung+Galaxy+J7+Duo+%28Black%2C+32+GB%29_samsung-mobile-store_MOBF47DRA4RG4R2R_neo/merchandising_0&cid=MOBF47DRA4RG4R2R";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database= FirebaseDatabase.getInstance();
        categories=database.getReference("Dummy");
        toolbar.setTitle("ProStore");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView textView=(TextView)findViewById(R.id.text);
        Typeface tr=Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Raleway-Light.ttf");
        textView.setTypeface(tr);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        setupslider();
        loadlistcategory();




    }

    private void setupslider() {
        sliderLayout=(SliderLayout)findViewById(R.id.Slider);
        imagelist=new HashMap<>();

        final DatabaseReference database=FirebaseDatabase.getInstance().getReference("Dummy");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren())
                {
                    Dummy dummy=data.getValue(Dummy.class);
                    imagelist.put(dummy.getName(),dummy.getImage());
                }
                for(String key:imagelist.keySet())
                {
                    text=new TextSliderView(getApplicationContext());
                    text.image(imagelist.get(key)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                        }
                    });
                    sliderLayout.addSlider(text);
                    database.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);

    }

    private void loadlistcategory() {

        adapter = new FirebaseRecyclerAdapter<Dummy, MenuViewHolder>(Dummy.class, R.layout.dummyitem, MenuViewHolder.class, categories){
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Dummy model, int position) {
                viewHolder.textMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);

                final Dummy local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(CategoryList.this, "" + local.getName(), Toast.LENGTH_SHORT).show();
                      //  Intent foodList = new Intent(Starting.this, FoodList.class);
                       // foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                       // startActivity(foodList);


                    }
                });
            }
        };


        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.starting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(Starting.this,Electronics.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            Intent intent=new Intent(this,Fashion.class);
            intent.putExtra("item","Fashion");
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class Title extends AsyncTask<Void, Void, Void> {
        String title="dcf";

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
                Elements elements=document.select("div[class=_1vC4OE _3qQ9m1]");
                     title=elements.text();

                Log.e("title",title)
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
           // TextView txttitle = (TextView) findViewById(R.id.info);
           // txttitle.setText("FLipkart Price is "+title);

        }
    }
    private class Amazon extends AsyncTask<Void, Void, Void> {
        String title="dcf";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                //Document document = Jsoup.connect(url).get();
                Document document = Jsoup.connect("https://www.amazon.in/Samsung-Galaxy-J7-Duo-offers/dp/B07C336J43/ref=sr_1_2_sspa?s=electronics&ie=UTF8&qid=1527680596&sr=1-2-spons&keywords=J7+duo&psc=1").get();
                Elements elements=document.select("div[id=cerberus-data-metrics]");
                title=elements.attr("data-asin-price");

                Log.e("title",title)
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
           // TextView txttitle = (TextView) findViewById(R.id.info1);
         //   txttitle.setText("Amazon Price is "+title);

        }
    }
}
