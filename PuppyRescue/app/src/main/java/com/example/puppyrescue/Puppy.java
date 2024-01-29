package com.example.puppyrescue;

import java.util.UUID;

public class Puppy
{
    private UUID mId;
    private String mTitle;
    private String mDogName;
    private String mDogBreed;
    private String mDogGender;
    private int mDogAge;

    public Puppy()
    {
        mId = UUID.randomUUID();
    }

    public UUID getId()
    {
        return mId;
    }

    public void setId(UUID id)

    {
        mId = id;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle()
    {
        this.mTitle =  this.getDogName() +" is a " + this.getDogBreed();
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

    public int getDogAge() {
        return mDogAge;
    }

    public void setDogAge(int age) {
        mDogAge = age;
    }
}
