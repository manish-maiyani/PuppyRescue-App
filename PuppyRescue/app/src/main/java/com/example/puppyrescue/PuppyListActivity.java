package com.example.puppyrescue;

import androidx.fragment.app.Fragment;

public class PuppyListActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new PuppyListFragment();
    }
}
