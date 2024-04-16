package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRespository;
import com.lcwd.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//so that we could inject @Service
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRespository categoryRespository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        Category category=mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRespository.save(category);
        CategoryDto dto = mapper.map(savedCategory, CategoryDto.class);
        return dto;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        //get category of given id
        Category category = categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception"));
        //update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCatogory = categoryRespository.save(category);
        CategoryDto category_Dto = mapper.map(updatedCatogory, CategoryDto.class);
        return category_Dto;
    }

    @Override
    public void delete(String categoryId) {
        //get category of given id
        Category category = categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception"));
        categoryRespository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Category> page = categoryRespository.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception"));
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        return categoryDto;
    }
}
