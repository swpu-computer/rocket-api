package com.github.alenfive.rocketapi.function;

import com.github.alenfive.rocketapi.datasource.DataSourceManager;
import com.github.alenfive.rocketapi.extend.ApiInfoContent;
import com.github.alenfive.rocketapi.service.ScriptParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 数据库操作函数
 */
@Component
@Slf4j
public class DbFunction implements IFunction{

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private ApiInfoContent apiInfoContent;

    @Autowired
    private ScriptParseService parseService;

    @Override
    public String getVarName() {
        return "db";
    }

    public Long count(String script,String dataSource) throws Exception {
        List<Map<String,Object>> list = find(script,dataSource);
       if (CollectionUtils.isEmpty(list))return 0L;

       Object count = list.get(0).values().toArray()[0];
       if (count == null){
           count = list.size();
       }
       return Long.valueOf(count.toString());
    }

    public Map<String,Object> findOne(String script,String dataSource) throws Exception {
        List<Map<String,Object>> list = find(script,dataSource);
        if (list.size() == 0)return null;
        return list.get(0);
    }

    public List<Map<String,Object>> find(String script,String dataSource) throws Exception {
        StringBuilder sbScript = new StringBuilder(script);
        parseService.parse(sbScript,apiInfoContent.getApiParams());
        List<Map<String,Object>> result = dataSourceManager.find(sbScript,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource);
        if (apiInfoContent.getIsDebug()){
            apiInfoContent.putLog("generate script:  " + sbScript);
        }
        log.info("generate script:{}",sbScript);
        return result;
    }

    public Object insert(String script,String dataSource) throws Exception {
        StringBuilder sbScript = new StringBuilder(script);
        parseService.parse(sbScript,apiInfoContent.getApiParams());
        Object result = dataSourceManager.insert(sbScript,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource);
        if (apiInfoContent.getIsDebug()){
            apiInfoContent.putLog("generate script:  " + sbScript);
        }
        log.info("generate script:{}",sbScript);
        return result;
    }

    public Object remove(String script,String dataSource) throws Exception {
        StringBuilder sbScript = new StringBuilder(script);
        parseService.parse(sbScript,apiInfoContent.getApiParams());
        Object result =  dataSourceManager.remove(sbScript,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource);
        if (apiInfoContent.getIsDebug()){
            apiInfoContent.putLog("generate script:  " + sbScript);
        }
        log.info("generate script:{}",sbScript);
        return result;
    }

    public Long update(String script,String dataSource) throws Exception {
        StringBuilder sbScript = new StringBuilder(script);
        parseService.parse(sbScript,apiInfoContent.getApiParams());
        Long result =  dataSourceManager.update(sbScript,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource);
        if (apiInfoContent.getIsDebug()){
            apiInfoContent.putLog("generate script:  " + sbScript);
        }
        log.info("generate script:{}",sbScript);
        return result;
    }

    public Long count(String script) throws Exception {
        return this.count(script,null);
    }

    public Map<String,Object> findOne(String script) throws Exception {
        return this.findOne(script,null);
    }

    public List<Map<String,Object>> find(String script) throws Exception {
        return this.find(script,null);
    }

    public Object insert(String script) throws Exception {
        return this.insert(script,null);
    }

    public Object remove(String script) throws Exception {
        return this.remove(script,null);
    }

    public Long update(String script) throws Exception {
        return this.update(script,null);
    }

}