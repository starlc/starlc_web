/**
 * 
 */
package com.starlc.cache.mongdb.util;

/**
 * @author liucheng2
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

/**
 * MongoDB Document查询条件构建器
 * 
 * <p>
 * mongoDB shell脚本示例1:等于查询,select * from entity where modelId = 'entity' 
 * </p>
 * 
 * <pre>
 * db.getCollection("entity").find(
 *     { 
 *         "modelId" : "entity"
 *     }
 * );
 * </pre>
 * 
 * mongoDB shell脚本示例2:大于查询,select * from entity where modelId > 'entity' 
 * 
 * <pre>
 * db.getCollection("entity").find(
 *     { 
 *         "modelId" : {
 *             "$gt" : "entity"
 *         }
 *     }
 * );
 * </pre>
 * 
 * mongoDB shell脚本示例3:小于查询,select * from entity where modelId < 'entity' 
 * 
 * <pre>
 * db.getCollection("entity").find(
 *     { 
 *         "modelId" : {
 *             "$lt" : "entity"
 *         }
 *     }
 * );
 * </pre>
 * 
 * mongoDB shell脚本示例4:小于等于查询,select * from entity where modelId <= 'entity' 
 * <pre>
 * db.getCollection("entity").find(
    { 
        "modelId" : {
            "$lte" : "entity"
        }
    }
   );
 * </pre>
 * 
 * mongoDB shell脚本示例5:大于等于查询,select * from entity where modelId >= 'entity' 
 * <pre>
 * db.getCollection("entity").find(
    { 
        "modelId" : {
            "$gte" : "entity"
        }
    }
);
 * </pre>
 * 
 * mongoDB shell脚本示例6:模糊查询,select * from entity where modelId like '%entity%' 
 * <pre>
 * db.getCollection("entity").find(
    { 
        "modelId" : /^.*entity.*$/i
    }
);
 * </pre>
 * 
 * mongoDB shell脚本示例7:不等于,select * from entity where modelId != 'entity'
 * <pre>
 * db.getCollection("entity").find(
    { 
        "modelId" : {
            "$ne" : "entity"
        }
    }
);
 * </pre> 
 * 
 * mongoDB shell脚本示例8:OR查询,select * from entity where modelId ='entity' or modelPackage='xxxx' 
 * <pre>
 * db.getCollection("entity").find(
    { 
        "$or" : [
            {
                "modelId" : "entity"
            }, 
            {
                "modelPackage" : "xxxx"
            }
        ]
    }
);
 * </pre>
 * 
 * mongoDB shell脚本示例9:AND查询,select * from entity where modelId ='entity' AND modelPackage='xxxx' 
 * <pre>
 db.getCollection("entity").find(
    { 
        "modelId" : "entity", 
        "modelPackage" : "xxxx"
    }
 *);
 *
 *或者
 *db.getCollection("entity").find(
    { 
        "$and" : [
            {
                "modelId" : "entity"
            }, 
            {
                "modelPackage" : "xxxx"
            }
        ]
    }
);
 * </pre>
 * 
 * mongoDB shell脚本示例10:查询指定某列,select modelId from entity where modelId ='entity' 
 * <pre>
 * db.getCollection("entity").find(
    { 
        "modelId" : "entity"
    }, 
    { 
        "modelId" : "$modelId"
    }
  );
 * </pre>
 * 
 * mongoDB shell脚本示例11:IN查询,select * from entity where modelId in ('entity')
 * <pre>
 * db.getCollection("entity").find(
    { 
        "modelId" : {
            "$in" : [
                "entity"
            ]
        }
    }
  );
 * </pre> 
 * 
 * 详细调用示例类@see comtop.cap.bm.metadata.cache.entity.EntityMongodbOperator
 * 
 * @author starlc
 * @since JDK1.7
 * @version 2018年7月19日 starlc 新建
 */
public class DocumentBuilder extends Document {
    
    /**
     * MongoDB字段>大于匹配
     * 
     * <p>
     * SQL如:select * from entity where modelId > 'entity'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *  DocumentBuilder builder = new DocumentBuilder();
     *  builder.greaterThan("modelId","entity");
     * </pre>
     * 
     * @param key 属性字段
     * @param value 值
     * @return Document
     */
    public DocumentBuilder greaterThan(String key, Object value) {
        this.append(key, new Document().append(MongodbOperator.GREATER_THAN.getValue(), value));
        return this;
    }
    
