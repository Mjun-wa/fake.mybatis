package mjun.fake.mybatis.binding;

import java.lang.reflect.Proxy;
import java.util.Map;

// Mapper代理类的工程类
public class MapperProxyFactory<Mapper> {

    // 被代理的Mapper类
    private final Class<Mapper> mapperInterface;

    public MapperProxyFactory(Class<Mapper> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Mapper newInstance(Map<String, String> sqlSession) {
        final MapperProxy<Mapper> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface);
        return (Mapper) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
