package pay4free.in.prostore;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import pay4free.in.prostore.Interface.ItemClickListener;
import pay4free.in.prostore.Model.Dummy;
import pay4free.in.prostore.ViewHolder.MenuViewHolder;

public class Electronics extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
public int image[]={R.drawable.mi,R.drawable.apple,R.drawable.samung,R.drawable.honor};
    public String text[]={"  Mi  ","Apple","Samsung","Honor"};
ImageView mi,apple,honor,samsung,oppo;
    FirebaseDatabase database;
    DatabaseReference categories;
    String s1;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Dummy, MenuViewHolder> adapter;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        database= FirebaseDatabase.getInstance();
        categories=database.getReference("Mixphones");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
  mi=(ImageView)findViewById(R.id.mi);
    oppo=(ImageView)findViewById(R.id.oppo);
    apple=(ImageView)findViewById(R.id.apple);
    honor=(ImageView)findViewById(R.id.honor);
    samsung=(ImageView)findViewById(R.id.samsung);
    TextView textView=(TextView)findViewById(R.id.text);
    Typeface tr=Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Raleway-Light.ttf");
    textView.setTypeface(tr);
        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Electronics.this,Phones.class);
                intent.putExtra("Phone","Apple");
                startActivity(intent);

            }
        });
    honor.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Electronics.this,Phones.class);
            intent.putExtra("Phone","Honor");
            startActivity(intent);

        }
    });
    samsung.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Electronics.this,Phones.class);
            intent.putExtra("Phone","Samsung");
            startActivity(intent);

        }
    });
    oppo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Electronics.this,Phones.class);
            intent.putExtra("Phone","Oppo");
            startActivity(intent);

        }
    });

    recyclerView=(RecyclerView)findViewById(R.id.recyclerview1);
    recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    loadlistcategory();
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
                        Intent showdetail = new Intent(Electronics.this, Details.class);
                        String s=adapter.getRef(position).getKey();
                        showdetail.putExtra("Phone","Mixphones");
                        showdetail.putExtra("Id", s);
                        startActivity(showdetail);

                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.electronics, menu);
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
        } else if (id == R.id.nav_gallery) {
            Intent intent=new Intent(Electronics.this,Laptop.class);
intent.putExtra("item","Laptops");
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent=new Intent(Electronics.this,Laptop.class);
            intent.putExtra("item","Accessories");
            startActivity(intent);


        } else if (id == R.id.nav_manage) {

            Intent intent=new Intent(Electronics.this,Laptop.class);
            intent.putExtra("item","AudioVideo");
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(Electronics.this,Laptop.class);
            intent.putExtra("item","CameraAccessories");
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent=new Intent(Electronics.this,Laptop.class);
            intent.putExtra("item","Tablets");
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
