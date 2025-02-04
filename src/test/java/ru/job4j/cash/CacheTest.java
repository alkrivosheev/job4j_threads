package ru.job4j.cash;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {
    @Test
    public void whenAddFind() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base");
    }

    @Test
    public void whenAddUpdateFind() throws OptimisticException {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base updated", 1));
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base updated");
    }

    @Test
    public void whenAddDeleteFind() throws OptimisticException {
        var base = new Base(1,   "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.delete(1);
        var find = cache.findById(base.id());
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    public void whenMultiUpdateThrowException() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class);
    }

    @Test
    public void whenAddNewModelThenSuccess() throws OptimisticException {
        Cache cache = new Cache();
        Base model = new Base(1, "Model 1", 1);
        assertTrue(cache.add(model));
        assertEquals(Optional.of(model), cache.findById(1));
    }

    @Test
    public void whenAddExistingModelThenFail() throws OptimisticException {
        Cache cache = new Cache();
        Base model1 = new Base(1, "Model 1", 1);
        Base model2 = new Base(1, "Model 2", 1);
        cache.add(model1);
        assertFalse(cache.add(model2));
        assertEquals(Optional.of(model1), cache.findById(1));
    }

    @Test
    public void whenUpdateModelThenSuccess() throws OptimisticException {
        Cache cache = new Cache();
        Base model = new Base(1, "Model 1", 1);
        cache.add(model);
        Base updatedModel = new Base(1, "Updated Model", 1);
        assertTrue(cache.update(updatedModel));
        assertEquals(Optional.of(new Base(1, "Updated Model", 2)), cache.findById(1));
    }

    @Test
    public void whenUpdateModelWithWrongVersionThenThrowException() throws OptimisticException {
        Cache cache = new Cache();
        Base model = new Base(1, "Model 1", 1);
        cache.add(model);
        Base updatedModel = new Base(1, "Updated Model", 2);
        assertThrows(OptimisticException.class, () -> cache.update(updatedModel));
    }

    @Test
    public void whenDeleteModelThenSuccess() throws OptimisticException {
        Cache cache = new Cache();
        Base model = new Base(1, "Model 1", 1);
        cache.add(model);
        cache.delete(1);
        assertEquals(Optional.empty(), cache.findById(1));
    }
}