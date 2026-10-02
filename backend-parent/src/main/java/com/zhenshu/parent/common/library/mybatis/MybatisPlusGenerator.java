package com.zhenshu.parent.common.library.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.zhenshu.parent.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author jing
 * @version 1.0
 * @desc 之所以这样改造是为了能从配置文件里读取配置，避免把数据库账号密码写在代码里
 * @date 2020/5/25 0025 11:01
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MybatisPlusGenerator {

    @Resource
    private MybatisPlusConfig plusConfig;

    // 需要生成的表名
    private static final String[] TABLE_NAMES = {"kg_guardian", "mini_login", "kg_deal_derate", "kg_deal_pay", "kg_deal_refund", "kg_payment_deal", "kg_payment_project", "kg_payment_project_details", "kg_student"};
    // 本地的代码目录
    private static final String PATH = "F:\\code\\JAVA\\JavaHome\\KgParent\\src\\main\\java";
    private static final String TABLE_PREFIX = "kg";
    private static final String PARENT = "com.zhenshu.parent.app";
    private static final String CONTROLLER = "controller";
    private static final String SERVICE = "service";
    private static final String MAPPER = "mapper";
    private static final String SERVICE_IMPL = "service.impl";
    private static final String DOMAIN = "domain";

    @Test
    public void generateByTables() {
        GlobalConfig config = new GlobalConfig();
        config.setIdType(IdType.AUTO).setFileOverride(true).setServiceName("%sService")
                .setControllerName("%sController")
                .setMapperName("%sMapper")
                .setServiceImplName("%sServiceImpl")
                .setXmlName("%sMapper")
                .setAuthor("jing").setBaseColumnList(true);
        // 数据库连接地址
        String dbUrl = plusConfig.getDbUrl();
        // 数据库配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl).setUsername(plusConfig.getUserName()).setPassword(plusConfig.getPassword()).setDriverName("com.mysql.cj.jdbc.Driver");

        // 表结构配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true).setEntityLombokModel(true).setNaming(NamingStrategy.underline_to_camel).setInclude(TABLE_NAMES).setTablePrefix(TABLE_PREFIX);
        config.setActiveRecord(false).setFileOverride(true).setEnableCache(false).setOutputDir(PATH);

        // 包名配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(PARENT).setController(CONTROLLER).setEntity(DOMAIN).setService(SERVICE).setMapper(MAPPER).setServiceImpl(SERVICE_IMPL).setXml(MAPPER);
        // 集成
        new AutoGenerator().setGlobalConfig(config).setDataSource(dataSourceConfig).setStrategy(strategyConfig).setPackageInfo(packageConfig).execute();
    }

}
