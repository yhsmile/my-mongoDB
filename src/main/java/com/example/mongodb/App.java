package com.example.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertManyOptions;

/**
 * Hello world!
 *
 */
public class App {
	
	
	private static MongoDatabase database = null;
	
	public static MongoDatabase connectMongoDB(){
		
		try {
			
			//连接到 MongoDB 服务
			MongoClient client = new MongoClient("127.0.0.1", 27017);
			
			
			//连接到数据库
			database = client.getDatabase("smile");
			
			System.out.println("MongoDB 连接成功");
			
		} catch (Exception e) {
			
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
		}
		
		return database;
	}
	
	
	
	//创建集合
	public static void createCollection(){
		
		database = connectMongoDB();
		
		database.createCollection("test");
		
		
		System.out.println("Collection 集合创建成功");
	}
	
	
	//获取集合
	public static MongoCollection<Document> getCollection(){
		
		database = connectMongoDB();
		
		MongoCollection<Document> collection =  database.getCollection("test");
		
		System.out.println("Collection test 集合获取成功");
		
		return collection;
	}
	
	//插入文档
	public static void insertDocument(){
		
		MongoCollection<Document> collection = getCollection();
		
		Document doc = new Document("title","mongoDB").append("description", "dataBase").append("author", "zhanSan");
		List<Document> list = new ArrayList<Document>();
		list.add(doc);
		
		
		InsertManyOptions options = new InsertManyOptions();
		options.ordered(true);
		options.bypassDocumentValidation(false);
		 
		collection.insertMany(list,options);
		
		System.out.println("插入文档成功");
	}	
	
	//查询文档
	public static void selectDocument(){
		
		MongoCollection<Document> collection = getCollection();
		//获取迭代器FindIterable<Document>
		FindIterable<Document> findIterable = collection.find();  
		//获取游标MongoCursor<Document>
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        //通过游标遍历检索出的文档集合
        while(mongoCursor.hasNext()){  
           System.out.println(mongoCursor.next());  
        }
        
	}
	
	//修改文档
	public static void updateDocument(){
		MongoCollection<Document> collection = getCollection();
		//修改的内容
		Document newDoc = new Document("author", "liSi");
		//$set表示修改操作
		Document upDoc = new Document("$set", newDoc);
		
		collection.updateMany(Filters.eq("author","zhanSan"), upDoc);
		
		System.out.println("修改文档内容成功");
		
		
	}
	
	//删除文档
	public static void deleteDocument(){
		MongoCollection<Document> collection = getCollection();
		
		//删除符合条件的第一个文档
		collection.deleteOne(Filters.eq("author", "liSi"));
		
		//删除所有符合条件的文档
		collection.deleteMany(Filters.eq("author", "liSi"));
		
		
		System.out.println("删除文档成功");
	}
	
	
    public static void main( String[] args ){
        System.out.println( "Hello World!" );
   //     connectMongoDB();
 //       createCollection();
        insertDocument();
      //  updateDocument();
          selectDocument();
      //    deleteDocument();
    }
}
