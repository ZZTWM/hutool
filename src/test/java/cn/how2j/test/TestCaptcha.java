package cn.how2j.test;
 
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
 
import java.io.OutputStream;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
 
import org.junit.Test;
 
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestCaptcha {
 
    @Test
    @Comment("���� �߶θ��ŵ���֤��")
    public void test1(){
        int width = 200;
        int height = 100;
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width, height);
        p3("��ǰ����֤����",captcha.getCode());
        String path = "d:/captcha1.png";
        captcha.write(path);
    }
    @Test
    @Comment("���� ԲȦ���ŵ���֤��")
    public void test2(){
        int width = 200;
        int height = 100;
        int codeCount = 5;
        int circleCount = 40;
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(width, height,codeCount,circleCount);
        p3("��ǰ����֤����",captcha.getCode());
        String path = "d:/captcha2.png";
        captcha.write(path);
    }
    @Test
    @Comment("���� Ť���߸��ŵ���֤��")
    public void test3(){
        int width = 200;
        int height = 100;
        int codeCount = 5;
        int thickness = 2;
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(width, height,codeCount,thickness);
        p3("��ǰ����֤����",captcha.getCode());
        String path = "d:/captcha3.png";
        captcha.write(path);
    }
    @Test
    @Comment("web ҳ�����")
    public void test4(){
        //junit �Ͼ�����servlet �������ò��� response���� ������α����
        int width = 200;
        int height = 100;
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width, height);
        OutputStream out = null;
//      out = HttpServletResponse.getOutputStream();
//      captcha.write(out);
        IoUtil.close(out);
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