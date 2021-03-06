package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "chunyujinfu.com", 
		message = "春雨金服-稳健透明的互联网金融信息服务平台,该平台提供公平、透明、稳健、高效的互联网金融信息服务.年利率高,了解更多出借、网上贷款等信息请访问chunyujinfu.com。", 
		platform = "chunyujinfu", 
		platformName = "春雨金服", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class ChunYuJinFuSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.chunyujinfu.com/front/validation/checkusermobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.chunyujinfu.com")
					.addHeader("Referer", "http://www.chunyujinfu.com/loginAndRegiste/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("false")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
