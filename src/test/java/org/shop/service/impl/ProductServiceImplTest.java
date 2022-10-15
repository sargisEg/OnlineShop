package org.shop.service.impl;

import org.assertj.core.api.Assertions;
import org.shop.entity.Product;
import org.shop.entity.ProductCategory;
import org.shop.repository.ProductRepository;
import org.shop.service.core.category.ProductCategoryService;
import org.shop.service.core.product.CreateProductParams;
import org.shop.service.core.product.ProductService;
import org.shop.service.core.product.UpdateProductParams;
import org.shop.service.impl.exceptions.ProductCategoryNotFoundException;
import org.shop.service.impl.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private ProductService testSubject;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryService productCategoryService;

    @BeforeEach
    public void init() {
        testSubject = new ProductServiceImpl(productRepository, productCategoryService);
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
    public void testFindByCategoryIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByCategoryId(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDeleteWhenProductIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.delete(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateWhenCategoryNotFound() {
        when(productCategoryService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.create(new CreateProductParams(
                    "name",
                    10L,
                    1L,
                    5L
            ));
        }).isExactlyInstanceOf(ProductCategoryNotFoundException.class);

        verify(productCategoryService).findById(1L);

        verifyNoMoreInteractions(productRepository, productCategoryService);
    }

    @Test
    public void testCreate() {
        when(productCategoryService.findById(1L))
                .thenReturn(Optional.of(new ProductCategory()));

        when(productRepository.save(new Product(
                "name",
                10L,
                new ProductCategory(),
                5L
        ))).thenReturn(new Product());

        Assertions.assertThat(testSubject.create(new CreateProductParams(
                "name",
                10L,
                1L,
                5L
        ))).isEqualTo(new Product());

        verify(productCategoryService).findById(1L);
        verify(productRepository).save(new Product(
                "name",
                10L,
                new ProductCategory(),
                5L
        ));

        verifyNoMoreInteractions(productRepository, productCategoryService);
    }

    @Test
    public void testUpdateWhenProductNotFound() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.update(new UpdateProductParams(
                    1L,
                    "name",
                    10L,
                    1L,
                    5L
            ));
        }).isExactlyInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(1L);

        verifyNoMoreInteractions(productRepository, productCategoryService);
    }

    @Test
    public void testUpdateWhenCategoryNotFound() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(new Product()));
        when(productCategoryService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.update(new UpdateProductParams(
                    1L,
                    "name",
                    10L,
                    1L,
                    5L
            ));
        }).isExactlyInstanceOf(ProductCategoryNotFoundException.class);

        verify(productRepository).findById(1L);
        verify(productCategoryService).findById(1L);

        verifyNoMoreInteractions(productRepository, productCategoryService);
    }

    @Test
    public void testUpdate() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(new Product()));
        when(productCategoryService.findById(1L))
                .thenReturn(Optional.of(new ProductCategory()));

        Assertions.assertThat(testSubject.update(new UpdateProductParams(
                1L,
                "name",
                10L,
                1L,
                5L
        ))).isEqualTo(new Product(
                "name",
                10L,
                new ProductCategory(),
                5L
        ));

        verify(productRepository).findById(1L);
        verify(productCategoryService).findById(1L);
        verify(productRepository).save(new Product(
                "name",
                10L,
                new ProductCategory(),
                5L
        ));

        verifyNoMoreInteractions(productRepository, productCategoryService);
    }

    @Test
    public void testFindByCategoryId() {
        when(productRepository.findByCategoryId(1L))
                .thenReturn(List.of(new Product()));

        Assertions.assertThat(testSubject.findByCategoryId(1L))
                .isEqualTo(List.of(new Product()));

        verify(productRepository).findByCategoryId(1L);

        verifyNoMoreInteractions(productRepository, productCategoryService);
    }

    @Test
    public void testFindByName() {
        when(productRepository.findByName("name"))
                .thenReturn(Optional.of(new Product()));

        Assertions.assertThat(testSubject.findByName("name"))
                .isEqualTo(Optional.of(new Product()));

        verify(productRepository).findByName("name");

        verifyNoMoreInteractions(productRepository, productCategoryService);
    }

    @Test
    public void testFindById() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(new Product()));

        Assertions.assertThat(testSubject.findById(1L))
                .isEqualTo(Optional.of(new Product()));

        verify(productRepository).findById(1L);

        verifyNoMoreInteractions(productRepository, productCategoryService);
    }

    @Test
    public void testFindAll() {
        when(productRepository.findAll())
                .thenReturn(List.of(new Product()));

        Assertions.assertThat(testSubject.findAll())
                .isEqualTo(List.of(new Product()));

        verify(productRepository).findAll();

        verifyNoMoreInteractions(productRepository, productCategoryService);
    }
}