package com.countries.repositories;

import org.springframework.data.repository.CrudRepository;
import com.countries.models.Countries;

public interface CountryRepository extends CrudRepository<Countries, Long> {
}
