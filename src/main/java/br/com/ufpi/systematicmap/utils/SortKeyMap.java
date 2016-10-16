///**
// * 
// */
//package br.com.ufpi.systematicmap.utils;
//
//import java.util.*;
//
//
///**
// * @author Gleison Andrade
// *
// */
//public class SortKeyMap extends HashMap<Integer, Double>{
//	
//    public Set<java.util.Map.Entry<Integer, Double>> entrySet() {
//        List<Entry<Integer,Double>> entries = new ArrayList<Entry<Integer,Double>>(super.entrySet());
//        Collections.sort(entries, new Comparator<Entry<Integer,Double>>(){
//
//            public int compare(Map.Entry<Integer,Double> o1, Map.Entry<Integer,Double> o2) {
//                return o1.getValue().compareTo(o2.getValue());
//            }});
//        return new LinkedHashSet<Entry<Integer,Double>>(entries);
//    }
//}
