package ir.geraked.nahj.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.geraked.nahj.MainActivity;
import ir.geraked.nahj.R;
import ir.geraked.nahj.database.Model;
import ir.geraked.nahj.database.SqlLiteDbHelper;
import ir.geraked.nahj.recyclerlist.Item;
import ir.geraked.nahj.recyclerlist.ItemAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    RecyclerView myRecycler;
    public static List<Item> mItem = new ArrayList<>();
    public static ItemAdapter mAdapter;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        myRecycler = view.findViewById(R.id.fl_recycler);
        mAdapter = new ItemAdapter(mItem, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        myRecycler.setLayoutManager(mLayoutManager);
        myRecycler.setItemAnimator(new DefaultItemAnimator());
        myRecycler.setHasFixedSize(true);
        myRecycler.setAdapter(mAdapter);
        setData();

        return view;
    }

    // Handle Toolbar Items in a Fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_favorite).setVisible(false);
        menu.findItem(R.id.action_font).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.search) + "...");
        if (!getArguments().getBoolean("FAV")) {
            switch (getArguments().getInt("CAT")) {
                case 1:
                    searchView.setQueryHint(getString(R.string.search_in_title) + " " + getString(R.string.sermons) + "...");
                    break;
                case 2:
                    searchView.setQueryHint(getString(R.string.search_in_title) + " " + getString(R.string.letters) + "...");
                    break;
                case 3:
                    searchView.setQueryHint(getString(R.string.search_in_title) + " " + getString(R.string.wisdoms) + "...");
                    break;
                case 4:
                    searchView.setQueryHint(getString(R.string.search_in_title) + " " + getString(R.string.strange_words) + "...");
                    break;
                case 5:
                    searchView.setQueryHint(getString(R.string.search_in_title) + " " + getString(R.string.about_book_short) + "...");
                    break;
            }
        }
    }

    private void setData() {

        String toolbarTitle = (getArguments().getString("TOOLBAR_TITLE") != null) ? getArguments().getString("TOOLBAR_TITLE") : getString(R.string.favorites);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolbarTitle);

        SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(getContext());
        ArrayList<Model> modelList = new ArrayList<>();
        modelList.clear();
        mItem.clear();
        dbHelper.openDataBase();
        modelList = dbHelper.getDetails(getArguments().getInt("CAT"), 0, getArguments().getBoolean("FAV"));

        for (int x = 0; x < modelList.size(); x++) {
            Model count = modelList.get(x);
            int id;
            String cat, num, title, cnt;
            id = count.getId();
            switch (count.getCat()) {
                case 1:
                    cat = getString(R.string.sermon);
                    break;
                case 2:
                    cat = getString(R.string.letter);
                    break;
                case 3:
                    cat = getString(R.string.wisdom);
                    break;
                case 4:
                    cat = getString(R.string.strange_word);
                    break;
                case 5:
                    cat = getString(R.string.about_book_short);
                    break;
                default:
                    cat = "";
            }
            num = count.getNum();
            title = count.getTitle();
            cnt = count.getcnt();

//            count.setTitle(title);
//            count.setCnt(cnt);
//            dbHelper.updateTitle(count);
//            dbHelper.updateCnt(count);

            int cntMaxLength = (cnt.length() < 150) ? cnt.length() : 150;
            mItem.add(new Item(id, cat, num, title, cnt.substring(0, cntMaxLength)));
        }

        mAdapter.notifyDataSetChanged();
    }

}
