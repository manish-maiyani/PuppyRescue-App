package com.example.puppyrescue.database;

public class DbSchema
{
    public static final class PuppyTable {
        public static final String NAME = "puppies";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String BREED = "breed";
            public static final String GENDER = "gender";
            public static final String AGE = "age";
            public static final String LAT = "lat";
            public static final String LON = "lon";
        }
    }

}
