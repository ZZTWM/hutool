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
 
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestNet {
 
    @Test
    @Comment("ipv4 �� long ����")
    public void test1(){
        String ip = "220.181.57.216";
        long value = 0L;
         
        value = NetUtil.ipv4ToLong(ip);
        ip = NetUtil.longToIpv4(value);
        p2("ip��ַ",ip, "��Ӧ��long", value);
        p2("longֵ ",value, "��Ӧ��ip", ip);
         
    }  
     
    @Test
    @Comment("�ж϶˿ں͵�ַ")
    public void test2(){
        int port1 =80;
        int port2 =68000;
        String ip1 = "220.181.57.216";
        String ip2 = "192.168.0.8";
        p2("�˿ں�",port1,  "�Ƿ��Ѿ���ռ��",!NetUtil.isUsableLocalPort(port1));
        p2("�˿ں�",port2,  "�Ƿ�һ����Ч�Ķ˿ں�",NetUtil.isValidPort(port2));
        p2("ip��ַ",ip1,  "�Ƿ��Ǹ�������ַ",NetUtil.isInnerIP(ip1));
        p2("ip��ַ",ip2,  "�Ƿ��Ǹ�������ַ",NetUtil.isInnerIP(ip2));
    }  
     
    @Test
    @Comment("������ز���")
    public void test3(){
         
        String ip = "220.181.57.216";
        String host = "baidu.com";
         
        p2("ԭip",ip,"�������һλ",NetUtil.hideIpPart(ip));
        p2("����",host,"��Ӧ��ip��ַ",NetUtil.getIpByHost(host));
        p3("����ip��ַ",NetUtil.localIpv4s());
        p3("����mac��ַ",NetUtil.getLocalMacAddress());
         
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