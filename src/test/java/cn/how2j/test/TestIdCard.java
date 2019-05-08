package cn.how2j.test;
 
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
 
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
 
import org.junit.Test;
 
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestIdCard {
 
    @Test
    @Comment("���֤У����")
    public void test1() {
        String id15 = "510108871125243";
        p3("15λ���֤����",id15);
        p3("�ж��Ƿ���Ч",IdcardUtil.isValidCard(id15));
        p3("ת��Ϊ18λ���֤����",IdcardUtil.convert15To18(id15));
 
        p3("��ȡ����",IdcardUtil.getBirthByIdCard(id15));
        p3("��ȡ����",IdcardUtil.getAgeByIdCard(id15));
        p3("��ȡ������",IdcardUtil.getYearByIdCard(id15));
        p3("��ȡ������",IdcardUtil.getMonthByIdCard(id15));
        p3("��ȡ������",IdcardUtil.getDayByIdCard(id15));
        p3("��ȡ�Ա�",IdcardUtil.getGenderByIdCard(id15));
        p3("��ȡʡ��",IdcardUtil.getProvinceByIdCard(id15));
         
    }
 
    private String preComment = null;
 
    private void c(String msg) {
        System.out.printf("\t��ע��%s%n", msg);
    }
 
    private void p1(String type1, Object value1, String type2, Object value2) {
        p(type1, value1, type2, value2, "format1");
    }
 
    private void p2(String type1, Object value1, String type2, Object value2) {
        p(type1, value1, type2, value2, "format2");
    }
 
    private void p3(String type1, Object value1) {
        p(type1, value1, "", "", "format3");
    }
 
    private void p(String type1, Object value1, String type2, Object value2, String format) {
        try {
            throw new Exception();
        } catch (Exception e) {
 
            String methodName = getTestMethodName(e.getStackTrace());
            Method m = ReflectUtil.getMethod(this.getClass(), methodName);
            Comment annotation = m.getAnnotation(Comment.class);
            if (null != annotation) {
                String comment = annotation.value();
                if (!comment.equals(preComment)) {
                    System.out.printf("%n%s ���ӣ� %n%n", comment);
                    preComment = comment;
                }
 
            }
        }
        int padLength = 12;
        type1 = StrUtil.padEnd(type1, padLength, Convert.toSBC(" ").charAt(0));
        type2 = StrUtil.padEnd(type2, padLength, Convert.toSBC(" ").charAt(0));
        if ("format1".equals(format)) {
            System.out.printf("\t%s��:\t\"%s\" %n\t��ת��Ϊ----->%n\t%s�� :\t\"%s\" %n%n", type1, value1, type2, value2);
        }
        if ("format2".equals(format)) {
            System.out.printf("\t���� %s:\t\"%s\" %n\t��ȡ %s:\t\"%s\"%n%n", type1, value1, type2, value2);
        }
        if ("format3".equals(format)) {
            System.out.printf("\t%s:\t\"%s\" %n\t%n", type1, value1);
 
        }
    }
 
    private String getTestMethodName(StackTraceElement[] stackTrace) {
        for (StackTraceElement se : stackTrace) {
            String methodName = se.getMethodName();
            if (methodName.startsWith("test"))
                return methodName;
        }
        return null;
    }
 
    @Target({ METHOD, TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface Comment {
        String value();
    }
}