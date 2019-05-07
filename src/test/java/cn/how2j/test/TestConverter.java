package cn.how2j.test;
 
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
 
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;
 
import org.junit.Test;
 

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestConverter {
 
    @Test
    @Comment("ת��Ϊ�ַ���")
    public void test1() {
        int a = 1;
        String aStr = Convert.toStr(a);
 
        int[] b = {1,2,3,4,5};
        String bStr = Convert.toStr(b);
         
        Object c = null;
        String cStr = Convert.toStr(c,"���ַ���(Ĭ��ֵ)");
 
        p("����", a, "�ַ���" , aStr);
        p("long����", b, "�ַ���" , bStr);  
        p("�ն���", c, "�ַ���" , cStr); 
         
    }
     
    @Test
    @Comment("�������ͻ���ת��")
    public void test2() {
        String[] a = { "1", "2", "3", "4" };
        Integer[] b = Convert.toIntArray(a);
        p("�ַ�������", Convert.toStr(a), "Integer����" , Convert.toStr(b));  
    }
     
    @Test
    @Comment("����ͼ��ϻ���")
 
    public void test3() {
         
        String[] a = { "1", "2", "3", "4" };
         
        List<?> l = Convert.toList(a);
         
        String[] b= Convert.toStrArray(l);
        p("�ַ�������", a, "����" , l);   
        p("����", l, "�ַ�������" , b);   
    }
 
    @Test
    @Comment("���ȫ�ǻ���ת��")
    public void test4() {
        String a = "123456789";
        String b = Convert.toSBC(a);
        String c = Convert.toDBC(b);
        p("���", a, "ȫ��" , b);  
        p("ȫ��", b, "���" , c);  
    }
         
    @Test
    @Comment("Unicode���ַ���ת��")
    public void test6() {
        String a = "how2j��Hutool�̳�";
        String unicode = Convert.strToUnicode(a);
        String b = Convert.unicodeToStr(unicode);  
         
        p("�ַ���", a, "unicode" , unicode);  
        p("unicode", unicode, "�ַ���" , b);  
         
    }
    @Test
    @Comment("��ͬ����֮���ת��")
    public void test7() {
        String a = "how2j��Hutool�̳�";
        //ת����resultΪ����
        String b = Convert.convertCharset(a, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1);
        String c = Convert.convertCharset(b, CharsetUtil.ISO_8859_1, "UTF-8");
         
        p("UTF-8", a, "IOS-8859-1" , b);   
        p("IOS-8859-1", b, "UTF-8" , c);   
    }
     
    @Test
    @Comment("����ת��Ϊ���")
    public void test8() {
        double  a= 1234567123456.12;
        String b = Convert.digitToChinese(a);
        p("����", a, "��Ʊ���" , b);
    }
    @Test
    @Comment("ԭʼ��Ͱ�װ��ת��")
    public void test9() {
        Class<?> wrapClass = Integer.class;
 
        Class<?> unWraped = Convert.unWrap(wrapClass);
 
        Class<?> primitiveClass = long.class;
 
        Class<?> wraped = Convert.wrap(primitiveClass);
        p("��װ����", wrapClass, "ԭʼ����" , unWraped);   
        p("ԭʼ����", primitiveClass, "wraped" , wraped);  
    }
 
    private String preComment = null;  
    private void p(String type1, Object value1, String type2, Object value2) {
        try {
            throw new Exception();
        } catch (Exception e) {
            String methodName = e.getStackTrace()[1].getMethodName();
            Method m =ReflectUtil.getMethod(this.getClass(), methodName);
            Comment annotation = m.getAnnotation(Comment.class);
            if(null!=annotation) {
                String comment= annotation.value();
                if(!comment.equals(preComment)) {
                    System.out.printf("%n%s ���ӣ� %n%n",comment);
                    preComment = comment;
                }
                 
            }
        }
        int padLength = 12;
        type1=StrUtil.padEnd(type1,padLength,Convert.toSBC(" ").charAt(0));
        type2=StrUtil.padEnd(type2,padLength,Convert.toSBC(" ").charAt(0));
        System.out.printf("\t%s��:\t\"%s\" %n\t��ת��Ϊ----->%n\t%s�� :\t\"%s\" %n%n",type1,value1, type2, value2);
    }
     
    @Target({METHOD,TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface Comment {
         String value();
    }
}