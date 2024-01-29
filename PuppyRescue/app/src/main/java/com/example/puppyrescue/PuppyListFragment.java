package com.example.puppyrescue;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PuppyListFragment extends Fragment
{
    private RecyclerView mPuppyRecyclerView;
    private PuppyAdapter mAdapter;

    private TextView mDogNameAndBreed;
    private TextView mDogAgeAndGender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_puppy_list, container, false);

        mPuppyRecyclerView = (RecyclerView) view.findViewById(R.id.puppy_recycler_view);
        mPuppyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    private void updateUI()
    {
        PuppyLab puppyLab = PuppyLab.get(getActivity());
        List<Puppy> puppies = puppyLab.getPuppies();

        if(mAdapter == null)
        {
            mAdapter = new PuppyAdapter(puppies);
            mPuppyRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class PuppyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Puppy mPuppy;

        public PuppyHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_puppy, parent, false));
            itemView.setOnClickListener(this);
            mDogNameAndBreed = (TextView) itemView.findViewById(R.id.puppy_name_breed);
            mDogAgeAndGender = (TextView) itemView.findViewById(R.id.puppy_age_gen);

        }

        public void bind(Puppy puppy)
        {
            mPuppy = puppy;
            mDogNameAndBreed.setText(mPuppy.getTitle());
            mDogAgeAndGender.setText(mPuppy.getDogAge() +" year old "+ mPuppy.getDogGender());
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = PuppyPageActivity.newIntent(getActivity(), mPuppy.getId());
            startActivity(intent);
        }
    }

    private class PuppyAdapter extends RecyclerView.Adapter<PuppyHolder>
    {

        private List<Puppy> mPuppies;

        public PuppyAdapter(List<Puppy> Puppies)
        {
            mPuppies = Puppies;
        }

        @Override
        public PuppyHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PuppyHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PuppyHolder holder, int position)
        {
            Puppy puppy = mPuppies.get(position);
            holder.bind(puppy);
        }

        @Override
        public int getItemCount()
        {
            return mPuppies.size();
        }

    }

}
