package edu.ics.uci.minebike.minecraft.item;

import com.teammetallurgy.aquaculture.items.AquacultureItems;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fishpond {
    public List<List<Pair<Integer, ItemStack>>> fishes= new ArrayList<List<Pair<Integer,ItemStack>>>();
    ItemStack shrooma = AquacultureItems.fish.getItemStackFish("Red Shrooma");
    ItemStack goldfish = AquacultureItems.fish.getItemStackFish("Goldfish");
    ItemStack whale = AquacultureItems.fish.getItemStackFish("Whale");

    static ItemStack bluegill = AquacultureItems.fish.getItemStackFish("Bluegill");
    static ItemStack perch = AquacultureItems.fish.getItemStackFish("Perch");
    static ItemStack gar = AquacultureItems.fish.getItemStackFish("Gar");
    static ItemStack bass = AquacultureItems.fish.getItemStackFish("Bass");
    static ItemStack muskellunge = AquacultureItems.fish.getItemStackFish("Muskellunge");
    static ItemStack trout = AquacultureItems.fish.getItemStackFish("Brown Trout");
    static ItemStack catfish = AquacultureItems.fish.getItemStackFish("Catfish");
    static ItemStack carp = AquacultureItems.fish.getItemStackFish("Carp");
    static List<Pair<Integer,ItemStack>> easy = new ArrayList<Pair<Integer,ItemStack>>(){{
        add(new Pair<Integer,ItemStack>(0, bluegill));
        add(new Pair<Integer,ItemStack>(0, perch));
        add(new Pair<Integer,ItemStack>(0, gar));
        add(new Pair<Integer,ItemStack>(1, bass));
        add(new Pair<Integer,ItemStack>(1, muskellunge));
        add(new Pair<Integer,ItemStack>(1, trout));
        add(new Pair<Integer,ItemStack>(2, catfish));
        add(new Pair<Integer,ItemStack>(2, carp));
    }};
    static ItemStack Blowfish = AquacultureItems.fish.getItemStackFish("Blowfish");
    static ItemStack Grouper = AquacultureItems.fish.getItemStackFish("Red Grouper");
    static ItemStack Salmon = AquacultureItems.fish.getItemStackFish("Salmon");
    static ItemStack Tuna = AquacultureItems.fish.getItemStackFish("Tuna");
    static ItemStack Halibut = AquacultureItems.fish.getItemStackFish("Halibut");
    static ItemStack Swordfish = AquacultureItems.fish.getItemStackFish("Swordfish");
    static ItemStack Shark = AquacultureItems.fish.getItemStackFish("Shark");
    static ItemStack Whale = AquacultureItems.fish.getItemStackFish("Whale");
    static List<Pair<Integer,ItemStack>> hard = new ArrayList<Pair<Integer,ItemStack>>(){{
        add(new Pair<Integer,ItemStack>(3, Blowfish));
        add(new Pair<Integer,ItemStack>(4, Grouper));
        add(new Pair<Integer,ItemStack>(4, Salmon));
        add(new Pair<Integer,ItemStack>(4, Tuna));
        add(new Pair<Integer,ItemStack>(5, Halibut));
        add(new Pair<Integer,ItemStack>(5, Swordfish));
        add(new Pair<Integer,ItemStack>(5, Shark));
        add(new Pair<Integer,ItemStack>(6, Whale));

    }};
    static ItemStack Frog = AquacultureItems.fish.getItemStackFish("Frog");
    static ItemStack Turtle = AquacultureItems.fish.getItemStackFish("Turtle");
    static ItemStack Eel = AquacultureItems.fish.getItemStackFish("Electric Eel");
    static ItemStack Tambaqui = AquacultureItems.fish.getItemStackFish("Tambaqui");
    static ItemStack Boulti = AquacultureItems.fish.getItemStackFish("Boulti");
    static ItemStack Capitaine = AquacultureItems.fish.getItemStackFish("Capitaine");
    static ItemStack Jellyfish = AquacultureItems.fish.getItemStackFish("Jellyfish");
    static ItemStack Squid = AquacultureItems.fish.getItemStackFish("Squid");
    static List<Pair<Integer,ItemStack>> medium = new ArrayList<Pair<Integer,ItemStack>>(){{
        add(new Pair<Integer,ItemStack>(1, Frog));
        add(new Pair<Integer,ItemStack>(2, Turtle));
        add(new Pair<Integer,ItemStack>(2, Eel));
        add(new Pair<Integer,ItemStack>(3, Tambaqui));
        add(new Pair<Integer,ItemStack>(3, Boulti));
        add(new Pair<Integer,ItemStack>(3, Capitaine));
        add(new Pair<Integer,ItemStack>(4, Jellyfish));
        add(new Pair<Integer,ItemStack>(4, Squid));

    }};
    public Fishpond(){

    }
    public static List<Pair<Integer,ItemStack>> easypond(){
        return easy;
    }
    public static List<Pair<Integer,ItemStack>> mediumpond(){
        return medium;
    }
    public static List<Pair<Integer,ItemStack>> hardpond(){
        return hard;
    }
}
