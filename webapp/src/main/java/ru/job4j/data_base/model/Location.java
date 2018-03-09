package ru.job4j.data_base.model;

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
    /**
     * cache cities.
     */
    private static final Map<String, Country> CITIES = new Hashtable<>();

    /**
     * City.
     */
    private final String city;

    /**
     * Country.
     */
    private final Country country;

    /**
     * @param city city name
     */
    private Location(String city) {
        this(city, null);
    }

    /**
     * @param city city name
     * @param country country
     */
    private Location(String city, Country country) {
        this.city = city;
        this.country = country != null ? country : Country.COUNTRY_404;
        Location.CITIES.put(city, country);
    }

    /**
     * Countries.
     */
    static class Country {
        /**
         * Unknown country.
         */
        public static final Country COUNTRY_404 = new Country("not found");

        /**
         * country cache.
         */
        private static final Map<String, Country> COUNTRIES = new Hashtable<>();

        /**
         * @param name name country.
         */
        private Country(String name) {
            this.name = name;
            Country.COUNTRIES.put(name, this);
        }

        /**
         * country name.
         */
        private final String name;

        /**
         * @param name country name
         * @return Country object.
         */
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
