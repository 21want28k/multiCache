import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// SpringJUnit4ClassRunner再Junit环境下提供Spring TestContext Framework的功能。
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration用来加载配置ApplicationContext，其中classes用来加载配置类
@ContextConfiguration(classes = {CacheClusterConfig.class})
public class CacheClusterCoreTest {
    private Logger logger = LoggerFactory.getLogger(CacheClusterCoreTest.class);
}
