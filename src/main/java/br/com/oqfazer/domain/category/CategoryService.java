package br.com.oqfazer.domain.category;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    private List<Category> categoryList = new LinkedList<>();

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
    public List<Category> findByName(final String name) {
        List<Category> categoryDb = repository.findByName(name);
        if (categoryDb.size() > 0) categoryDb.forEach(this::getSonsCategories);
        return categoryList;
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

    /**
     * Metodo recursivo para buscar todas categorias filhas
     * @param category
     */
    private void getSonsCategories(Category category) {
        categoryList.add(category);
        if (category.getSons().size() > 0) category.getSons().forEach(this::getSonsCategories);
    }
}
