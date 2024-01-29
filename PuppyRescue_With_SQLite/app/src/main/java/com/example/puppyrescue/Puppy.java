package com.example.puppyrescue;

import java.util.UUID;

public class Puppy
{
    private UUID mId;
    private String mDogName;
    private String mDogBreed;
    private String mDogGender;
    private int mDogAge;

    private double mLatitude;
    private double mLongitude;

    public Puppy()
    {
        this(UUID.randomUUID());
    }

    public Puppy(UUID id)
    {
        mId = id;
        mDogAge = 0;
    }

    public UUID getId()
    {
        return mId;
    }

    public void setId(UUID id)

    {
        mId = id;
    }

    public String getDogName()
    {
        return mDogName;
    }

    public void setDogName(String dogName) {
        mDogName = dogName;
    }

    public String getDogBreed() {
        return mDogBreed;
    }

    public void setDogBreed(String dogBreed) {
        mDogBreed = dogBreed;
    }

    public String getDogGender() {
        return mDogGender;
    }

    public void setDogGender(String dogGender) {
        mDogGender = dogGender;
    }

    public int getDogAge()
    {
        return mDogAge;
    }

    public void setDogAge(int age) {
        mDogAge = age;
    }

    public double getLatitude()
    {
        return mLatitude;
    }

    public void setLatitude(double latitude)
    {
        mLatitude = latitude;
    }

    public double getLongitude()
    {
        return mLongitude;
    }

    public void setLongitude(double longitude)
    {
        mLongitude = longitude;
    }
}
