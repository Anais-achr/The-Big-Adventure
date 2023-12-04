package fr.umlv.map;

public interface Element {
    public enum Elem {
        ALGAE("ALGAE"), CLOUD("CLOUD"), FLOWER("FLOWER"), FOLIAGE("FOLIAGE"), GRASS("GRASS"), 
        LADDER("LADDER"), LILY("LILY"), PLANK("PLANK"), REED("REED"), ROAD("ROAD"), SPROUT("SPROUT"), 
        TILE("TILE"), TRACK("TRACK"), VINE("VINE"), BED("BED"), BOG("BOG"), BOMB("BOMB"), BRICK("BRICK"), 
        CHAIR("CHAIR"), CLIFF("CLIFF"), DOOR("DOOR"), FENCE("FENCE"), FORT("FORT"), GATE("GATE"), HEDGE("HEDGE"),
        HOUSE("HOUSE"), HUSK("HUSK"), HUSKS("HUSKS"), LOCK("LOCK"), MONITOR("MONITOR"), PIANO("PIANO"), PILLAR("PILLAR"),
        PIPE("PIPE"), ROCK("ROCK"), RUBBLE("RUBBLE"), SHELL("SHELL"), SIGN("SIGN"), SPIKE("SPIKE"), STATUE("STATUE"),
        STUMP("STUMP"), TABLE("TABLE"), TOWER("TOWER"), TREE("TREE"), TREES("TREES"), WALL("WALL"), BUBBLE("BUBBLE"), DUST("DUST"), 
        LAVA("LAVA"), WATER("WATER"), WIND("WIND"), BOLT("BOLT"), GEM("GEM")
        ;
        
        final String regex;

        Elem(String regex) {
            this.regex = regex;
        }
    }
}