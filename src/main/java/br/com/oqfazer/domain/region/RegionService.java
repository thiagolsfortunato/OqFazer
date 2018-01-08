package br.com.oqfazer.domain.region;

import br.com.oqfazer.domain.region.Region;
import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    RegionRepository repository;

    /**
     * O metodo e reponsavel por salvar e editar region
     *
     * @param region
     * @return
     */
    public Region save(final Region region) throws ExistException {
        Region regionEntity = findByName(region.getName());
        if (regionEntity != null) throw new ExistException();
        else return repository.save(region);
    }

    /**
     * O metodo e reponsavel por editar region
     *
     * @param region
     * @return
     */
    public Region edit(final Region region) {
        return repository.save(region);
    }

    /**
     * O metodo e reponsavel por deletar category
     *
     * @param region
     */
    public void delete(final Region region) {
        repository.delete(region);
    }

    /**
     * O metodo e reponsavel por buscar todas cities
     *
     * @return List<region>
     */
    public List<Region> getAllRegions() {
        return repository.findAll();
    }

    /**
     * O metodo e reponsavel por buscar por nome da region
     *
     * @param name
     * @return
     */
    public Region findByName(final String name) {
        return repository.findByName(name);
    }

    /**
     * O metodo e reponsavel por buscar por id region
     *
     * @param id
     * @return
     */
    public Region findById(final Long id) {
        return repository.findById(id);
    }
}
