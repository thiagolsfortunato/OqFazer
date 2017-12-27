package br.com.oqfazer.domain.category;

import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    public Category save(final Category category) throws ExistException {
        Category categoryEntity = findByName(category.getName());
        if (categoryEntity != null) throw new ExistException();
        else {
            categoryEntity = new Category(category.getName(), category.getParent());
            repository.save(categoryEntity);
        }
        return categoryEntity;
    }

    /**
     * O metodo e reponsavel por editar category
     *
     * @param category
     * @return
     */
    public Category edit(Category category) {
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
    public Category findByName(String name) {
        return repository.findByName(name);
    }

    /**
     * O metodo e reponsavel por buscar por id category
     *
     * @param id
     * @return
     */
    public Category findById(Long id) {
        return repository.findById(id);
    }


}
