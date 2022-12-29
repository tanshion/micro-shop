package com.abc1236.ms.config.jackjson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@Configuration
public class JacksonConfig {

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        //序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
        objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        //允许将JSON空字符串强制转换为null对象值
        //objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //允许单个数值当做数组处理
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //禁止重复键, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        //禁止使用int代表Enum的order()來反序列化Enum, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        //有属性不能映射的时候不报错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //使用null表示集合类型字段是时不抛异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        //对象为空时不抛异常
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //允许在JSON中使用c/c++风格注释
        objectMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
        //允许未知字段
        objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        //在JSON中允许未引用的字段名
        objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        //时间格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //识别单引号
        objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);


        //识别Java8时间
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        //识别Guava包的类
        objectMapper.registerModule(new GuavaModule());
        SimpleModule MyModule = new SimpleModule() {

            @Override
            public void setupModule(SetupContext context) {
                super.setupModule(context);
                context.addBeanSerializerModifier(new MyBeanSerializerModifier());
            }
        };
        //格式化日期
        MyModule.addSerializer(Date.class, new DateSerializer());
        //MyModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        //MyModule.addSerializer(Long.class, ToStringSerializer.instance);
        //MyModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        //自定义枚举解析
        //MyModule.addSerializer(EnumValue.class, new EnumValueSerializer());
        //MyModule.addDeserializer(EnumValue.class, new EnumValueDeserializer());
        objectMapper.registerModule(MyModule);


        /** 为objectMapper注册一个带有SerializerModifier的Factory */
        //objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
        //        .withSerializerModifier(new MyBeanSerializerModifier()));
        //
        //SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
        //serializerProvider.setNullValueSerializer(new Null4StringSerializer());
        return objectMapper;
    }

    /**
     * 表示优先使用这个自定义mapper
     */
    @ConditionalOnMissingBean(ObjectMapper.class)
    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = createObjectMapper();
        return objectMapper;
    }

    public static class MyBeanSerializerModifier extends BeanSerializerModifier {

        private static final JsonSerializer<Object> _null4StringSerializer = new Null4StringSerializer();

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
            List<BeanPropertyWriter> beanProperties) {
            // 循环所有的beanPropertyWriter
            for (BeanPropertyWriter writer : beanProperties) {
                // 判断字段的类型
                Class<?> clazz = writer.getType().getRawClass();
                if (CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
                    writer.assignNullSerializer(_null4StringSerializer);
                }
            }
            return beanProperties;
        }
    }

    public static class Null4StringSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeString("");
        }
    }
}
