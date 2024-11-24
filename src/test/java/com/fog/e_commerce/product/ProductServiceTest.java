package com.fog.e_commerce.product;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fog.e_commerce.exception.InvalidInputException;
import com.fog.e_commerce.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a sample product for testing
        product = new Product("Test Product", "Description", "M", "image.jpg", "M", "Electronics", 100.0, 10, 5, new Date());
        productDto = new ProductDto("Description", "M", "image.jpg", "M", "Electronics", 100.0, 10, 5, new Date());
    }

    @Test
    void testGetProduct_ValidId() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(product));

        // When
        Product result = productService.getProduct(id);

        // Then
        assertEquals(product, result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testGetProduct_ProductNotFound() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(id));
    }

    @Test
    void testAddProduct() {
        // Given
        when(repository.save(any(Product.class))).thenReturn(product);

        // When
        ResponseEntity response = productService.addProduct(productDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(product, response.getBody());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_ValidId() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);

        // When
        ResponseEntity response = productService.updateProduct(id, productDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_SellingCountExceedsQuantity() {
        // Given
        Long id = 1L;
        productDto.setSellingCount(20);  // Exceeds available quantity (10)
        when(repository.findById(id)).thenReturn(Optional.of(product));

        // When & Then
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> productService.updateProduct(id, productDto));
        assertEquals("Selling count cannot exceed available quantity", exception.getMessage());
    }

    @Test
    void testDeleteProduct_ValidId() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(product));
        doNothing().when(repository).delete(any(Product.class));

        // When
        ResponseEntity response = productService.deleteProduct(id);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product with ID 1 deleted.", response.getBody());
        verify(repository, times(1)).delete(any(Product.class));
    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(id));
    }

    @Test
    void testGetProducts_WithFilters() {
        // Given
        Pageable pageable = mock(Pageable.class);
        Page<Product> page = mock(Page.class);
        when(repository.findAllByFilters("M", "Electronics", pageable)).thenReturn(page);

        // When
        Page<Product> result = productService.getProducts("M", "Electronics", pageable);

        // Then
        assertEquals(page, result);
        verify(repository, times(1)).findAllByFilters("M", "Electronics", pageable);
    }
}
