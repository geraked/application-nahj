package ir.geraked.nahj.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import ir.geraked.nahj.MainActivity;
import ir.geraked.nahj.R;
import ir.geraked.nahj.database.Model;
import ir.geraked.nahj.database.SqlLiteDbHelper;
import ir.geraked.nahj.recyclerlist.ItemAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    TextView fctTitle, fctCnt;

    SqlLiteDbHelper dbHelper;
    ArrayList<Model> modelList;
    Model count;

    int id, fav;
    String cat, num, title, cnt;

    public ContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        sharedPref = getActivity().getSharedPreferences("ir.geraked.nahj.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);

        fctTitle = view.findViewById(R.id.fct_title);
        fctCnt = view.findViewById(R.id.fct_cnt);

        fctTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, sharedPref.getInt("TEXT_SIZE", 14));
        fctCnt.setTextSize(TypedValue.COMPLEX_UNIT_SP, sharedPref.getInt("TEXT_SIZE", 14));

        dbHelper = new SqlLiteDbHelper(getContext());
        modelList = new ArrayList<>();
        modelList.clear();
        dbHelper.openDataBase();
        modelList = dbHelper.getDetails(0, getArguments().getInt("ITEM_ID"), false);

        count = modelList.get(0);
        switch (count.getCat()) {
            case 1:
                cat = getString(R.string.sermon);
                fctTitle.setBackgroundResource(R.color.colorSermons);
                break;
            case 2:
                cat = getString(R.string.letter);
                fctTitle.setBackgroundResource(R.color.colorLetters);
                break;
            case 3:
                cat = getString(R.string.wisdom);
                fctTitle.setBackgroundResource(R.color.colorWisdoms);
                break;
            case 4:
                cat = getString(R.string.strange_word);
                fctTitle.setBackgroundResource(R.color.colorStranges);
                break;
            case 5:
                cat = getString(R.string.about_book_short);
                fctTitle.setBackgroundResource(R.color.colorAbout);
                break;
            default:
                cat = "";
        }
        id = count.getId();
        num = count.getNum();
        title = count.getTitle();
        cnt = count.getcnt();
        fav = count.getFav();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(cat + " " + num);

        fctTitle.setText(title);
        fctCnt.setText(cnt);

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
        menu.findItem(R.id.action_search).setVisible(false);

        final MenuItem favorite = menu.findItem(R.id.action_favorite);
        if (fav == 1) {
            favorite.setChecked(true);
            favorite.setIcon(R.drawable.ic_star_filled);
            favorite.setTitle(getString(R.string.unadd_to_favorites));
        } else {
            favorite.setChecked(false);
            favorite.setIcon(R.drawable.ic_star_outline);
            favorite.setTitle(getString(R.string.add_to_favorites));
        }

        favorite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (favorite.isChecked()) {
                    count.setFav(0);
                    favorite.setChecked(false);
                    favorite.setIcon(R.drawable.ic_star_outline);
                    favorite.setTitle(getString(R.string.add_to_favorites));

                    Snackbar snackbar = Snackbar.make(getView(), "«" + cat + " " + num + "»" + " از لیست علاقه‌مندی‌ها حذف شد", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    int snackbarTextId = android.support.design.R.id.snackbar_text;
                    TextView textView = snackbarView.findViewById(snackbarTextId);
                    textView.setTextColor(Color.WHITE);
                    if (sharedPref.getBoolean("THEME_NIGHT_MODE", false))
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorNightPrimary));
                    else
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    snackbar.show();

                } else {
                    count.setFav(1);
                    favorite.setChecked(true);
                    favorite.setIcon(R.drawable.ic_star_filled);
                    favorite.setTitle(getString(R.string.unadd_to_favorites));

                    Snackbar snackbar = Snackbar.make(getView(), "«" + cat + " " + num + "»" + " به لیست علاقه‌مندی‌ها اضافه شد", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    int snackbarTextId = android.support.design.R.id.snackbar_text;
                    TextView textView = snackbarView.findViewById(snackbarTextId);
                    textView.setTextColor(Color.WHITE);
                    if (sharedPref.getBoolean("THEME_NIGHT_MODE", false))
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorNightPrimary));
                    else
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    snackbar.show();

                }
                dbHelper.updateFav(count);
                return true;
            }
        });

        MenuItem font = menu.findItem(R.id.action_font);
        font.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_font);
                dialog.setTitle("Custom Alert Dialog");

                final SeekBar seekBar = dialog.findViewById(R.id.df_font_seek_bar);
                Button button = dialog.findViewById(R.id.df_default);

                seekBar.setProgress(sharedPref.getInt("TEXT_SIZE", 14) - 11);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        seekBar.setProgress(i);
                        editor = sharedPref.edit();
                        editor.putInt("TEXT_SIZE", i + 11);
                        editor.apply();
                        fctTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, i + 11);
                        fctCnt.setTextSize(TypedValue.COMPLEX_UNIT_SP, i + 11);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor = sharedPref.edit();
                        editor.remove("TEXT_SIZE");
                        editor.apply();
                        seekBar.setProgress(3);
                        fctTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        fctCnt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    }
                });

                dialog.show();
                return true;
            }
        });

        MenuItem share = menu.findItem(R.id.action_share);
        share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "«" + cat + " " + num + "- " + title + "»" + "\n\n" + cnt);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;
            }
        });

    }

}
