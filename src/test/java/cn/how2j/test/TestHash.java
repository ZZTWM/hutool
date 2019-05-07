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
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
  
public class TestHash {
    @Test
    @Comment("���ָ�����hash�㷨")
    public void test2() {
        String s = "how2j.cn- java�̳�";
        int number = 12;
        long hash1 = HashUtil.additiveHash(s, Integer.MAX_VALUE);
        long hash2 = HashUtil.rotatingHash(s, Integer.MAX_VALUE);
        long hash3 = HashUtil.oneByOneHash(s);
        long hash4 = HashUtil.bernstein(s);
//      long hash5 = HashUtil.universal(s); ��ô���ã�վ��������á���������~~~
//      long hash6 = HashUtil.zobrist(s);
        long hash7 = HashUtil.fnvHash(s);
        long hash8 = HashUtil.intHash(number);
        long hash9 = HashUtil.rsHash(s);
        long hash10 = HashUtil.jsHash(s);
        long hash11 = HashUtil.pjwHash(s);
        long hash12 = HashUtil.elfHash(s);
        long hash13 = HashUtil.bkdrHash(s);
        long hash14 = HashUtil.sdbmHash(s);
        long hash15 = HashUtil.djbHash(s);
        long hash16 = HashUtil.dekHash(s);
        long hash17 = HashUtil.apHash(s);
        long hash18 = HashUtil.tianlHash(s);
        long hash19 = HashUtil.javaDefaultHash(s);
        long hash20 = HashUtil.mixHash(s);     
        p2("ԭ����",s, "�ӷ��㷨��Ӧ�Ĺ�ϣֵ", hash1);
        p2("ԭ����",s, "��ת�㷨��Ӧ�Ĺ�ϣֵ", hash2);
        p2("ԭ����",s, "һ��һ���㷨��Ӧ�Ĺ�ϣֵ", hash3);
        p2("ԭ����",s, "Bernstein's�㷨��Ӧ�Ĺ�ϣֵ", hash4);
//      p2("ԭ����",s, " Universal �㷨��Ӧ�Ĺ�ϣֵ", hash5);
//      p2("ԭ����",s, " Zobrist �㷨��Ӧ�Ĺ�ϣֵ", hash6);
        p2("ԭ����",s, " �Ľ���32λFNV �㷨��Ӧ�Ĺ�ϣֵ", hash7);
        p2("ԭ����",s, "Thomas Wang�������㷨��Ӧ�Ĺ�ϣֵ", hash8);
        p2("ԭ����",s, "RS�㷨��Ӧ�Ĺ�ϣֵ", hash9);
        p2("ԭ����",s, "JS�㷨��Ӧ�Ĺ�ϣֵ", hash10);
        p2("ԭ����",s, "PJ�㷨��Ӧ�Ĺ�ϣֵ", hash11);
        p2("ԭ����",s, "ELF�㷨��Ӧ�Ĺ�ϣֵ", hash12);
        p2("ԭ����",s, "BKDR�㷨��Ӧ�Ĺ�ϣֵ", hash13);
        p2("ԭ����",s, "SDBM�㷨��Ӧ�Ĺ�ϣֵ", hash14);
        p2("ԭ����",s, "DJB�㷨��Ӧ�Ĺ�ϣֵ", hash15);
        p2("ԭ����",s, "DEK�㷨��Ӧ�Ĺ�ϣֵ", hash16);
        p2("ԭ����",s, "AP�㷨��Ӧ�Ĺ�ϣֵ", hash17);
        p2("ԭ����",s, "TianL�㷨��Ӧ�Ĺ�ϣֵ", hash18);
        p2("ԭ����",s, "JAVA�Լ����㷨��Ӧ�Ĺ�ϣֵ", hash19);
        p2("ԭ����",s, "����㷨��Ӧ�Ĺ�ϣֵ", hash20);
         
    }
  
    private String preComment = null; 
    private void c(String msg) {
        System.out.printf("\t��ע��%s%n",msg);
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
        if("format1".equals(format)) {
            System.out.printf("\t%s��:\t\"%s\" %n\t��ת��Ϊ----->%n\t%s�� :\t\"%s\" %n%n",type1,value1, type2, value2);
        }
        if("format2".equals(format)) {
            System.out.printf("\t���� %s:\t\"%s\" %n\t��ȡ %s:\t\"%s\"%n%n",type1,value1, type2, value2);
        }
        if("format3".equals(format)) {
            System.out.printf("\t%s:\t\"%s\" %n\t%n",type1,value1);
  
        }
    }
      
    private String getTestMethodName(StackTraceElement[] stackTrace) {
        for (StackTraceElement se : stackTrace) {
            String methodName = se.getMethodName();
            if(methodName.startsWith("test"))
                return methodName;
        }
        return null;
    }
  
    @Target({METHOD,TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface Comment {
         String value();
    }
}