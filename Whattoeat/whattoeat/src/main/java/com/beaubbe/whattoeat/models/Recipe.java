package com.beaubbe.whattoeat.models;


import java.util.ArrayList;
import java.util.HashMap;

public class Recipe
{
    int id;
    String name;
    HashMap<Ingredient, Quantity> ingredients;
    ArrayList<Step> steps;
}
