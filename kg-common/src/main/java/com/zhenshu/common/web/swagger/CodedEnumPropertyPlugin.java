package com.zhenshu.common.web.swagger;

import com.fasterxml.classmate.ResolvedType;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.Annotations;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jing
 * @version 1.0
 * @desc 枚举
 * @date 2022/2/16 0016 10:53
 **/
@Component
public class CodedEnumPropertyPlugin implements ModelPropertyBuilderPlugin {
    @Override
    public void apply(ModelPropertyContext context) {
        Optional<ApiModelProperty> annotation = Optional.empty();

        if (context.getAnnotatedElement().isPresent()) {
            annotation = ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get());
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = Annotations.findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class);
        }
        final Class<?> rawPrimaryType = context.getBeanPropertyDefinition().get().getRawPrimaryType();
        //过滤得到目标类型
        if (annotation.isPresent() && IBaseEnum.class.isAssignableFrom(rawPrimaryType)) {
            //获取CodedEnum的code值
            IBaseEnum[] values = (IBaseEnum[]) rawPrimaryType.getEnumConstants();
            final List<String> displayValues = Arrays.stream(values).map(baseEnum -> (baseEnum.getCode()) + "-" + baseEnum.getInfo()).collect(Collectors.toList());
            final AllowableListValues allowableListValues = new AllowableListValues(displayValues, rawPrimaryType.getTypeName());
            // 固定设置为int类型
            final ResolvedType resolvedType = context.getResolver().resolve(String.class);
            context.getBuilder().allowableValues(allowableListValues).type(resolvedType);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
