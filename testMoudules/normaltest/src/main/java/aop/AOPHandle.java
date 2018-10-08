package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AOPHandle implements InvocationHandler {
    //保存对象
    private AOPMethod method;
    private Object o;

    public AOPHandle(Object o) {
        this.o = o;
    }

    public AOPHandle(Object o, AOPMethod method) {
        this.o = o;
        this.method = method;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = null;
        //修改的地方在这里哦
        if (this.method != null) {
            this.method.before(proxy, method, args);
        }
        System.out.println("1111");
        ret = method.invoke(o, args);
        //修改的地方在这里哦
        if (this.method != null) {
            this.method.after(proxy, method, args);
        }
        System.out.println("2222");
        return ret;
    }

    public Object invoke11(Object proxy, Method method, Object[] args) throws Throwable {
        //方法返回值
        Object ret = null;
        //打印方法名称
        System.err.println("执行方法:" + method.getName() + "n参数类型为:");
        //打印参数
        for (Class type : method.getParameterTypes())
            System.err.println(type.getName());
        //打印返回类型
        System.err.println("返回数据类型:" + method.getReturnType().getName());
        //反射调用方法
        ret = method.invoke(o, args);
        //声明结束
        System.err.println("方法执行结束");
        //返回反射调用方法的返回值
        return ret;
    }
}
