import bean.Student;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.Car;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;
import utils.RegexUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TestA {
    private static ConcurrentMap<String, Integer> uriMap = new ConcurrentHashMap();

    public static ConcurrentHashMap<String, List<Student>> map = new ConcurrentHashMap<>();

    static {
        Student s1 = new Student();
        s1.setScore(1);
        Student s2 = new Student();
        s2.setScore(2);
        Student s3 = new Student();
        s3.setScore(3);
        Student s4 = new Student();
        s4.setScore(4);
        Student s5 = new Student();
        s5.setScore(5);

        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        studentList.add(s4);
        studentList.add(s5);

        map.put("aaa", studentList);
    }

    @Test

    public void test111() {

        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());

        System.out.println(uriMap.get("asdf"));
    }

    @Test
    public void test1() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Student student = new Student();
        student.setName("123");


        Field[] fields = student.getClass().getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), student.getClass());
            Method read = pd.getReadMethod();
            Method write = pd.getWriteMethod();
            Object name = read.invoke(student);
            System.out.println(field.getType().equals(Integer.class));
            field.getAnnotatedType();
            if (field.getName().equals("score")) {
                write.invoke(student, 321);
            }
            System.out.println(name);
        }
        System.out.println(student.getScore());
    }

    @Test
    public void test2() {
        String url = "http://172.16.10.145/group1/M00/00/00/rBAKkVsXrsyAVG5AAAZIEoVhbfM199.jpg_200x200.jpg";
        String uri = RegexUtil.urlToUri(url);
        System.out.println(uri);
    }

    @Test
    public void test3() {
        List<Student> studentList = getList();

//        String string = JSON.toJSONString(studentList);
//        List<Student> jsonList = JSON.parseArray(string, Student.class);

        List<Student> subList1 = studentList.subList(0, 2);
        List<Student> subList2 = studentList.subList(0, 2);

        Set<Student> studentSet = new HashSet<>();
        studentSet.addAll(studentList);

        Iterator<Student> iterator = studentSet.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            student.setName("qefa");
            iterator.remove();
        }

/*        Iterator<Student> it = ss.iterator();
        while(it.hasNext()){
            System.out.println(it.next().getScore());
            it.remove();
        }*/

        Integer a = 5;
        int b = 2;

        System.out.println(Double.valueOf(a) / b);

        System.out.println(JSON.toJSON(subList1));
        System.out.println(JSON.toJSON(subList2));
        System.out.println(JSON.toJSON(map));
    }

    @Test
    public void testList() {
        List<Student> studentList = getList();
        int batchSizeLimit = 2;
        int totalSize = studentList.size();
        int batchSize = batchSizeLimit;
        while (!studentList.isEmpty()) {
            if (studentList.size() < batchSizeLimit) {
                batchSize = studentList.size();
            }
            List<Student> subList = new ArrayList<>(studentList.subList(0, batchSize));
            System.out.println(subList);
            studentList.subList(0, batchSize).clear();
            System.out.println(subList);
        }
/*        for (int i = 0; i <= totalSize / batchSize; i++) {
            List<Student> subList = studentList.subList(0, batchSize);
            studentList.subList(0, batchSize).clear();
            System.out.println(subList);
        }*/

        System.out.println(studentList);
    }

    public List<Student> getList() {
        return map.get("aaa");
    }

    @Test
    public void test4() {
        Student student = new Student();
        Field[] fields = student.getClass().getDeclaredFields();
        PropertyDescriptor pd = null;
        for (Field field : fields) {
            System.out.println(field.getName());
            try {
                pd = new PropertyDescriptor(field.getName(), student.getClass());
                Method read = pd.getReadMethod();
                Object value = read.invoke(student);

                Method write = pd.getWriteMethod();
                if ("name".equals(field.getName()))
                    write.invoke(student, "1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(student.getName());
    }

    @Test
    public void test5() {
        //方法一
        long milliSecondsLeftToday = 86400000 - DateUtils.getFragmentInMilliseconds(Calendar.getInstance(), Calendar.DATE);
        long secondsLeftToday = 86400 - DateUtils.getFragmentInSeconds(Calendar.getInstance(), Calendar.DATE);
        System.out.println("当天剩余毫秒1：" + milliSecondsLeftToday);
        System.out.println("当天剩余秒1：" + secondsLeftToday);


        //方法二
        DateTime dateTime = new DateTime().millisOfDay().withMaximumValue();
        long millSeconds2 = new Duration(new DateTime(), dateTime).getMillis();
        long count = new Duration(new DateTime(), dateTime).getStandardSeconds();
        System.out.println("当天剩余毫秒2：" + millSeconds2);
        System.out.println("当天剩余秒2：" + count);


        //方法三:LocalDateTime和ChronoUnit为1.8新增
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long millSeconds = ChronoUnit.MILLIS.between(LocalDateTime.now(), midnight);
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
        System.out.println("当天剩余毫秒3：" + millSeconds);
        System.out.println("当天剩余秒3：" + seconds);
    }

    Integer b;
    int c;

    @Test
    public void test6() {
        Integer a = 1;
        int b = a;
        System.out.println(a == null);
        System.out.println(String.valueOf(c));
    }

    @Test
    public void test7() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        System.out.println(df.format(calendar.getTime()));
    }

    @Test
    public void test8() {
        JSONObject obj = JSON.parseObject("a:2");
        obj.get("1");
    }

    @Test
    public void test9() {
        int a = 124132;
        int b = 354234567;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void test10() throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Car car1 = new Car();
        car1.setMani("toyota");

        Car car2 = (Car) car1.clone();
        car2.setMani("auto");

        System.out.println(car1.getMani());

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(cars);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        List<Car> carList = (List<Car>) ois.readObject();

//        List<Car> carList = new ArrayList(Arrays.asList(new String[cars.size()]));
//        Collections.copy(carList, cars);

        carList.get(0).setMani("fe");
        carList.get(1).setMani("benz");
        carList.add(car1);
        System.out.println(carList);
    }

    @Test
    public void loadArea() throws IOException {
        String filePath = "/Users/temp/province.json";
        File file = new File(filePath);

        String content = FileUtils.readFileToString(file, "UTF-8");
        JSONArray array = JSON.parseArray(content);
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            String proCode = object.getString("code");
            String proName = object.getString("name");

            JSONArray cityList = object.getJSONArray("cityList");
        }



    }

    @Test
    public void test17 () {
        String str="hello            song";
//        Pattern p = Pattern.compile("\\s+");
        String regex = " +";
        str.replaceAll(regex, "a");
        System.out.println(str);
    }

    @Test
    public void test18 () {
        Set<String> stringSet = new HashSet<>();
        stringSet.add("a");
        stringSet.add("b");
        stringSet.add("c");

        String[] strings = new String[3];
        stringSet.toArray(strings);
        System.out.println(strings[1]);
    }
}
