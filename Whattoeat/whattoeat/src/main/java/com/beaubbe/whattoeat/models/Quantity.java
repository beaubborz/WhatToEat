package com.beaubbe.whattoeat.models;

import android.content.Context;

import com.beaubbe.whattoeat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gab on 06/02/14.
 */
public class Quantity
{
    double amount;
    Unit unit;

    public static class Unit
    {
        Type type;
        Category category;
        String symbol;

        public Unit(Type type, Category category, String symbol)
        {
            this.type=type;
            this.category=category;
            this.symbol=symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }

        public static List<Unit> getAsList(Context c) {
            final Unit ML = new Unit(Type.ml, Category.VOLUME, c.getString(R.string.ml));
            final Unit LITERS = new Unit(Type.liter, Category.VOLUME, c.getString(R.string.liters));
            final Unit CUP = new Unit(Type.cup, Category.VOLUME, c.getString(R.string.cups));
            final Unit TEASPOON = new Unit(Type.teaspoon, Category.VOLUME, c.getString(R.string.teaspoon));
            final Unit TABLESPOON = new Unit(Type.tablespoon, Category.VOLUME, c.getString(R.string.tablespoon));
            final Unit FLOZ = new Unit(Type.floz, Category.VOLUME, c.getString(R.string.floz));
            final Unit GALLON = new Unit(Type.gallon, Category.VOLUME, c.getString(R.string.gallon));
            final Unit GRAM = new Unit(Type.gram, Category.MASS, c.getString(R.string.gram));
            final Unit POUND = new Unit(Type.pound, Category.MASS, c.getString(R.string.pound));
            final Unit KILOGRAM = new Unit(Type.kilogram, Category.MASS, c.getString(R.string.kilogram));
            final Unit OUNCE = new Unit(Type.ounce, Category.MASS, c.getString(R.string.ounce));

            final ArrayList<Unit> list = new ArrayList<Unit>();

            list.add(ML);
            list.add(LITERS);
            list.add(CUP);
            list.add(TEASPOON);
            list.add(TABLESPOON);
            list.add(FLOZ);
            list.add(GALLON);
            list.add(GRAM);
            list.add(POUND);
            list.add(KILOGRAM);
            list.add(OUNCE);

            return list;
        }

        public static enum Category
        {
            VOLUME, MASS
        }

        public static enum Type
        {
            ml, liter, cup, teaspoon, tablespoon, floz, gallon,
            gram, pound, kilogram, ounce
        }
    }
}
