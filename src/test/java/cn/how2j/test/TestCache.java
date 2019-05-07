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
 
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.file.LFUFileCache;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
public class TestCache {
    @Test
    @Comment("���湤��")
    public void test0(){
        p4("hutool�Ļ��棬�ڴ��������˻��ߵ��ڵ�ʱ�������¼��ֲ��ԣ�");
 
        p3("FIFOCache","first in first out , �����ȳ���һ���������ˣ��ȷŽ�ȥ�ģ��ȱ����");
        p3("LFUCache","least frequently used, һ���������ˣ��õ����ٵģ��ȱ���� (���е͵����ݱ����)");
        p3("LRUCache","least recently used, һ���������ˣ����û�õģ��ȱ���� (�����ݱ����)");
        p3("TimedCache","һ��ʱ�䵽�ˣ������ (����ʱЧ��)");
        p3("WeakCache","һ���ڴ����ˣ�Ҫ���������ˣ����ȱ���� (�ڴ�ռ����Ҫ��)");
        p3("FileCach","���ļ�������Ϊ���棬����IO����Ƶ��");
         
//      LFUCache
//      LRUCache least recently used
//      TimedCache
//      WeakCache
//      FileCach
    }
     
    @Test
    @Comment("FIFO ʾ��")
    public void test1(){
        p4("��ʼ�������С�� 2");
        p4("���������氤������1��2��3�����»������");
        Cache<String,Integer> cache= CacheUtil.newFIFOCache(2);
        cache.put("key1",1 );
        cache.put("key2",2 );
        cache.put("key3",3 );
        p3("���������е�����",CollectionUtil.join(cache, ","));
        p4("��Ԥ��һ�㣬�����������ȱ����");
    }
     
    @Test
    @Comment("LFUCache ʾ��")
    public void test2(){
        p4("��ʼ�������С�� 2");
        p4("���������氤������1��2��3�����»������");
        Cache<String,Integer> cache= CacheUtil.newLFUCache(2);
        cache.put("key1",1 );
        cache.put("key2",2 );
        p4("��;����ʹ��һ�� key1");
        cache.get("key1");
        cache.put("key3",3 );
        p3("���������е�����",CollectionUtil.join(cache, ","));
        p4("��Ԥ��һ�㣬ʹ��Ƶ����͵� 2 �ı����");
    }
 
    @Test
    @Comment("LFUCache ʾ��")
    public void test3(){
        p4("��ʼ�������С�� 2");
        p4("���������氤������1��2��3�����»������");
        Cache<String,Integer> cache= CacheUtil.newLRUCache(2);
        cache.put("key1",1 );
        cache.put("key2",2 );
        p4("��;����ʹ��һ�� key1");
        cache.get("key1");
        cache.put("key3",3 );
        p3("���������е�����",CollectionUtil.join(cache, ","));
        p4("��Ԥ��һ�㣬���û�б�ʹ�õ�  2 �ı����");
    }
 
    @Test
    @Comment("TimedCache ʾ��")
    public void test4(){
        p4("��ʼ�������С�� 2");
        p4("���������氤������1��2�� �ֱ�����ô��ʱ��Ϊ1���5��");
        Cache<String,Integer> cache= CacheUtil.newTimedCache(Integer.MAX_VALUE);
        cache.put("key1",1, 1000 );
        cache.put("key2",2,5000 );
        p4("��Ϣ3��");
        ThreadUtil.sleep(3000);
        p3("���������е�����",CollectionUtil.join(cache, ","));
        p4("��Ԥ��һ�㣬 ����3���1������ˣ�2����");
    }
     
    @Test
    @Comment("WeakCache ʾ��")
    public void test5(){
        p4("WeekCache��ʾ���������շ�����ʱ�򣬲����赲���������������ߡ�");
         
        p4("��ע�⿴������\"�����赲\", ����˵��������������Ҫ���������ˣ��ǿ������ֵġ�");
        p4("�����������շ�����ʱ�򣬲�һ����������������� week���á�");
        p4("����Ϊ��ˣ����׹۲쵽���󣬶��Ҳ��ȶ������ԾͲ�����ʾ�ˣ������");
         
    }
     
    @Test
    @Comment("FileCache ʾ��")
    public void test6(){
        p4("FileCache Ҳ�� LFU, LRU �ȣ�ֻ�ǵ��÷�ʽ�������𣬲�û�б��ŵ� CacheUtil����˺�һ������ҵ���������");
        //����1�������������ɵ�byte��
        //����2������ļ���С��byte���������ܻ������ٶ����ļ����������ֵ��������ֱ�Ӷ�ȡ
        //����3����ʱ������
         
        long capacity = 1024*1024*500; //���500m, ̫���ˣ��ڴ�Բ����������û��ʵʩ��
        long maxFileSize = 1024*1024*10; //���10m, �ļ�С������ͻ��棬̫����Ҳ������
        long timeout = 1000*60*60*24; //����һ�죬����������Զ��ӻ������Ƴ���
         
        LFUFileCache cache = new LFUFileCache(1024*1024*500, 500, 2000);
        //ʹ�ð취��
        //byte[] bytes = cache.getFileBytes("e:/project/hutool/img/logo.png");
         
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
    private void p4(Object value) {
        p(null, value, "", "", "format4");
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
        if ("format4".equals(format)) {
            System.out.printf("\t%s%n%n", value1);
             
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