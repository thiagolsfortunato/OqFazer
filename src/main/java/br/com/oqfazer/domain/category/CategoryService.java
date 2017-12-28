package br.com.oqfazer.domain.category;

import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public void setRepository(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * O metodo e reponsavel por salvar e editar category
     *
     * @param category
     * @return
     */
    public Category save(final Category category) {
        return repository.save(category);
    }

    /**
     * O metodo e reponsavel por editar category
     *
     * @param category
     * @return
     */
    public Category edit(final Category category) {
        return repository.save(category);
    }

    /**
     * O metodo e reponsavel por deletar category
     *
     * @param category
     */
    public void delete(final Category category) {
        repository.delete(category);
    }

    /**
     * O metodo e reponsavel por buscar todas categories
     *
     * @return List<Category>
     */
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    /**
     * O metodo e reponsavel por buscar por nome a category
     *
     * @param name
     * @return
     */
    public Category findByName(final String name) {
        return repository.findByName(name);
    }

    /**
     * O metodo e reponsavel por buscar por id category
     *
     * @param id
     * @return
     */
    public Category findById(final Long id) {
        return repository.findById(id);
    }

}
