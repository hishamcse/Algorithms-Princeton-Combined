package com.company;

import java.util.HashSet;
import java.util.Set;

public class WrongHashingUsingCode {
    public static class OlympicAthlete1 {
        private final String name;

        public OlympicAthlete1(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    public static class OlympicAthlete2 {
        private final String name;

        public OlympicAthlete2(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object that) {
            if (that == null) return false;
            if (!(that instanceof OlympicAthlete2)) return false;
            return name.equals(((OlympicAthlete2) that).name);
        }
    }

    public static class OlympicAthlete3 {
        private final String name;

        public OlympicAthlete3(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        public boolean equals(OlympicAthlete3 that) {
            if (that == null) return false;
            return name.equals(that.name);
        }
    }

    public static void main(String[] args) {
        //test 1
//        Set<OlympicAthlete1> set = new HashSet<>();
//        OlympicAthlete1 athlete1 = new OlympicAthlete1("peter");
//        set.add(athlete1);
//        set.add(athlete1);
//        System.out.println(set.size());
//
//        OlympicAthlete1 athlete2 = new OlympicAthlete1("peter");
//        System.out.println(athlete2.hashCode()==athlete1.hashCode());
//        set.add(athlete2);
//        System.out.println(set.size());
//
//        OlympicAthlete1 athlete3 = new OlympicAthlete1("peter3");
//        System.out.println(athlete2.hashCode()==athlete3.hashCode());
//        set.add(athlete3);
//        System.out.println(set.size());

        //test 2
//        Set<OlympicAthlete2> set = new HashSet<>();
//        OlympicAthlete2 athlete1 = new OlympicAthlete2("peter");
//        set.add(athlete1);
//        set.add(athlete1);
//        System.out.println(set.size());
//
//        OlympicAthlete2 athlete2 = new OlympicAthlete2("peter");
//        set.add(athlete2);
//        System.out.println(athlete1.equals(athlete2));
//
//        System.out.println(set.size());
//
//        OlympicAthlete2 athlete3 = new OlympicAthlete2("peter3");
//        set.add(athlete3);
//        System.out.println(set.size());

        //test 3
        Set<OlympicAthlete3> set = new HashSet<>();
        OlympicAthlete3 athlete1 = new OlympicAthlete3("peter");
        set.add(athlete1);
        set.add(athlete1);
        System.out.println(set.size());

        OlympicAthlete3 athlete2 = new OlympicAthlete3("peter");
        set.add(athlete2);
        System.out.println(athlete1.equals(athlete2));
        System.out.println(set.size());

        OlympicAthlete3 athlete3 = new OlympicAthlete3("peter3");
        set.add(athlete3);
        System.out.println(set.size());

    }
}