    /**
     * MongoDB字段<小于匹配
     * <p>
     * SQL如: select * from entity where modelId <= 'entity'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *  DocumentBuilder builder = new DocumentBuilder();
     *  builder.lessThanEquals("modelId","entity");
     * </pre>
     * 
     * @param key 属性字段
     * @param value 值
     * @return Document
     */
    public DocumentBuilder lessThan(String key, Object value) {
        this.append(key, new Document().append(MongodbOperator.LESS_THAN.getValue(), value));
        return this;
    }
    
    /**
     * MongoDB字段=等于匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId = 'entity'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *  DocumentBuilder builder = new DocumentBuilder();
     *  builder.equals("modelId","entity");
     * </pre>
     * 
     * @param key 属性字段
     * @param value 值
     * @return Document
     */
    public DocumentBuilder equals(String key, Object value) {
        this.append(key, value);
        return this;
    }
    
    /**
     * MongoDB字段!=不等于匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId != 'entity'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *  DocumentBuilder builder = new DocumentBuilder();
     *  builder.notEquals("modelId","entity");
     * </pre>
     * 
     * @param key 属性字段
     * @param value 值
     * @return Document
     */
    public DocumentBuilder notEquals(String key, Object value) {
        this.append(key, new Document().append(MongodbOperator.NOT_EQUALS.getValue(), value));
        return this;
    }
    
    /**
     * MongoDB字段>=大于等于匹配
     * 
     * @param key 属性字段
     * @param value 值
     * @return Document
     */
    public DocumentBuilder greaterThanEquals(String key, Object value) {
        this.append(key, new Document().append(MongodbOperator.GREATER_THAN_EQUALS.getValue(), value));
        return this;
    }
    
    /**
     * MongoDB字段<=小于等于匹配
     * 
     * @param key 属性字段
     * @param value 值
     * @return Document
     */
    public DocumentBuilder lessThanEquals(String key, Object value) {
        this.append(key, new Document().append(MongodbOperator.LESS_THAN_EQUALS.getValue(), value));
        return this;
    }
    
    /**
     * MongoDB字段like全模糊匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId like '%entity%'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *  DocumentBuilder builder = new DocumentBuilder();
     *  builder.like("modelId","entity");
     * </pre>
     * 
     * @param key 属性字段
     * @param value 值
     * @return Document
     */
    public DocumentBuilder like(String key, Object value) {
        this.append(key, MongoDBQueryUtil.getAllLikePattern(value));
        return this;
    }
    
    /**
     * 左模糊匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId like '%entity'
     * </p>
     * 
     * @param key 属性字段
     * @param value 值
     * @return Document
     */
    public DocumentBuilder leftLike(String key, Object value) {
        this.append(key, MongoDBQueryUtil.getLeftLikePattern(value));
        return this;
    }
    
    /**
     * 右模糊匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId like 'entity%'
     * </p>
     * 
     * @param key 属性字段
     * @param value 值
     * @return Document
     */
    public DocumentBuilder rightLike(String key, Object value) {
        this.append(key, MongoDBQueryUtil.getRightLikePattern(value));
        return this;
    }
    
    /**
     * OR查询匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId = 'entityId' or modelName='entityName'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *         DocumentBuilder builder = new DocumentBuilder();
     *         builder.or(new DocumentBuilder[] { new DocumentBuilder().equals("modelId", "entityId"),
     *             new DocumentBuilder().equals("modelName", "entityName") });
     * </pre>
     * 
     * @param documents 查询条件集合
     * @return Document
     */
    public DocumentBuilder or(Document[] documents) {
        this.append(MongodbOperator.OR.getValue(), Arrays.asList(documents));
        return this;
    }
    
    /**
     * OR查询匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId = 'entityId' or modelName='entityName'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *         DocumentBuilder builder = new DocumentBuilder();
     *         List<DocumentBuilder> orList = new ArrayList<DocumentBuilder>();
     *         orList.add(new DocumentBuilder().equals("modelId", "entityId"));
     *         orList.add(new DocumentBuilder().equals("modelName", "entityName"));
     *         builder.or(orList);
     * </pre>
     * 
     * @param documents 查询条件集合
     * @return Document
     */
    public DocumentBuilder or(List<DocumentBuilder> documents) {
        this.append(MongodbOperator.OR.getValue(), documents);
        return this;
    }
    
