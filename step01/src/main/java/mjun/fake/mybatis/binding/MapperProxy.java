package mjun.fake.mybatis.binding;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

// 将来代理所有Mapper(dao)的代理对类
public class MapperProxy<Mapper> implements InvocationHandler, Serializable {

    @Serial
    private static final long serialVersionUID = -6424540398559729838L;

    // 将来所有与DB的直接操作将经由sqlSession来完成
    private final Map<String, String> sqlSession;
    // 被代理的Mapper类
    private final Class<Mapper> mapperInterface;

    public MapperProxy(Map<String, String> sqlSession, Class<Mapper> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    // 代理逻辑
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Object类里的方法不会被代理, 仅代理mapperInterface中的接口
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            return "你的被代理了！" + sqlSession.get(mapperInterface.getName() + "." + method.getName());
        }
    }
}
