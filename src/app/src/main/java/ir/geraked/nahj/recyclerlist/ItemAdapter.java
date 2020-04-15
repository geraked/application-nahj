package ir.geraked.nahj.recyclerlist;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.geraked.nahj.fragments.ContentFragment;
import ir.geraked.nahj.R;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<Item> itemList;
    private Context mContext;

    public ItemAdapter(List<Item> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View aView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
        return new MyViewHolder(aView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Item aItem = itemList.get(position);

        holder.aCat.setText(aItem.getuCat());
        holder.aCount.setText(aItem.getuCount());
        holder.aTitle.setText(aItem.getuTitle());
        holder.aShort.setText(aItem.getuShort());

        if (aItem.getuCat().equals(mContext.getResources().getString(R.string.sermon))) {
            holder.aCat.setBackgroundResource(R.color.colorSermons);
            holder.aCount.setBackgroundResource(R.color.colorSermonsDark);
            holder.aLine.setBackgroundResource(R.color.colorSermonsDark);
        } else if (aItem.getuCat().equals(mContext.getResources().getString(R.string.letter))) {
            holder.aCat.setBackgroundResource(R.color.colorLetters);
            holder.aCount.setBackgroundResource(R.color.colorLettersDark);
            holder.aLine.setBackgroundResource(R.color.colorLettersDark);
        } else if (aItem.getuCat().equals(mContext.getResources().getString(R.string.wisdom))) {
            holder.aCat.setBackgroundResource(R.color.colorWisdoms);
            holder.aCount.setBackgroundResource(R.color.colorWisdomsDark);
            holder.aLine.setBackgroundResource(R.color.colorWisdomsDark);
        } else if (aItem.getuCat().equals(mContext.getResources().getString(R.string.strange_word))) {
            holder.aCat.setBackgroundResource(R.color.colorStranges);
            holder.aCount.setBackgroundResource(R.color.colorStrangesDark);
            holder.aLine.setBackgroundResource(R.color.colorStrangesDark);
        } else if (aItem.getuCat().equals(mContext.getResources().getString(R.string.about_book_short))) {
            holder.aCat.setBackgroundResource(R.color.colorAbout);
            holder.aCount.setBackgroundResource(R.color.colorAboutDark);
            holder.aLine.setBackgroundResource(R.color.colorAboutDark);
        } else {
            holder.aCat.setBackgroundResource(R.color.colorPrimary);
            holder.aCount.setBackgroundResource(R.color.colorPrimaryDark);
            holder.aLine.setBackgroundResource(R.color.colorPrimaryDark);
        }


        holder.aItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //Toast.makeText(mContext, "Item: " + position, Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Bundle bundle = new Bundle();
                        bundle.putInt("ITEM_ID", aItem.getuId());
                        Fragment myFragment = new ContentFragment();
                        myFragment.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frg_container, myFragment).addToBackStack(null).commit();

                    }
                }, 100);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView aCat;
        TextView aCount;
        TextView aTitle;
        TextView aShort;
        View aLine;
        LinearLayout aItem;


        MyViewHolder(View itemView) {
            super(itemView);
            aCat = itemView.findViewById(R.id.cli_cat);
            aCount = itemView.findViewById(R.id.cli_count);
            aTitle = itemView.findViewById(R.id.cli_title);
            aShort = itemView.findViewById(R.id.cli_short);
            aLine = itemView.findViewById(R.id.cli_line);
            aItem = itemView.findViewById(R.id.cli_card);
        }
    }

    public void setFilter(ArrayList<Item> newList) {
        itemList = new ArrayList<>();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

}