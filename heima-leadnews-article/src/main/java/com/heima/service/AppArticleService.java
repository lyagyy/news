package com.heima.service;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;

public interface AppArticleService {

    /**
     *
     * @param type 1加载更多 2加载最新
     * @param dto 封装数据
     * @return
     */
    ResponseResult load(Short type, ArticleHomeDto dto);
}
