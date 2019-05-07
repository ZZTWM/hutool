package cn.how2j.test;
 
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
 
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Map;
 
import org.junit.Test;
 
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestArray {
 
    @Test
    @Comment("Ϊ���ж�")
    public void test1(){
        int [] a = null;
        int [] b = new int[5];
        int [] c = new int[] {10,11,12};
         
        p1("����",Convert.toStr(a),"�Ƿ�Ϊ��", ArrayUtil.isEmpty(a));
        p1("����",Convert.toStr(b),"�Ƿ�Ϊ��", ArrayUtil.isEmpty(b));
        p1("����",Convert.toStr(c),"�Ƿ�Ϊ��", ArrayUtil.isEmpty(c));
         
    }
    @Test
    @Comment("���������С")
    public void test2(){
        Integer [] a = new Integer[] {10,11,12};
        Integer[] b = ArrayUtil.resize(a,5);
         
        p3("������Сǰ������", Convert.toStr(a) );
        p3("������С�������", Convert.toStr(b) );
         
    }
    @Test
    @Comment("�ϲ�����")
    public void test3(){
        Integer [] a = {1,2,3};
        Integer [] b = {10,11,12};
        Integer [] c = ArrayUtil.addAll(a,b);
         
        p2("�ϲ�ǰ���������� " ,Convert.toStr(a) + " , " + Convert.toStr(b), "�ϲ����������", Convert.toStr(c));
    }
     
    @Test
    @Comment("��¡")
    public void test4(){
        Integer [] a = {1,2,3};
        Integer b[]= ArrayUtil.clone(a);
         
        p2("ԭ����",Convert.toStr(a), "��¡������", Convert.toStr(b));
         
    }
    @Test
    @Comment("������������")
    public void test5(){
        p3("���ɿ�ʼ��0��������100��������9����������", Convert.toStr( ArrayUtil.range(0, 100, 9)));
    }
    @Test
    @Comment("����")
    public void test6(){
        Integer []a = {1,2,3,4,5,6,7,8,9};
        Integer[] b = ArrayUtil.filter(a,new Filter<Integer>() {
 
            @Override
            public boolean accept(Integer t) {
                if(0==t%3)
                    return true;
                return false;
            }
             
        });
         
        p2("ԭ����",Convert.toStr(a),"3�ı�������֮��",Convert.toStr(b));
         
    }
    @Test
    @Comment("ת��Ϊmap")
    public void test7(){
        Integer a[] = {1,2,3};
        String c[] = {"a","b","c"};
        Map<Integer,String> m = ArrayUtil.zip(a, c);
         
        p2("��������",Convert.toStr(a) + " , " + Convert.toStr(c),"ת��Ϊ Map ",m);
         
    }
    @Test
    @Comment("�Ƿ����ĳԪ��")
    public void test8(){
        Integer a[] = {1,2,3};
         
        p1("����", Convert.toStr(a),"�Ƿ����Ԫ��3", ArrayUtil.contains(a, 3));
         
    }
    @Test
    @Comment("װ�����")
    public void test9(){
        int a[] = {1,2,3};
        Integer b[] = ArrayUtil.wrap(a);
        int c[] = ArrayUtil.unWrap(b);
         
        p3("����������͵�װ�����","ArrayUtil.wrap | ArrayUtil.unWrap");
    }
    @Test
    @Comment("ת��Ϊ�ַ���")
    public void testa(){
        int a[] = {1,2,3};
         
        p3("����ת��ΪĬ���ַ���", ArrayUtil.toString(a) );
        p3("����ת��Ϊ�Զ���ָ������ַ���", ArrayUtil.join(a,"-" ));
         
    }
    @Test
    @Comment("���")
    public void testb(){
         
        byte []a = {1,2,3,4,5,6,7,8,9};
         
        byte[][] b=ArrayUtil.split(a, 2);
         
        p3("���鱻���2Ϊ���ȵĵȷ�",Convert.toStr(a));
         
        for (byte[] bs : b) {
            p3("��ֺ�����飺",Convert.toStr(bs));
        }
                 
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