    /**
     * OR查询匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId = 'entityId' or modelName='entityName'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *         DocumentBuilder builder = new DocumentBuilder();
     *         builder.or(new DocumentBuilder().equals("modelId", "entityId"));
     *         builder.or(new DocumentBuilder().equals("modelName", "entityName"));
     * </pre>
     * 
     * @param document 查询条件对象
     * @return Document
     */
    @SuppressWarnings("unchecked")
    public DocumentBuilder or(Document document) {
        Object value = this.get(MongodbOperator.OR.getValue());
        if (value == null) {
            List<Document> list = new ArrayList<Document>();
            list.add(document);
            this.append(MongodbOperator.OR.getValue(), list);
        } else {
            ((List<Document>) value).add(document);
        }
        return this;
    }
    
    /**
     * AND查询匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId = 'entityId' and modelName='entityName'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *         DocumentBuilder builder = new DocumentBuilder();
     *         builder.and(new DocumentBuilder[] { new DocumentBuilder().equals("modelId", "entityId"),
     *             new DocumentBuilder().equals("modelName", "entityName") });
     * </pre>
     * 
     * @param documents 查询条件集合
     * @return Document
     */
    public DocumentBuilder and(Document[] documents) {
        this.append(MongodbOperator.AND.getValue(), Arrays.asList(documents));
        return this;
    }
    
    /**
     * AND查询匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId = 'entityId' and modelName='entityName'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *         DocumentBuilder builder = new DocumentBuilder();
     *         List<DocumentBuilder> andList = new ArrayList<DocumentBuilder>();
     *         andList.add(new DocumentBuilder().equals("modelId", "entityId"));
     *         andList.add(new DocumentBuilder().equals("modelName", "entityName"));
     *         builder.and(andList);
     * </pre>
     * 
     * @param documents 查询条件集合
     * @return Document
     */
    public DocumentBuilder and(List<DocumentBuilder> documents) {
        this.append(MongodbOperator.AND.getValue(), documents);
        return this;
    }
    
    /**
     * AND查询匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId = 'entityId' and modelName='entityName'
     * </p>
     * 
     * <pre>
     * 调用示例:
     *         DocumentBuilder builder = new DocumentBuilder();
     *         builder.and(new DocumentBuilder().equals("modelId", "entityId"));
     *         builder.and(new DocumentBuilder().equals("modelName", "entityName"));
     * </pre>
     * 
     * @param document 查询条件对象
     * @return Document
     */
    @SuppressWarnings("unchecked")
    public DocumentBuilder and(Document document) {
        Object value = this.get(MongodbOperator.AND.getValue());
        if (value == null) {
            List<Document> list = new ArrayList<Document>();
            list.add(document);
            this.append(MongodbOperator.AND.getValue(), list);
        } else {
            ((List<Document>) value).add(document);
        }
        return this;
    }
    
    /**
     * IN查询匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId in ('entityId','pageId')
     * </p>
     * 
     * <pre>
     * 调用示例:
     *         DocumentBuilder builder = new DocumentBuilder();
     *         builder.in("modelId", new Object[] { "entityId", "pageId" });
     * </pre>
     * 
     * @param key 属性字段
     * @param values 值集合
     * @return Document
     */
    public DocumentBuilder in(String key, Object[] values) {
        this.append(key, new Document().append(MongodbOperator.IN.getValue(), Arrays.asList(values)));
        return this;
    }
    
    /**
     * IN查询匹配
     * 
     * <p>
     * SQL如: select * from entity where modelId in ('entityId','pageId')
     * </p>
     * 
     * <pre>
     * 调用示例:
     *         DocumentBuilder builder = new DocumentBuilder();
     *         List<Object> inList = new ArrayList<Object>();
     *         inList.add("entityId");
     *         inList.add("pageId");
     *         builder.in("modelId", inList);
     * </pre>
     * 
     * @param key 属性字段
     * @param values 值集合
     * @return Document
     */
    public DocumentBuilder in(String key, List<Object> values) {
        this.append(key, new Document().append(MongodbOperator.IN.getValue(), values));
        return this;
    }
}

