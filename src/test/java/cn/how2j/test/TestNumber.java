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
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestNumber {
 
    @Test
    @Comment("��ȷ����")
    public void test1(){
        double result1 = (1.2 - 0.4);
        p3("���������� 1.2 - 0.4 �޷��õ���ȷ���",result1);
        double result2 = NumberUtil.sub(1.2, 0.4);
        p3("���������� NumberUtil.sub(1.2,0.4) ���ܵõ���ȷ���",result2);
    }
    @Test
    @Comment("��������")
    public void test2(){
        double a = 100.123;
        double b = 100.125;
         
        double result1= NumberUtil.round(a, 2).doubleValue();
        double result2= NumberUtil.round(b, 2).doubleValue();
         
        p1("������", a, "��������֮��", result1);
        p1("������", b, "��������֮��", result2);
         
    }
    @Test
    @Comment("���ָ�ʽ��")
    public void test3(){
         
//      0 -> ȡһλ����
//      0.00 -> ȡһλ��������λС��
//      00.000 -> ȡ��λ��������λС��
//      # -> ȡ������������
//      #.##% -> �԰ٷֱȷ�ʽ��������ȡ��λС��
//      #.#####E0 -> ��ʾΪ��ѧ����������ȡ��λС��
//      ,### -> ÿ��λ�Զ��Ž��зָ������磺299,792,458
//      ���ٴ�СΪÿ��,###�� -> ����ʽǶ���ı�
         
        p3("�Ԧн��и�ʽ�����е�ֵ��",Math.PI);
        double pi= Math.PI;
        String format = null;
        String str = null;
        format= "0";
        str = NumberUtil.decimalFormat(format,pi);
        p2("��ʽ",format,"��ʽ����õ�", str);
 
        format= "0.00";
        str = NumberUtil.decimalFormat(format,pi);
        p2("��ʽ",format,"��ʽ����õ�", str);
 
        format= "00.000";
        str = NumberUtil.decimalFormat(format,pi);
        p2("��ʽ",format,"��ʽ����õ�", str);
 
        format= "#";
        str = NumberUtil.decimalFormat(format,pi);
        p2("��ʽ",format,"��ʽ����õ�", str);
 
        format= "#.##";
        str = NumberUtil.decimalFormat(format,pi);
        p2("��ʽ",format,"��ʽ����õ�", str);
 
        format= "#.##%";
        str = NumberUtil.decimalFormat(format,pi);
        p2("��ʽ",format,"��ʽ����õ�", str);
         
        format= "#.####E0";
        str = NumberUtil.decimalFormat(format,pi);
        p2("��ʽ",format,"��ʽ����õ�", str);
 
        format= ",###";
        str = NumberUtil.decimalFormat(format,pi*10000);
        p2("��ʽ",format,"x1000 �ٸ�ʽ����õ�", str);
 
        format= ",####";
        str = NumberUtil.decimalFormat(format,pi*10000);
        p2("��ʽ",format,"x1000 �ٸ�ʽ����õ�", str);
 
        format= "�еĴ�С��#.##########����κ����";
        str = NumberUtil.decimalFormat(format,pi);
        p2("��ʽ",format,"��ʽ����õ�", str);
          
    }
    @Test
    @Comment("�����ж�")
    public void test4(){
        String s1 = "3.1415926";
        int n = 11;
        p2("�ַ���",s1, "�Ƿ�����",NumberUtil.isNumber(s1));
        p2("�ַ���",s1, "�Ƿ�����(���������)",NumberUtil.isInteger(s1));
        p2("�ַ���",s1, "�Ƿ񸡵���",NumberUtil.isDouble(s1));
        p2("����",n, "�Ƿ�����",NumberUtil.isPrimes(n));
    }
    @Test
    @Comment("�����")
    public void test5(){
        int random[]=NumberUtil.generateRandomNumber(1,1000,10);
         
        p3("��С��1�������1000���ܳ�����10�Ĳ��ظ��������",Convert.toStr(random));
    }
    @Test
    @Comment("�����б�")
    public void test6(){
        int numbers[] = NumberUtil.range(0, 100, 9);
        p3("��С��0�������100��������9������",Convert.toStr(numbers));
    }
    @Test
    @Comment("�������")
    public void test7(){
         
        p3("����3�Ľ׳�", NumberUtil.factorial(3));
        p3("����9��ƽ����", NumberUtil.sqrt(9));
        p3("����9��6�����Լ��", NumberUtil.divisor(9,6));
        p3("����9��6����С������", NumberUtil.multiple(9,6));
        p3("�������9��Ӧ�Ķ������ַ���", NumberUtil.getBinaryStr(9));
        p3("��ȡ123456789��Ӧ���", NumberUtil.decimalFormatMoney(123456789));
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