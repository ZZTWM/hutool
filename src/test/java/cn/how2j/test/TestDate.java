package cn.how2j.test;
 
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
 
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Date;
 
import org.junit.Test;
 
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormater.Level;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestDate {
    @Test
    @Comment("�ַ���ת����")
    public void test0() {
        printDefaultFormat();
        Date d;
        String str3 = "12:12:12";
        d = DateUtil.parse(str3);
        p1("�ַ���",str3, "���ڸ�ʽ",d);
         
        String str1 = "2012-12-12";
        d = DateUtil.parse(str1);
        p1("�ַ���",str1, "���ڸ�ʽ",d);
         
        String str4 = "2012-12-12 12:12";
        d = DateUtil.parse(str4);
        p1("�ַ���",str4, "���ڸ�ʽ",d);
         
        String str2 = "2012-12-12 12:12:12";
        d = DateUtil.parse(str2);
        p1("�ַ���",str2, "���ڸ�ʽ",d);
    }
     
    @Test
    @Comment("����ת�ַ���")
    public void test1() {
        Date d = new Date();
         
        //��� 2017/03/01
        String format = DateUtil.format(d, "yyyy/MM/dd");
 
        //���ø�ʽ�ĸ�ʽ���������2017-03-01
        String formatDate = DateUtil.formatDate(d);
 
        //�����2017-03-01 00:00:00
        String formatDateTime = DateUtil.formatDateTime(d);
 
        //�����00:00:00
        String formatTime = DateUtil.formatTime(d);
         
        p1("���ڸ�ʽ",d, "�Զ����ʽ���ַ���",format);
        p1("���ڸ�ʽ",d, "ֻ�����ڸ�ʽ",formatDate);
        p1("���ڸ�ʽ",d, "���ں�ʱ���ʽ",formatDateTime);
        p1("���ڸ�ʽ",d, "ֻ��ʱ���ʽ",formatTime);
    }
     
    @Test
    @Comment("��ȡ������Ϣ")
    public void test2() {
        Date d = new Date();
        //�����Ĳ���
        int year= DateUtil.year(d);
        //����·ݣ���0��ʼ����
        int month = DateUtil.month(d);
        //����·�ö��
        Enum months= DateUtil.monthEnum(d);
         
        p2("��ǰ����",DateUtil.formatDateTime(d), "���",year);
        p2("��ǰ����",DateUtil.formatDateTime(d), "�·�",month);
        p2("��ǰ����",DateUtil.formatDateTime(d), "�·�ö����Ϣ",months);
         
    }
 
    @Test
    @Comment("��ʼ�ͽ���ʱ��")
    public void test3() {
        Date date = new Date();
 
        //һ��Ŀ�ʼ�������2017-03-01 00:00:00
        Date beginOfDay = DateUtil.beginOfDay(date);
 
        //һ��Ľ����������2017-03-01 23:59:59
        Date endOfDay = DateUtil.endOfDay(date);
        p2("��ǰ����",DateUtil.formatDateTime(date), "��ʼʱ��",beginOfDay);
        p2("��ǰ����",DateUtil.formatDateTime(date), "����ʱ��",endOfDay);
        c("����ڲ�ѯ���ݿ�ʱ���������ڲ�һ����Χ�ڵ����ݾͺ�����");
    }
     
    @Test
    @Comment("����ʱ��ƫ��")
    public void test4() {
        Date date = new Date();
 
        Date d1 = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
 
        Date d2 = DateUtil.offsetDay(date, 3);
 
        Date d3= DateUtil.offsetHour(date, -3);
         
        p2("��ǰ����",DateUtil.formatDateTime(date), "����֮�������",d1);
        p2("��ǰ����",DateUtil.formatDateTime(date), "����֮�������",d2);
        p2("��ǰ����",DateUtil.formatDateTime(date), "��Сʱ֮ǰ������",d3);
         
    }
    @Test
    @Comment("ƫ�Ƽ��÷�")
    public void test5() {
        Date date = new Date();
        Date d1= DateUtil.yesterday();
        Date d2=DateUtil.tomorrow();
        Date d3=DateUtil.lastWeek();
        Date d4=DateUtil.nextWeek();
        Date d5=DateUtil.lastMonth();
        Date d6=DateUtil.nextMonth();
        p2("��ǰ����",DateUtil.formatDateTime(date), "����",d1);
        p2("��ǰ����",DateUtil.formatDateTime(date), "����",d2);
        p2("��ǰ����",DateUtil.formatDateTime(date), "����",d3);
        p2("��ǰ����",DateUtil.formatDateTime(date), "����",d4);
        p2("��ǰ����",DateUtil.formatDateTime(date), "�ϸ���",d5);
        p2("��ǰ����",DateUtil.formatDateTime(date), "�¸���",d6);
 
    }
    @Test
    @Comment("����ʱ���")
    public void test6() {
        Date date1 = DateUtil.parse("2012-12-12 12:12:12");
        Date date2 = DateUtil.parse("2013-13-13 13:13:13");
 
        long b1 = DateUtil.between(date1, date2, DateUnit.MS);
        long b2 = DateUtil.between(date1, date2, DateUnit.SECOND);
        long b3 = DateUtil.between(date1, date2, DateUnit.MINUTE);
        long b4 = DateUtil.between(date1, date2, DateUnit.HOUR);
        long b5 = DateUtil.between(date1, date2, DateUnit.DAY);
        long b6 = DateUtil.between(date1, date2, DateUnit.WEEK);
         
        p2("��ǰ������������",date1 + " " + date2, "������",b1);
        p2("��ǰ������������",date1 + " " + date2, "�����",b2);
        p2("��ǰ������������",date1 + " " + date2, "����",b3);
        p2("��ǰ������������",date1 + " " + date2, "���Сʱ",b4);
        p2("��ǰ������������",date1 + " " + date2, "�����",b5);
        p2("��ǰ������������",date1 + " " + date2, "�������",b6);
    }
    @Test
    @Comment("��ʽ��ʱ���")
    public void test7() {
        long between = System.currentTimeMillis();
         
        String s0 = DateUtil.formatBetween(between, Level.MILLSECOND);
        String s1 = DateUtil.formatBetween(between, Level.SECOND);
        String s2 = DateUtil.formatBetween(between, Level.MINUTE);
        String s3 = DateUtil.formatBetween(between, Level.HOUR);
        String s4 = DateUtil.formatBetween(between, Level.DAY);
        p2("������",between, "��Ӧʱ�䣬���ȵ�����",s0);
        p2("������",between, "��Ӧʱ�䣬���ȵ���",s1);
        p2("������",between, "��Ӧʱ�䣬���ȵ������",s2);
        p2("������",between, "��Ӧʱ�䣬���ȵ���Сʱ",s3);
        p2("������",between, "��Ӧʱ�䣬���ȵ�����",s4);
 
    }
    @Test
    @Comment("����ͳ��")
    public void test8() {
        int loopcount = 100;
        TimeInterval timer = DateUtil.timer();
        forloop(loopcount);
        long interval1= timer.interval();
        forloop(loopcount);
        long interval2 = timer.intervalRestart();
        forloop(loopcount);
        long interval3 = timer.interval();
         
        p3("����ͳ�ƣ��ܹ������� (������)",interval1);
        p3("����ͳ�ƣ��ܹ������� (������),������",interval2);
        p3("����ͳ�ƣ��ܹ������� (������)",interval3);
 
    }
 
    @Test
    @Comment("����")
    public void test9() {
 
        String birthDay = "1949-10-01";
        int age = DateUtil.ageOfNow(birthDay);
 
        int year = 2012;
        boolean isLeap= DateUtil.isLeapYear(year);
         
        String now = DateUtil.now();
        String today =  DateUtil.today();
         
        p2("����",birthDay,"����",age);
        p2("���",year,"�Ƿ�����",isLeap);
         
        p3("����",now);
        p3("����",today);
         
    }
 
    private void forloop(int total) {
        for (int i = 0; i < total; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
 
    public void printDefaultFormat() {
        System.out.println("DateUtilĬ�ϻ�����¸�ʽ����ʶ��");
        System.out.println();
 
        System.out.println("\tyyyy-MM-dd HH:mm:ss");
        System.out.println("\tyyyy/MM/dd HH:mm:ss");
        System.out.println("\tyyyy.MM.dd HH:mm:ss");
        System.out.println("\tyyyy��MM��dd�� HHʱmm��ss��");
        System.out.println("\tyyyy-MM-dd");
        System.out.println("\tyyyy/MM/dd");
        System.out.println("\tyyyy.MM.dd");
        System.out.println("\tHH:mm:ss");
        System.out.println("\tHHʱmm��ss��");
        System.out.println("\tyyyy-MM-dd HH:mm");
        System.out.println("\tyyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("\tyyyyMMddHHmmss");
        System.out.println("\tyyyyMMddHHmmssSSS");
        System.out.println("\tyyyyMMdd");
        System.out.println();
 
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