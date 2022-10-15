package org.shop.service.impl;

import org.assertj.core.api.Assertions;
import org.shop.entity.ProductCategory;
import org.shop.repository.ProductCategoryRepository;
import org.shop.service.core.category.CreateProductCategoryParams;
import org.shop.service.core.category.ProductCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {

    private ProductCategoryService testSubject;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    public void init() {
        testSubject = new ProductCategoryServiceImpl(productCategoryRepository);
    }

    @Test
    public void testCreateWhenParamsIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.create(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findById(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByNameWhenNameIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByName(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByNameWhenNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByName("");
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreate() {
        final ProductCategory productCategory = new ProductCategory("name");
        productCategory.setId(1L);

        when(productCategoryRepository.save(new ProductCategory("name")))
                .thenReturn(productCategory);

        Assertions.assertThat(testSubject.create(new CreateProductCategoryParams("name")))
                .isEqualTo(productCategory);

        verify(productCategoryRepository).save(new ProductCategory("name"));

        verifyNoMoreInteractions(productCategoryRepository);
    }
    @Test
    public void testFindById() {

        final ProductCategory productCategory = new ProductCategory("name");
        productCategory.setId(1L);

        when(productCategoryRepository.findById(1L))
                .thenReturn(Optional.of(productCategory));

        Assertions.assertThat(testSubject.findById(1L))
                .isEqualTo(Optional.of(productCategory));

        verify(productCategoryRepository).findById(1L);

        verifyNoMoreInteractions(productCategoryRepository);
    }

    @Test
    public void testFindByName() {

        final ProductCategory productCategory = new ProductCategory("name");

        when(productCategoryRepository.findByName("name"))
                .thenReturn(Optional.of(productCategory));

        Assertions.assertThat(testSubject.findByName("name"))
                .isEqualTo(Optional.of(productCategory));

        verify(productCategoryRepository).findByName("name");

        verifyNoMoreInteractions(productCategoryRepository);
    }

    @Test
    public void testFindAll() {
        when(productCategoryRepository.findAll())
                .thenReturn(List.of(new ProductCategory()));

        Assertions.assertThat(testSubject.findAll())
                .isEqualTo(List.of(new ProductCategory()));

        verify(productCategoryRepository).findAll();

        verifyNoMoreInteractions(productCategoryRepository);
    }
}