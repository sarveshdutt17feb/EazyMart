package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRespository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRespository categoryRespository;
    @Value("${product.image.path}")
    private String imagePath;
    //other dependency
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Override
    public ProductDto create(ProductDto productDto) {

        Product prodcut = mapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        prodcut.setProductId(productId);
        //added date
        prodcut.setAddedDate(new Date());
        Product savedProduct = productRepository.save(prodcut);
        ProductDto productDto1 = mapper.map(savedProduct, ProductDto.class);
        return productDto1;
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given id !!"));
        product.setTitle(productDto.getTitle());
        product.setLive(productDto.isLive());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());
        //save the entity
        Product updatedProduct = productRepository.save(product);
        ProductDto updatedProductDto = mapper.map(updatedProduct, ProductDto.class);
        return updatedProductDto;
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given id !!"));
        String fullPath = imagePath+product.getProductImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
            logger.info("image deleted successfully {}",product.getProductImageName());
        }catch (NoSuchFileException ex){
            ex.printStackTrace();
            logger.info("no such file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        productRepository.delete(product);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given id !!"));
        ProductDto productDto = mapper.map(product, ProductDto.class);
        return productDto;
    }
    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir){
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }
    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        //fetch the category
        Category category = categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found of given Id !!"));
        Product prodcut = mapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        prodcut.setProductId(productId);
        //added date
        prodcut.setAddedDate(new Date());
        prodcut.setCategory(category);
        Product savedProduct = productRepository.save(prodcut);
        ProductDto productDto1 = mapper.map(savedProduct, ProductDto.class);

        return productDto1;
    }

    //update category of product
    @Override
    public ProductDto updateCategory(String categoryId, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product of given id not found !!"));
        Category category = categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category of given id not found !!"));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        ProductDto dto = mapper.map(savedProduct, ProductDto.class);
        return dto;
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category of given id  not found !!"));
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());

        Pageable pageable =PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> AllOfCategory = productRepository.findByCategory(category, pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(AllOfCategory, ProductDto.class);
        return response;
    }


}
