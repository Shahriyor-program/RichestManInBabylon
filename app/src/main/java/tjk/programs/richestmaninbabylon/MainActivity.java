package tjk.programs.richestmaninbabylon;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import tjk.programs.richestmaninbabylon.adapter.VPAdapter;
import tjk.programs.richestmaninbabylon.databinding.ActivityMainBinding;
import tjk.programs.richestmaninbabylon.fragment.ContentFragment;
import tjk.programs.richestmaninbabylon.fragment.InfoFragment;
import tjk.programs.richestmaninbabylon.fragment.MoreFragment;

public class MainActivity extends AppCompatActivity {
    private Adapter adapter;
    ArrayList<String> items;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.color_1));
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new ContentFragment(), "Content");
        vpAdapter.addFragment(new InfoFragment(), "Info");
        vpAdapter.addFragment(new MoreFragment(), "More");
        viewPager.setAdapter(vpAdapter);
    }
}