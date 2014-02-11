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
    private double amount;
    private Unit unit;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public static class Unit
    {
        private Type type;
        private Category category;
        private String symbol;

        public Unit(Context c, int type) {
            List<Unit> uts = this.getAsList(c);

            for(Unit u:uts)
            {
                if (u.type.ordinal() == type)
                {
                    this.type = u.type;
                    this.category = u.category;
                    this.symbol = u.symbol;
                    break;
                }
            }
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

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
            final Unit UNIT = new Unit(Type.unit, Category.NUMBER, c.getString(R.string.unit));
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

            list.add(UNIT);
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
            VOLUME, MASS, NUMBER
        }

        public static enum Type
        {
            unit, ml, liter, cup, teaspoon, tablespoon, floz, gallon,
            gram, pound, kilogram, ounce
        }
    }
}
