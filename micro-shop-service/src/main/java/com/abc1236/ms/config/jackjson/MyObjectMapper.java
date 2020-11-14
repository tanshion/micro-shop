package com.abc1236.ms.config.jackjson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@Slf4j
public class MyObjectMapper extends ObjectMapper {

    public MyObjectMapper() {
        super();
        configCommon();
        configNullValue();
        initModule();
    }

    private void configNullValue() {
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object obj, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
                String fieldName = gen.getOutputContext().getCurrentName();
                if (fieldName == null) {
                    gen.writeNull();
                    return;
                }
                //反射获取字段类型
                Field field = ReflectionUtils.findField(gen.getCurrentValue().getClass(), fieldName);
                if (field != null && Objects.equals(field.getType(), String.class)) {
                    //字符串型空值""
                    gen.writeString("");
                } else {
                    gen.writeNull();
                }
            }
        });
    }


    public void initModule() {
        SimpleModule module = new SimpleModule();
        //格式化日期
        module.addSerializer(Date.class, new DateSerializer());
        //module.addSerializer(BigInteger.class, ToStringSerializer.instance);
        //module.addSerializer(Long.class, ToStringSerializer.instance);
        //module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        //自定义枚举解析
        //module.addSerializer(EnumValue.class, new EnumValueSerializer());
        //module.addDeserializer(EnumValue.class, new EnumValueDeserializer());
        this.registerModule(module);

    }

    private void configCommon() {
        //序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
        this.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        //允许将JSON空字符串强制转换为null对象值
        this.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //允许单个数值当做数组处理
        this.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //禁止重复键, 抛出异常
        this.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        //禁止使用int代表Enum的order()來反序列化Enum, 抛出异常
        this.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        //有属性不能映射的时候不报错
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //使用null表示集合类型字段是时不抛异常
        this.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        //对象为空时不抛异常
        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //允许在JSON中使用c/c++风格注释
        this.enable(JsonParser.Feature.ALLOW_COMMENTS);
        //允许未知字段
        this.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        //在JSON中允许未引用的字段名
        this.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        //时间格式
        this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //识别单引号
        this.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        //识别Java8时间
        this.registerModule(new ParameterNamesModule());
        this.registerModule(new Jdk8Module());
        this.registerModule(new JavaTimeModule());
        //识别Guava包的类
        this.registerModule(new GuavaModule());
    }
}
