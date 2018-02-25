package ru.job4j.store.model;

import java.util.Hashtable;
import java.util.Map;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.02.2018
 */
public class Location {
    private static final Map<String, Country> CITIES = new Hashtable<>();

    private final String city;

    private final Country country;

    public Location(String city) {
        Country country = Location.CITIES.get(city);
        if (country == null) {
            country = Country.COUNTRY_404;
        }
        this.city = city;
        this.country = country;
    }
    public Location(String city, Country country) {
        this.city = city;
        this.country = country;
        Location.CITIES.put(city, country);
    }

    static class Country {

        public static final Country COUNTRY_404 = new Country("not found");

        private static final Map<String, Country> COUNTRIES = new Hashtable<>();

        private Country(String name) {
            this.name = name;
            Country.COUNTRIES.put(name, this);
        }

        private final String name;

        public static Country valueOf(String name) {
            Country country = Country.COUNTRIES.get(name);
            if (country == null) {
                country = new Country(name);
            }
            return country;
        }

        /**
         * @return country name;
         */
        public String name() {
            return this.name;
        }
    }

}
