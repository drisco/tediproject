package com.book.pharmacie.fagments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.pharmacie.MainActivity;
import com.book.pharmacie.R;
import com.book.pharmacie.adapter.NewsAdapter;
import com.book.pharmacie.model.NewsItem;

import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class FragmentAccueil extends Fragment {

    private SearchView searchView;

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsList;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acceuil, container, false);

        searchView =getActivity().findViewById(R.id.searchView);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        newsList = new ArrayList<>();
// Ajouter des exemples de données avec des liens d'images
        newsList.add(new NewsItem("COVID-19 : Symptômes et prévention", "Les symptômes courants incluent la fièvre et la toux.", "https://www.pngitem.com/pimgs/m/103-1035945_coronavirus-covid-19-illustration.png", " 5 min read","",""));
        newsList.add(new NewsItem("Maladies cardiaques : Signes d'alerte", "Les premiers symptômes incluent des douleurs thoraciques.", "https://images.rawpixel.com/image_png_800/czNmcy1wcml2YXRlL3Jhd3BpeGVsX2ltYWdlcy93ZWJzaXRlX2NvbnRlbnQvam9iNjc5LTEwMy1wLWwxNjd4ZGdvLnBuZw.png", "3 min read","",""));
        newsList.add(new NewsItem("Gestion du diabète", "Surveillance des niveaux de sucre dans le sang.", "https://www.diabetologie-pratique.com/sites/www.diabetologie-pratique.com/files/styles/une_journal_578_383/public/images/article_journal/pancreas_178787544_1.png?itok=RoqBrqVu", " 4 min read","",""));
        newsList.add(new NewsItem("Vaccination", "La vaccination aide à prévenir la propagation des maladies.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbaY3ktU7Ap8-boOo3l_hGh9cTHFbQ5TtMCg&s", " 2 min read","",""));
        newsList.add(new NewsItem("Santé mentale : Dépression", "Les signes incluent une tristesse persistante.", "https://www.britishjournalofcommunitynursing.com/media/qlga1gyl/mental_health_02.png?width=1002&height=564&bgcolor=White&v=1da9b0438995bc0", " 6 min read","",""));
        newsList.add(new NewsItem("Alimentation saine", "Une alimentation équilibrée doit inclure des fruits et légumes.", "https://w7.pngwing.com/pngs/512/1024/png-transparent-eating-healthy-diet-food-healthy-food-natural-foods-food-weight-loss-thumbnail.png", " 5 min read","",""));
        newsList.add(new NewsItem("Cancer : Détection précoce", "La détection précoce améliore les chances de traitement.", "https://png.pngtree.com/png-clipart/20210129/ourmid/pngtree-stem-cell-cancer-virus-png-image_2844867.jpg", " 4 min read","",""));
        newsList.add(new NewsItem("Obésité : Risques", "L'obésité augmente le risque de maladies cardiaques.", "https://image.similarpng.com/very-thumbnail/2020/10/Fat-man-playing-sport-isolated-on-transparent-background-PNG.png", " 3 min read","",""));
        newsList.add(new NewsItem("Asthme", "Les déclencheurs incluent les allergènes et la fumée.", "https://png.pngtree.com/png-clipart/20210415/ourmid/pngtree-world-asthma-day-medical-mold-png-image_3221210.jpg", " 2 min read","",""));
        newsList.add(new NewsItem("Hypertension", "Surveillance régulière de la tension artérielle.", "https://png.pngtree.com/png-vector/20240203/ourlarge/pngtree-hypertension-3d-illustration-png-image_11591845.png ", "6 min read","",""));


        newsAdapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(newsAdapter);


        // Ajoutez le ScrollListener pour cacher la barre de navigation
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Scrolling down, hide the navigation bar
                    ((MainActivity) getActivity()).hideBottomNavigation();
                } else if (dy < 0) {
                    // Scrolling up, show the navigation bar
                    ((MainActivity) getActivity()).showBottomNavigation();
                }
            }
        });
        return view;

    }
}
