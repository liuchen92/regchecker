package com.jisucloud.clawler.regagent.service.impl.b2b;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "sonhoo.com", 
		message = "商虎中国(www.sonhoo.com)是专业的供求信息免费发布平台,在这里你可以查到供应厂家,生产企业,采购商家,各类商机供求,是企业B2B免费信息发布平台。", 
		platform = "sonhoo", 
		platformName = "商虎中国", 
		tags = { "b2b" ,"商机" ,"生意" }, 
		testTelephones = { "18212345678", "13925306966" })
public class ShangHuZhongGuoSpider extends PapaSpider {

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://login.sonhoo.com/login/sonhooreg/html/aj/default.aspx";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("act", "judgeMobile")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "login.sonhoo.com")
					.addHeader("Referer", "http://login.sonhoo.com/login/sonhooreg/html/register.aspx")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("error\":0")) {
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
