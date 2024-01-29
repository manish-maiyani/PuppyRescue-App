package com.example.puppyrescue;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.UUID;

public class PuppyFragment extends Fragment
{
    private static final String ARG_PUPPY_ID = "puppy_id";

    private Puppy mPuppy;
    private EditText mDogName;
    private EditText mDogBreed;
    private EditText mDogGender;
    private EditText mDogAge;
    private Button mSaveButton;
    private Button mDelButton;
    private Button mSetLocButton;
    private Button mMapButton;
    private Button mEmailButton;

    private double mLatitude;
    private double mLongitude;

    FusedLocationProviderClient mFusedLocationClient;

    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    private static boolean temp;

    UUID puppyId;

    public static PuppyFragment newInstance(UUID puppyId, boolean yes)
    {
        temp = yes;
        Bundle args = new Bundle();
        args.putSerializable(ARG_PUPPY_ID, puppyId);
        PuppyFragment fragment = new PuppyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(temp)
        {
            puppyId = (UUID) getArguments().getSerializable(ARG_PUPPY_ID);
            mPuppy = PuppyLab.get(getActivity()).getPuppy(puppyId);
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if (mFusedLocationClient != null)
        {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_puppy, container, false);

        mDogName = (EditText) v.findViewById(R.id.edt_dogName);
        mDogBreed = (EditText) v.findViewById(R.id.edt_dogBreed);
        mDogGender = (EditText) v.findViewById(R.id.edt_dogGender);
        mDogAge = (EditText) v.findViewById(R.id.edt_dogAge);

        if(temp)
        {
            mDogName.setText(mPuppy.getDogName());
            mDogBreed.setText(mPuppy.getDogBreed());
            mDogGender.setText(mPuppy.getDogGender());
            mDogAge.setText(Integer.toString(mPuppy.getDogAge()));
            mLongitude = mPuppy.getLongitude();
            mLatitude = mPuppy.getLatitude();
        }

        mMapButton = (Button) v.findViewById(R.id.btn_ShowMap);
        mMapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mLatitude != 0 && mLongitude != 0)
                {
                    if(!mDogName.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(), mLatitude +"--"+mLongitude, Toast.LENGTH_LONG ).show();
                        Intent intent = new Intent(getActivity(), MapsActivity.class);

                        intent.putExtra(MapsActivity.EXTRA_NAME, mDogName.getText().toString());
                        intent.putExtra(MapsActivity.EXTRA_LAT, mLatitude);
                        intent.putExtra(MapsActivity.EXTRA_LON, mLongitude);

                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Please enter dog name!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Please set the Location!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mSetLocButton = (Button) v.findViewById(R.id.btn_SetLocation);
        mSetLocButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (hasLocationPermission())
                {
                    requestLocation();
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSIONS);
                }

            }
        });


        mEmailButton = (Button) v.findViewById(R.id.btn_Email);
        mEmailButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
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
                    String mTitle = "Puppy Rescue";
                    String mMessage = "Puppy Rescue has a dog you may be interested in adopting\n" +
                            mDogName.getText().toString() + " is "
                            + mDogAge.getText().toString() + " year old "
                            + mDogGender.getText().toString() + " "
                            + mDogBreed.getText().toString();

                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
                    intent.putExtra(Intent.EXTRA_SUBJECT, mTitle);
                    intent.putExtra(Intent.EXTRA_TEXT, mMessage);
                    startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                }
            }
        });

        mSaveButton = (Button) v.findViewById(R.id.btn_Save);
        mSaveButton.setEnabled(!temp);
        mSaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
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
                    else if(mLatitude == 0 && mLongitude == 0)
                    {
                        Toast.makeText(getContext(), "Please set the Location!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        PuppyLab puppyLab = PuppyLab.get(getContext());

                        Puppy puppy = new Puppy();
                        puppy.setDogName(mDogName.getText().toString());
                        puppy.setDogBreed(mDogBreed.getText().toString());
                        puppy.setDogGender(mDogGender.getText().toString());
                        puppy.setLongitude(mLongitude);
                        puppy.setLatitude(mLatitude);
                        try
                        {
                            if(Integer.parseInt(mDogAge.getText().toString()) > 0)
                            {
                                puppy.setDogAge(Integer.parseInt(mDogAge.getText().toString()));
                                puppyLab.addNewPuppy(puppy);

                                getActivity().onBackPressed();
                            }
                            else{
                                Toast.makeText(getContext(), "Please enter dog's age greater than zero!",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e)
                        {
                            getActivity().onBackPressed();
                        }
                    }
            }
        });

        mDelButton = (Button) v.findViewById(R.id.btn_Delete);
        mDelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PuppyLab puppyLab = PuppyLab.get(getContext());
                puppyLab.deletePuppy(puppyId);
                getActivity().onBackPressed();
            }
        });


                return v;
    }

    LocationCallback mLocationCallback = new LocationCallback()
    {
        @Override
        public void onLocationResult(LocationResult locationResult)
        {
            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0)
            {
                Location location = locationList.get(locationList.size() - 1);

                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();
                Toast.makeText(getContext(),"Location has been set successfully!",Toast.LENGTH_LONG).show();
            }
        }
    };


    private void requestLocation()
    {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setNumUpdates(1);
        mLocationRequest.setInterval(0);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    private boolean hasLocationPermission()
    {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode != REQUEST_LOCATION_PERMISSIONS)
        {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        else if (hasLocationPermission())
        {
            requestLocation();
        }
    }

}
