package com.rupp.spring.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.rupp.spring.domain.CountriesList;

@Service("countrySevice")
public class CountryServiceImpl implements CountryService {

    @Override
    public Collection<String> getAll() {
        return CountriesList.getAllCountries();
    }

}
