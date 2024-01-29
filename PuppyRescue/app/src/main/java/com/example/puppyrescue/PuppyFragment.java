package com.example.puppyrescue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class PuppyFragment extends Fragment
{
    private static final String ARG_PUPPY_ID = "puppy_id";

    private Puppy mPuppy;
    private EditText mDogName;
    private EditText mDogBreed;
    private EditText mDogGender;
    private EditText mDogAge;
    private Button mNewDogButton;

    public static PuppyFragment newInstance(UUID puppyId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PUPPY_ID, puppyId);

        PuppyFragment fragment = new PuppyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        UUID puppyId = (UUID) getArguments().getSerializable(ARG_PUPPY_ID);
        mPuppy = PuppyLab.get(getActivity()).getPuppy(puppyId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_puppy, container, false);

        mDogName = (EditText) v.findViewById(R.id.edt_dogName);
        mDogName.setText(mPuppy.getDogName());
        mDogBreed = (EditText) v.findViewById(R.id.edt_dogBreed);
        mDogBreed.setText(mPuppy.getDogBreed());
        mDogGender = (EditText) v.findViewById(R.id.edt_dogGender);
        mDogGender.setText(mPuppy.getDogGender());
        mDogAge = (EditText) v.findViewById(R.id.edt_dogAge);
        mDogAge.setText(Integer.toString(mPuppy.getDogAge()));


        mNewDogButton = (Button) v.findViewById(R.id.btn_AddSave);
        mNewDogButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                if(mNewDogButton.getText() == getString(R.string.btn_addNewDog))
                {
                    mNewDogButton.setText(getString(R.string.btn_Save));
                    mDogName.setEnabled(true);
                    mDogName.setText("");
                    mDogBreed.setEnabled(true);
                    mDogBreed.setText("");
                    mDogGender.setEnabled(true);
                    mDogGender.setText("");
                    mDogAge.setEnabled(true);
                    mDogAge.setText("");
                }
                else
                {
                    if(mDogName.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(), "Please enter dog name!",Toast.LENGTH_LONG).show();
                        mDogName.clearFocus();
                    }
                    else if(mDogBreed.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(), "Please enter dog's breed!",Toast.LENGTH_LONG).show();
                        mDogBreed.clearFocus();
                    }
                    else if(mDogGender.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(), "Please Enter dog's gender!",Toast.LENGTH_LONG).show();
                        mDogGender.clearFocus();
                    }
                    else if(mDogAge.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(), "Please enter dog's age!",Toast.LENGTH_LONG).show();
                        mDogAge.clearFocus();
                    }
                    else
                    {
                        PuppyLab puppyLab = PuppyLab.get(getContext());

                        Puppy puppy = new Puppy();
                        puppy.setDogName(mDogName.getText().toString());
                        puppy.setDogBreed(mDogBreed.getText().toString());
                        puppy.setTitle();
                        puppy.setDogGender(mDogGender.getText().toString());
                        try
                        {
                            if(Integer.parseInt(mDogAge.getText().toString()) > 0)
                            {
                                puppy.setDogAge(Integer.parseInt(mDogAge.getText().toString()));
                                puppyLab.addNewPuppy(puppy);

                                getActivity().onBackPressed();
                                mNewDogButton.setText(getString(R.string.btn_addNewDog));
                                mDogName.setEnabled(false);
                                mDogBreed.setEnabled(false);
                                mDogGender.setEnabled(false);
                                mDogAge.setEnabled(false);
                            }
                            else{
                                Toast.makeText(getContext(), "Please enter dog's age greater than zero!",Toast.LENGTH_LONG).show();
                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getContext(), "Please enter digit as dog's age!",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        return v;
    }
}
