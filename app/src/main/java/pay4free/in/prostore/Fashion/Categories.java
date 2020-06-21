package pay4free.in.prostore.Fashion;

/**
 * Created by AAKASH on 27-06-2018.
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import pay4free.in.prostore.Interface.ItemClickListener;
import pay4free.in.prostore.Model.FashionList;
import pay4free.in.prostore.Phones;
import pay4free.in.prostore.R;
import pay4free.in.prostore.ViewHolder.MenuViewHolder;


public class Categories extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference categories;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<FashionList, MenuViewHolder> adapter;
    TextView textView;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        database= FirebaseDatabase.getInstance();
        categories=database.getReference(getIntent().getStringExtra("category"));
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview1);
        textView=(TextView)findViewById(R.id.lappi);
        textView.setText(getIntent().getStringExtra("item"));
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Raleway-Light.ttf");
        textView.setTypeface(typeface);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        if (getIntent() != null) {
            id = getIntent().getStringExtra("select");
        }
        if (!id.isEmpty() && id != null) {
            loadlistcategory(id);
        }
    }
    private void loadlistcategory(String id) {

        adapter = new FirebaseRecyclerAdapter<FashionList,MenuViewHolder>(FashionList.class, R.layout.dummyitem, MenuViewHolder.class, categories.orderByChild("MenuId").equalTo(id)){
            @Override
            protected void populateViewHolder(final MenuViewHolder viewHolder, FashionList model, int position) {
                viewHolder.textMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);

                final FashionList local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent=new Intent(Categories.this,ShowITem.class);
                        intent.putExtra("item",viewHolder.textMenuName.getText().toString());
                        startActivity(intent);

                    }
                });
            }


        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }
}

