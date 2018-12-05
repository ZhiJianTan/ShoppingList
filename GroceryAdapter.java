package com.example.zaphk.shoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    //cursor can be used to get data out of database.
    public GroceryAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        //for showing name later
        public TextView nameText;
        public TextView countText;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText= itemView.findViewById(R.id.textview_name_item);
            countText=itemView.findViewById(R.id.textview_amount_item);
        }
    }


    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grocery_item, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder( GroceryViewHolder holder, int position) {
        //ensure cursor can move to existing position
        if (!mCursor.moveToPosition(position)){
            return;
        }

        //reaching the value with the cursor
        String name = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_AMOUNT));
        long id = mCursor.getLong(mCursor.getColumnIndex(GroceryContract.GroceryEntry._ID));


        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));
        //setting tag to holder so we can identify it
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        //return the amount of items in database.
        return mCursor.getCount();
    }

    //for swapping to a new cursor everytime we call a new item
    public void swapCursor (Cursor newCursor){
        if (mCursor!=null){
            //getting rid of old cursor
            mCursor.close();
        }

        mCursor = newCursor;
        if (newCursor !=null){
            notifyDataSetChanged();
        }
    }
}