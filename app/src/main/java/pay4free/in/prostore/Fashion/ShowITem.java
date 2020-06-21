package pay4free.in.prostore.Fashion;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import pay4free.in.prostore.Details;
import pay4free.in.prostore.Interface.ItemClickListener;
import pay4free.in.prostore.Model.Dummy;
import pay4free.in.prostore.R;
import pay4free.in.prostore.ViewHolder.MenuViewHolder;

public class ShowITem extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference categories;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Dummy, MenuViewHolder> adapter;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        database= FirebaseDatabase.getInstance();
        categories=database.getReference(getIntent().getStringExtra("item"));
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview1);
        textView=(TextView)findViewById(R.id.lappi);
        textView.setText(getIntent().getStringExtra("item"));
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Raleway-Light.ttf");
        textView.setTypeface(typeface);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        loadlistcategory();
    }
    private void loadlistcategory() {

        adapter = new FirebaseRecyclerAdapter<Dummy, MenuViewHolder>(Dummy.class, R.layout.dummyitem, MenuViewHolder.class, categories){
            @Override
            protected void populateViewHolder(final MenuViewHolder viewHolder, Dummy model, int position) {
                viewHolder.textMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);

                final Dummy local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent=new Intent(ShowITem.this,Details.class);
                        intent.putExtra("Phone",getIntent().getStringExtra("item"));
                        String s=adapter.getRef(position).getKey();
                        intent.putExtra("Id",s);
                        startActivity(intent);

                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }
}
