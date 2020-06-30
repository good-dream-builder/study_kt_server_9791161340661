package com.songko.simpleshopserver.config

import com.songko.simpleshopserver.interceptor.TokenValidationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig
@Autowired constructor(
        private val tokenValidationInterceptor: TokenValidationInterceptor
) : WebMvcConfigurer {
    @Value("\${simple-shop.file-upload.default-dir}")
    var resourceLocation: String? = ""

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/images/**")
//                .addResourceLocations("file:/Users/psj/Desktop/SimpleShop/simple-shop-datacenter/images/")
                .addResourceLocations("file:///C:/Users/psj/Desktop/simple-shop-datacenter/images/")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(tokenValidationInterceptor)
                .addPathPatterns("/api/**")
    }


}