package com.countries.controllers;

import com.countries.models.Countries;

import com.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    @Autowired
    private CountryRepository countryrepos;

    private List<Countries> findCountry(List<Countries> myList, CheckCountry tester)
    {
        List<Countries> tempList = new ArrayList<>();
        for (Countries c : myList)
        {
            if (tester.test(c))
            {
                tempList.add(c);
            }
        }
        return tempList;
    }

    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries()
    {
        List<Countries> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/U
    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listCountriesStartingWithU(@PathVariable char letter)
    {
        List<Countries> myList =  new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Countries> rtnList = findCountry(myList, c -> c.getName().charAt(0) == letter);
        rtnList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> listPopulationTotal()
    {
        List<Countries> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        long totalPopulation = 0;
        for (Countries c : myList)
        {
            totalPopulation += c.getPopulation();
        }
        System.out.println("The Total Population is " + totalPopulation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> listMinPopulation()
    {
        List<Countries> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getPopulation() > c2.getPopulation() ? 1 : -1);
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }

    // http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> listMaxPopulation()
    {
        List<Countries> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getPopulation() < c2.getPopulation() ? 1 : -1);
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }

    // http://localhost:2019/population/median
    @GetMapping(value = "/population/median", produces = {"application/json"})
    public ResponseEntity<?> listMedianPopulation()
    {
        List<Countries> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getPopulation() > c2.getPopulation() ? 1 : -1);
        return new ResponseEntity<>(myList.get(myList.size() / 2 + 1), HttpStatus.OK);
    }
}