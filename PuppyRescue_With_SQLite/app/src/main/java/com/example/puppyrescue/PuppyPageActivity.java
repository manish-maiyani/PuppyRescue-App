package com.example.puppyrescue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class PuppyPageActivity extends AppCompatActivity
{

    private static final String EXTRA_PUPPY_ID =
            "com.example.puppyrescue.puppyrescue.puppy_id";


    private ViewPager mViewPager;
    private List<Puppy> mPuppies;

    static boolean temp;

    public static Intent newIntent(Context packageContext, UUID puppyId,Boolean temp2)
    {
        temp = temp2;
        Intent intent = new Intent(packageContext, PuppyPageActivity.class);
        intent.putExtra(EXTRA_PUPPY_ID, puppyId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puppy_pager);

        UUID puppyId = (UUID) getIntent().getSerializableExtra(EXTRA_PUPPY_ID);

        mViewPager = (ViewPager) findViewById(R.id.puppy_view_pager);

        mPuppies = PuppyLab.get(this).getPuppies();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager)
        {

            @Override
            public Fragment getItem(int position)
            {
                Puppy puppy = mPuppies.get(position);
                return PuppyFragment.newInstance(puppy.getId(), temp);
            }

            @Override
            public int getCount()
            {
                return mPuppies.size();
            }
        });

        for (int i = 0; i < mPuppies.size(); i++)
        {
            if (mPuppies.get(i).getId().equals(puppyId))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
