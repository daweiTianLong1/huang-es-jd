package com.huang;

import com.alibaba.fastjson.JSON;
import com.huang.service.ContentService;
import com.huang.util.FileUtils;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class HuangEsJdApplicationTests {

	@Autowired
	private RestHighLevelClient restHighLevelClient;


	@Test
	public void test(){



	}



	@Test
	void contextLoads() {
	}

//	@Test
//	void test1() throws Exception {
//		System.out.println(contentService.parseContent("java"));
//	}


	@Test
	void test2() throws Exception {
//		System.out.println(contentService.searchPage("一舞难忘"));
	}

	@Test
	void searchDocment() throws IOException {
		SearchRequest request = new SearchRequest("hibymusic_index");
		//构建搜索条件
		final SearchSourceBuilder builder = new SearchSourceBuilder();


		//查询条件，我们可以使用QueryBuilders工具类来实现
		// QueryBuilders.termQuery  ：  精确匹配
//		 QueryBuilders.matchAllQuery() ： 匹配所有
//		final TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "爱情");
//		FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("title", "爱情");
		QueryBuilder queryBuilders = QueryBuilders.boolQuery()
				.should(QueryBuilders.wildcardQuery("title","凤飞飞"))
				.should(QueryBuilders.wildcardQuery("artist","凤飞飞"))
				.should(QueryBuilders.wildcardQuery("album","凤飞飞"))
				.should(QueryBuilders.matchQuery("lyric","凤飞飞"));
		builder.query(queryBuilders);
		builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		request.source(builder);
		final SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
		System.out.println(JSON.toJSONString(search.getHits()));
		for (SearchHit documentFields : search.getHits().getHits()){
			System.out.println(documentFields.getSourceAsMap());
		}

	}

	/*
		MultiSearchRequest api的使用
	 */

	@Test
	public void testBoolQuery()  {

		String filePath = "C:\\Users\\Admin\\Desktop\\1000000\\1000000";

		File file = new File(filePath);
		File[] fs= file.listFiles();
		int i = 0;
		int count = 0;
		String encoding = null;
		for(File f : fs){
		//如果是路径，那么是检查一个对象是否是文件夹
			if(f.isDirectory()){
				System.out.println(f);
			}
			try {
				Path path = Paths.get(f.getPath());
				System.out.println(path);
				byte[] data = Files.readAllBytes(path);
				CharsetDetector detector = new CharsetDetector();
				detector.setText(data);
				CharsetMatch match = detector.detect();
				if (match == null) {
//					return encoding;
					System.out.println(encoding);
				}

				encoding = match.getName();
				System.out.println(encoding);
				switch (encoding){

					case "UTF-8":
						i++;
						break;
					case "ISO-8859-1":
						count++;
						break;
					default:
						break;
				}



			} catch (IOException var6) {
//				logger.error(var6.getMessage());
			}


		}
		System.out.println("UTF-8格式的文件数量为：" + i);
		System.out.println("ISO-8859-1格式的文件数量为：" + count);

	}

	@Test
	public void test9(){


		String path = "C:\\Users\\Admin\\Desktop\\123";
		File file = new File(path);

		File[] files = file.listFiles();
		for(File f : files){

			try {
				System.out.println(f);
				Path path1 = Paths.get(f.getPath());
				byte[] data = Files.readAllBytes(path1);
				CharsetDetector detector = new CharsetDetector();
				detector.setText(data);
				CharsetMatch match = detector.detect();
				String encoding = match.getName();
				System.out.println(encoding);
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(f), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String line;
				while((line = bufferedReader.readLine())!=null){
					System.out.println(line);
				}
				read.close();


			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}



}













