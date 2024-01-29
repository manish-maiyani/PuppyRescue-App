/*
Working in pair

Student Name : (1) Patalia Rahul Ashokbhai - 12118767
               (2) Maiyani Manish Damajibhai - 12118792
 */

package com.example.puppyrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class PuppyActivity extends SingleFragmentActivity
{
    private static final String EXTRA_PUPPY_ID =
            "com.example.puppyrescue.puppyrescue.puppy_id";

    public static Intent newIntent(Context packageContext, UUID puppyId)
    {
        Intent intent = new Intent(packageContext, PuppyActivity.class);
        intent.putExtra(EXTRA_PUPPY_ID, puppyId);
        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        UUID puppyId = (UUID) getIntent().getSerializableExtra(EXTRA_PUPPY_ID);
        return PuppyFragment.newInstance(puppyId);
    }
}