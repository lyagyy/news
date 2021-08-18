package com.heima.service.impl;

import com.heima.common.article.constans.ArticleConstans;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.mappers.app.ApArticleMapper;
import com.heima.model.mappers.app.ApUserArticleListMapper;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserArticleList;
import com.heima.service.AppArticleService;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class AppArticleServiceImpl implements AppArticleService {

    // 单页最大加载的数字
    private static final short MAX_PAGE_SIZE=50;



    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private ApUserArticleListMapper apUserArticleListMapper;

    /**
     * @param time 时间节点
     * @param type 1加载更多 2加载最新
     * @param dto 封装数据
     * @param size 每次返回数据量
     * @return 数据列表
     */
    @Override
    public ResponseResult load(Short type, ArticleHomeDto dto) {
        //参数校验
        if(dto==null){
            dto= new ArticleHomeDto();
        }

        ApUser user = AppThreadLocalUtils.getUser();
        Integer size = dto.getSize();
        String tag = dto.getTag();
        // 分页参数校验
        if (size == null || size <= 0) {
            size = 20;
        }
        size = Math.min(size,MAX_PAGE_SIZE);
        dto.setSize(size);
        //  类型参数校验
        if (!type.equals(ArticleConstans.LOADTYPE_LOAD_MORE) && !type.equals(ArticleConstans.LOADTYPE_LOAD_NEW)){
            type = ArticleConstans.LOADTYPE_LOAD_MORE;
        }
        // 文章频道参数验证
        if (StringUtils.isEmpty(tag)) {
            dto.setTag(ArticleConstans.DEFAULT_TAG);
        }
        // 最大时间处理
        if(dto.getMaxBehotTime()==null){
            dto.setMaxBehotTime(new Date());
        }
        // 最小时间处理
        if(dto.getMinBehotTime()==null){
            dto.setMinBehotTime(new Date());
        }
        // 数据加载
        if(user!=null){
            //存在 已经登录 加载推荐文章
            return ResponseResult.okResult(getUserArticle(user,dto,type));
        }else{
            //不存在 未登录 加载默认文章
            return ResponseResult.okResult(getDefaultArticle(dto,type));
        }
    }

    /**
     * 先从用户的推荐表中查找文章，如果没有再从大文章列表中获取
     * @param user
     * @param dto
     * @param type
     * @return
     */
    private List<ApArticle> getUserArticle(ApUser user,ArticleHomeDto dto,short type){

        List<ApUserArticleList> list = apUserArticleListMapper.loadArticleIdListByUser(user,dto,type);
        if(!list.isEmpty()){
            List<ApArticle> temp = apArticleMapper.loadArticleListByIdList(list);
            return temp;
        }else {
            return getDefaultArticle(dto,type);
        }

    }

    /**
     * 从默认的大文章列表中获取文章
     * @param dto
     * @param type
     * @return
     */
    private List<ApArticle> getDefaultArticle(ArticleHomeDto dto,short type){

    return apArticleMapper.loadArticleListByLocation(dto,type);
    }
}
