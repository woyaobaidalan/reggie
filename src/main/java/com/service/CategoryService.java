package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.R;
import com.domain.Category;

public interface CategoryService extends IService<Category> {

    void remove(Long id);

}
