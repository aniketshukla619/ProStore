package pay4free.in.prostore;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import java.util.HashMap;

import pay4free.in.prostore.Interface.ItemClickListener;
import pay4free.in.prostore.Model.Dummy;
import pay4free.in.prostore.R;
import pay4free.in.prostore.ViewHolder.MenuViewHolder;





public class Phones extends AppCompatActivity {
    RecyclerView recyclerView;
    TextSliderView text;
    FirebaseDatabase database;
    DatabaseReference categories;
    HashMap<String,String> imagelist;
    SliderLayout sliderLayout;
    FirebaseRecyclerAdapter<Dummy, MenuViewHolder> adapter;
    String s1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honor);

        TextView textView=(TextView)findViewById(R.id.text);
        Typeface tr=Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Raleway-Light.ttf");
        textView.setTypeface(tr);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        s1=getIntent().getStringExtra("Phone");
        database= FirebaseDatabase.getInstance();
        textView.setText(getIntent().getStringExtra("Phone"));
        categories=database.getReference(getIntent().getStringExtra("Phone"));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        setupslider();
        loadlistcategory();
    }
    private void setupslider() {
        sliderLayout=(SliderLayout)findViewById(R.id.Slider);
        imagelist=new HashMap<>();

        final DatabaseReference database= FirebaseDatabase.getInstance().getReference(s1);

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
        imagelist.clear();

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
                        Intent showdetail = new Intent(Phones.this, Details.class);
                        String s=adapter.getRef(position).getKey();
                        showdetail.putExtra("Phone",s1);
                        showdetail.putExtra("Id",s);
                        startActivity(showdetail);

                    }
                });
            }
        };

adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }
}

