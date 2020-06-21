package pay4free.in.prostore.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pay4free.in.prostore.Interface.ItemClickListener;
import pay4free.in.prostore.R;


/**
 * Created by AAKASH on 09-10-2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
   public TextView textMenuName;
    public ImageView imageView;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        textMenuName=(TextView)itemView.findViewById(R.id.menu_name);
        imageView=(ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
