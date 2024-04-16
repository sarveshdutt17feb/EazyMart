package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    //create
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto){
        //call service to save obj
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("categoryId") String categoryId){
        CategoryDto updatedDto = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedDto,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId ){
        categoryService.delete(categoryId);
        //api response bna lenge using ApiResponse in format
        ApiResponseMessage response = ApiResponseMessage.builder().success(true).message("Category is deleted successfully !!").status(HttpStatus.OK).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    //get all for pageable response give pageNumber ,pagesize default val
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(@RequestParam(value = "pageNumber",defaultValue = "0",required = false)  int pageNumber,
                                                                @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                                                                @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
                                                                @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

    ){
        PageableResponse<CategoryDto> all = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(all,HttpStatus.OK);
    }
    //get single
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>  getSingle(@PathVariable("categoryId") String categoryId){
        CategoryDto categoryDto = categoryService.get(categoryId);
        return ResponseEntity.ok(categoryDto);
    }
    //search category
}
