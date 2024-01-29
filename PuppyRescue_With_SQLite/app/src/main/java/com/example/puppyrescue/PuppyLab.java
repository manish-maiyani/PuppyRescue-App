package com.example.puppyrescue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.puppyrescue.database.DbCursorWrapper;
import com.example.puppyrescue.database.DbHelper;
import com.example.puppyrescue.database.DbSchema.PuppyTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PuppyLab
{
    private List<Puppy> mPuppies;
    private static PuppyLab sPuppyLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PuppyLab get(Context context)
    {
        if (sPuppyLab == null)
        {
            sPuppyLab = new PuppyLab(context);
        }
        return sPuppyLab;
    }

    public PuppyLab(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext).getWritableDatabase();

        Puppy [] puppy = new Puppy[1];

        puppy[0] = new Puppy();
        puppy[0].setDogName("Molly");
        puppy[0].setDogBreed("Labrador");
        puppy[0].setDogAge(6);
        puppy[0].setDogGender("Female");
        puppy[0].setLatitude(137);
        puppy[0].setLongitude(40);

        ContentValues values = getContentValues(puppy[0]);
        mDatabase.insert(PuppyTable.NAME, null, values);


    }

    public void addNewPuppy(Puppy puppy)
    {
        ContentValues values = getContentValues(puppy);

        mDatabase.insert(PuppyTable.NAME, null, values);
                Puppy newpuppy = puppy;
                mPuppies.add(newpuppy);
    }

    public void deletePuppy(UUID id)
    {
        mDatabase.delete(PuppyTable.NAME,  PuppyTable.Cols.UUID+ "='" + id + "'", null);
    }

    public List<Puppy> getPuppies()
    {
        List<Puppy> puppies = new ArrayList<>();

        DbCursorWrapper cursor = queryPuppies(null, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                puppies.add(cursor.getPuppy());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return puppies;
    }

    public Puppy getPuppy(UUID id)
    {
        DbCursorWrapper cursor = queryPuppies(PuppyTable.Cols.UUID + " = ?",new String[] { id.toString() });

        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPuppy();
        }
        finally
        {
            cursor.close();
        }
    }

    public void updatePuppy(Puppy puppy)
    {
        String uuidString = puppy.getId().toString();
        ContentValues values = getContentValues(puppy);

        mDatabase.update(PuppyTable.NAME, values,PuppyTable.Cols.UUID + " = ?",new String[] { uuidString });
    }

    private DbCursorWrapper queryPuppies(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                PuppyTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new DbCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Puppy puppy)
    {
        ContentValues values = new ContentValues();
        values.put(PuppyTable.Cols.UUID, puppy.getId().toString());
        values.put(PuppyTable.Cols.NAME, puppy.getDogName());
        values.put(PuppyTable.Cols.BREED, puppy.getDogBreed());
        values.put(PuppyTable.Cols.GENDER, puppy.getDogGender());
        values.put(PuppyTable.Cols.AGE, puppy.getDogAge());
        values.put(PuppyTable.Cols.LAT, puppy.getLatitude());
        values.put(PuppyTable.Cols.LON, puppy.getLongitude());

        return values;
    }

}
