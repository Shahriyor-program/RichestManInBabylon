package tjk.programs.richestmaninbabylon;

import android.os.Bundle;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import tjk.programs.richestmaninbabylon.adapter.VPAdapter;
import tjk.programs.richestmaninbabylon.fragment.ContentFragment;
import tjk.programs.richestmaninbabylon.fragment.InfoFragment;
import tjk.programs.richestmaninbabylon.fragment.MoreFragment;
import tjk.programs.richestmaninbabylon.model.Root;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Root> items = new ArrayList<>();
    private TabLayout tabLayout;


    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.color_1));
//        items = (ArrayList<String>) Arrays.asList(getResources().getStringArray(R.array.AudioContent));
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Root>>() {
        }.getType();
        items = gson.fromJson(loadJSONFromAsset(), collectionType);

//        items= gson.fromJson(loadJSONFromAsset(), (Type) Root.class);
        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new ContentFragment(), "Content");
        vpAdapter.addFragment(new InfoFragment(), "Info");
        vpAdapter.addFragment(new MoreFragment(), "More");
        viewPager.setAdapter(vpAdapter);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}