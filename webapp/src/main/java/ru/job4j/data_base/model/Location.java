package ru.job4j.data_base.model;

import ru.job4j.data_base.JSONConvert;
import ru.job4j.data_base.store.LocationStore;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.02.2018
 */
public class Location implements JSONConvert {
    /**
     * Patterns for validate.
     */
    private static final Pattern PATTERN = Pattern.compile("[A-zА-я]{2,30}");

    /** validate params.
     * @param country login
     * @param city name
     * @return true if params correct
     */
    public static boolean validate(String country, String city) {
        boolean flag = PATTERN.matcher(country).find();
        flag &= PATTERN.matcher(city).find();
        return flag;
    }

    /**
     * cache cities.
     */
    private static final Map<Country, Map<String, Location>> CITIES = new TreeMap<>();

    /**
     * country cache.
     */
    private static final Map<String, Country> COUNTRIES = new TreeMap<>();

    static {
        LocationStore store = new LocationStore();
        for (String country : store.getCountries()) {
            Country countryObj = new Location.Country(country);
            for (String city : store.getCity(country)) {
                new Location(city, countryObj);
            }
        }
    }

    /**
     * City.
     */
    private final String city;

    /**
     * @return city Name
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Country.
     */
    private final Country country;


    /**
     * Unknown country.
     */
    public static final Country COUNTRY_404 = Country.valueOf("-------");


    /**
     * Unknown city.
     */
    public static final Location UNKNOWN = Location.valueOf("-------");

    /**
     * @param city city name
     * @param country country
     */
    private Location(String city, Country country) {
        this.city = city;
        this.country = country != null ? country : Location.COUNTRY_404;
        Location.CITIES.get(this.country).put(city, this);
    }

    /**
     * @return country iterator.
     */
    public static Iterator<String> getCountries() {
        return Location.COUNTRIES.keySet().iterator();
    }

    /**
     * @param country region
     * @return cities of region.
     */
    public static Iterator<String> getCities(String country) {
        return Location.CITIES.get(Country.valueOf(country)).keySet().iterator();
    }

    /**
     * @param location string location city/country
     * @return Location object.
     */
    public static Location valueOf(String location) {
        Location loc = Location.UNKNOWN;
        String[] split = location.split("/");
        if (split.length == 1) {
            LinkedList<Location> locations = new LinkedList<>();
            for (Entry<Country, Map<String, Location>> entry : Location.CITIES.entrySet()) {
                for (String city : entry.getValue().keySet()) {
                    if (city.equals(split[0])) {
                        locations.add(entry.getValue().get(city));
                    }
                }
            }
            if (locations.size() != 0) {
                loc = locations.getFirst();
            }
        }
        if (split.length == 2) {
            Country country = Country.valueOf(split[1]);
            loc = Location.CITIES.get(country).get(split[0]);
        }
        if (loc == null) {
            loc = Location.UNKNOWN;
        }
        return loc;
    }

    /** getter.
     * @return country name location
     */
    public String geCountry() {
        return this.country.name;
    }

    @Override
    public String toJSON() {
        return String.format("{\"city\":\"%s\",\"country\":\"%s\"}", this.city, this.country.name);
    }

    /**
     * Countries.
     */
    static class Country implements Comparable<Country> {

        /**
         * @param name name country.
         */
        private Country(String name) {
            this.name = name;
            Location.COUNTRIES.put(name, this);
            Location.CITIES.put(this, new TreeMap<>());
        }

        /**
         * country name.
         */
        private final String name;

        /**
         * @param name country name
         * @return Country object.
         */
        static Country valueOf(String name) {
            Country country = Location.COUNTRIES.get(name);
            if (country == null) {
                country = Location.COUNTRY_404;
            }
            return country;
        }

        /**
         * @return country name;
         */
        public String name() {
            return this.name;
        }

        @Override
        public int compareTo(Country o) {
            return this.name.compareTo(o.name);
        }
    }

}
