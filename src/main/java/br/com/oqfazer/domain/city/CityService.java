package br.com.oqfazer.domain.city;

import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityRepository repository;

    public void setRepository(CityRepository repository) {
        this.repository = repository;
    }

    /**
     * O metodo e reponsavel por salvar e editar city
     *
     * @param city
     * @return
     */
    public City save(final City city) throws ExistException {
        City cityEntity = this.findByName(city.getName());
        if (cityEntity != null) throw new ExistException();
        else return repository.save(city);
    }

    /**
     * O metodo e reponsavel por editar city
     *
     * @param city
     * @return
     */
    public City edit(final City city) {
        return repository.save(city);
    }

    /**
     * O metodo e reponsavel por deletar city
     *
     * @param city
     */
    public void delete(final City city) {
        City cityEntity = findById(city.getId());
        repository.delete(cityEntity);
    }

    /**
     * O metodo e reponsavel por buscar todas cities
     *
     * @return List<City>
     */
    public List<City> getAllCities() {
        return repository.findAll();
    }

    /**
     * O metodo e reponsavel por buscar por nome da city
     *
     * @param name
     * @return
     */
    public City findByName(final String name) {
        return repository.findByName(name);
    }

    /**
     * O metodo e reponsavel por buscar por id city
     *
     * @param id
     * @return
     */
    public City findById(final Long id) {
        return repository.findById(id);
    }

}
