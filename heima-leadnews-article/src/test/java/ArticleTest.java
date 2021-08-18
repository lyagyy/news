import com.heima.ArticleApplication;
import com.heima.common.article.constans.ArticleConstans;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.service.AppArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试文章列表相关接口
 */
@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleTest {

    @Autowired
    private AppArticleService appArticleService;

    /**
     * 测试load
     */
    @Test
    public void testLoad() {

        ResponseResult data = appArticleService.load(ArticleConstans.LOADTYPE_LOAD_MORE,null);
        System.out.println(data.getData());
    }

}