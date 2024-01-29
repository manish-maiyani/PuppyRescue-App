package com.example.puppyrescue.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.puppyrescue.Puppy;

import com.example.puppyrescue.database.DbSchema.PuppyTable;

import java.util.UUID;

public class DbCursorWrapper extends CursorWrapper
{
    public DbCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Puppy getPuppy()
    {
        String uuidString = getString(getColumnIndex(PuppyTable.Cols.UUID));
        String Name = getString(getColumnIndex(PuppyTable.Cols.NAME));
        String Breed = getString(getColumnIndex(PuppyTable.Cols.BREED));
        String Gender = getString(getColumnIndex(PuppyTable.Cols.GENDER));
        int Age = getInt(getColumnIndex(PuppyTable.Cols.AGE));
        long Lat = getInt(getColumnIndex(PuppyTable.Cols.LAT));
        int Lon = getInt(getColumnIndex(PuppyTable.Cols.LON));

        Puppy puppy = new Puppy(UUID.fromString(uuidString));

        puppy.setDogName(Name);
        puppy.setDogBreed(Breed);
        puppy.setDogGender(Gender);
        puppy.setDogAge(Age);

        puppy.setLatitude(Lat);
        puppy.setLongitude(Lon);

        return puppy;
    }
}
