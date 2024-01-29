package com.example.puppyrescue;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PuppyLab
{
    private List<Puppy> mPuppies;

    private static PuppyLab sPuppyLab;

    public static PuppyLab get(Context context)
    {
        if (sPuppyLab == null) {
            sPuppyLab = new PuppyLab(context);
        }
        return sPuppyLab;
    }

    public PuppyLab(Context context)
    {
        mPuppies = new ArrayList<>();

        Puppy [] puppy = new Puppy[4];

        puppy[0] = new Puppy();
        puppy[0].setDogName("Molly");
        puppy[0].setDogBreed("Labrador");
        puppy[0].setTitle();
        puppy[0].setDogAge(6);
        puppy[0].setDogGender("Female");
        mPuppies.add(puppy[0]);

        puppy[1] = new Puppy();
        puppy[1].setDogName("Butch");
        puppy[1].setDogBreed("German Shepherd");
        puppy[1].setTitle();
        puppy[1].setDogAge(3);
        puppy[1].setDogGender("Male");
        mPuppies.add(puppy[1]);

        puppy[2] = new Puppy();
        puppy[2].setDogName("Milo");
        puppy[2].setDogBreed("Kelpie");
        puppy[2].setTitle();
        puppy[2].setDogAge(8);
        puppy[2].setDogGender("Female");
        mPuppies.add(puppy[2]);

        puppy[3] = new Puppy();
        puppy[3].setDogName("Odie");
        puppy[3].setDogBreed("Poodle");
        puppy[3].setTitle();
        puppy[3].setDogAge(1);
        puppy[3].setDogGender("Male");
        mPuppies.add(puppy[3]);
    }

    public void addNewPuppy(Puppy Puppy)
    {
        Puppy newpuppy = Puppy;
        mPuppies.add(newpuppy);
    }

    public List<Puppy> getPuppies()
    {
        return mPuppies;
    }

    public Puppy getPuppy(UUID id)
    {
        for (Puppy puppy : mPuppies)
        {
            if (puppy.getId().equals(id))
            {
                return puppy;
            }
        }

        return null;
    }

}
