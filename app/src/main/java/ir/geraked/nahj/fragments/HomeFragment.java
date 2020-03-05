package ir.geraked.nahj.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ir.geraked.nahj.MainActivity;
import ir.geraked.nahj.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));

        CardView fhcWisdoms = view.findViewById(R.id.fhc_wisdoms);
        CardView fhcSermons = view.findViewById(R.id.fhc_sermons);
        CardView fhcLetters = view.findViewById(R.id.fhc_letters);
        CardView fhcStrangeWords = view.findViewById(R.id.fhc_strange_words);
        CardView fhcAboutBook = view.findViewById(R.id.fhc_about_book);

        fhcWisdoms.setOnClickListener(this);
        fhcSermons.setOnClickListener(this);
        fhcLetters.setOnClickListener(this);
        fhcStrangeWords.setOnClickListener(this);
        fhcAboutBook.setOnClickListener(this);

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
        menu.findItem(R.id.action_favorite).setVisible(false);
        menu.findItem(R.id.action_font).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
    }

    int cat;
    String toolbarTitle;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fhc_wisdoms:
                cat = 3;
                toolbarTitle = getString(R.string.wisdoms);
                break;
            case R.id.fhc_sermons:
                cat = 1;
                toolbarTitle = getString(R.string.sermons);
                break;
            case R.id.fhc_letters:
                cat = 2;
                toolbarTitle = getString(R.string.letters);
                break;
            case R.id.fhc_strange_words:
                cat = 4;
                toolbarTitle = getString(R.string.strange_words);
                break;
            case R.id.fhc_about_book:
                cat = 5;
                toolbarTitle = getString(R.string.about_book);
                break;
            default:
                cat = 0;
                toolbarTitle = getString(R.string.app_name);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Bundle bundle = new Bundle();
                bundle.putInt("CAT", cat);
                bundle.putString("TOOLBAR_TITLE", toolbarTitle);
                Fragment fragment = new ListFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frg_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        }, 100);
    }

}