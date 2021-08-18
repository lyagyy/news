package com.heima.controller.v1;

import com.heima.article.apis.ArticleHomeControllerApi;
import com.heima.common.article.constans.ArticleConstans;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.service.AppArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article")
public class AppArticleController implements ArticleHomeControllerApi{

    @Autowired
    private AppArticleService appArticleService;

    /**
     * 加載首頁文章
     * @param dto 封装参数对象
     * @return 文章列表数据
     */
    @Override
    @GetMapping("/load")
    public ResponseResult load(ArticleHomeDto dto) {
        return appArticleService.load(ArticleConstans.LOADTYPE_LOAD_MORE,dto);
    }

    /**
     * 加载更多
     * @param dto 封装参数对象
     * @return 文章列表数据
     */
    @Override
    @GetMapping("/loadmore")
    public ResponseResult loadMore(ArticleHomeDto dto) {
        return appArticleService.load(ArticleConstans.LOADTYPE_LOAD_MORE,dto);
    }

    /**
     * 加载最新的数据
     * @param dto 封装参数对象
     * @return 文章列表
     */
    @Override
    @GetMapping("/loadnew")
    public ResponseResult loadNew(ArticleHomeDto dto) {
        return appArticleService.load(ArticleConstans.LOADTYPE_LOAD_NEW,dto);
    }
}
