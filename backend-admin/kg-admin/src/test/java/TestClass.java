import cn.hutool.system.UserInfo;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.system.business.kg.base.record.domain.po.Student;
import org.junit.Test;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/4/27 17:50
 * @desc
 */
public class TestClass {
    @Test
    public void test1(){
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(1L);
    }
}
