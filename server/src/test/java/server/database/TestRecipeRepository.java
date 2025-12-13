package server.database;

import commons.Recipe;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestRecipeRepository implements RecipeRepository {

    public final List<Recipe> recipes = new ArrayList<>();
    public long nextId = 1;

    @Override
    @NonNull
    public List<Recipe> findAll() {
        return new ArrayList<>(recipes);
    }

    @Override
    @NonNull
    public <S extends Recipe> S save(@NonNull S entity) {
        if (entity.id == 0) {
            entity.id = nextId++;
        }
        recipes.removeIf(r -> r.id == entity.id);
        recipes.add(entity);
        return entity;
    }

    @Override
    public boolean existsById(@NonNull Long id) {
        return recipes.stream().anyMatch(r -> r.id == id);
    }

    @Override
    public void deleteAll() {
        recipes.clear();
    }

    @Override
    public void flush() {}

    @Override
    @NonNull
    public <S extends Recipe> S saveAndFlush(@NonNull S entity) {
        return save(entity);
    }

    @Override
    @NonNull
    public <S extends Recipe> List<S> saveAllAndFlush(@NonNull Iterable<S> entities) {
        return saveAll(entities);
    }

    @Override
    public void deleteAllInBatch(@NonNull Iterable<Recipe> entities) {}

    @Override
    public void deleteAllByIdInBatch(@NonNull Iterable<Long> ids) {}

    @Override
    public void deleteAllInBatch() {}

    @Override
    @NonNull
    public Recipe getOne(@NonNull Long id) {
        return findById(id).orElse(null);
    }

    @Override
    @NonNull
    public Recipe getById(@NonNull Long id) {
        return findById(id).orElse(null);
    }

    @Override
    @NonNull
    public Recipe getReferenceById(@NonNull Long id) {
        return findById(id).orElse(null);
    }

    @Override
    @NonNull
    public <S extends Recipe> List<S> findAll(@NonNull Example<S> example) {
        return new ArrayList<>();
    }

    @Override
    @NonNull
    public <S extends Recipe> List<S> findAll(@NonNull Example<S> example, @NonNull Sort sort) {
        return new ArrayList<>();
    }

    @Override
    @NonNull
    public <S extends Recipe> List<S> saveAll(@NonNull Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    @NonNull
    public Optional<Recipe> findById(@NonNull Long id) {
        return recipes.stream().filter(r -> r.id == id).findFirst();
    }

    @Override
    @NonNull
    public List<Recipe> findAllById(@NonNull Iterable<Long> ids) {
        return new ArrayList<>();
    }

    @Override
    public long count() {
        return recipes.size();
    }

    @Override
    public void deleteById(@NonNull Long id) {
        recipes.removeIf(r -> r.id == id);
    }

    @Override
    public void delete(@NonNull Recipe entity) {
        recipes.removeIf(r -> r.id == entity.id);
    }

    @Override
    public void deleteAllById(@NonNull Iterable<? extends Long> ids) {}

    @Override
    public void deleteAll(@NonNull Iterable<? extends Recipe> entities) {}

    @Override
    @NonNull
    public List<Recipe> findAll(@NonNull Sort sort) {
        return new ArrayList<>();
    }

    @Override
    @NonNull
    public Page<Recipe> findAll(@NonNull Pageable pageable) {
        return Page.empty();
    }

    @Override
    @NonNull
    public <S extends Recipe> Optional<S> findOne(@NonNull Example<S> example) {
        return Optional.empty();
    }

    @Override
    @NonNull
    public <S extends Recipe> Page<S> findAll(@NonNull Example<S> example, @NonNull Pageable pageable) {
        return Page.empty();
    }

    @Override
    public <S extends Recipe> long count(@NonNull Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Recipe> boolean exists(@NonNull Example<S> example) {
        return false;
    }

    @Override
    @NonNull
    public <S extends Recipe, R> R findBy(@NonNull Example<S> example, @NonNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
