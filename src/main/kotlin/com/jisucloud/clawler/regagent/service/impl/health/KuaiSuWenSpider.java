package com.jisucloud.clawler.regagent.service.impl.health;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class KuaiSuWenSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "快速问医生旗下有问必答网是医生在线健康问答咨询平台。来自全国数万名医生为您免费解答任何健康问题,可以通过电话、文字等多种方式与医生进行一对一咨询!";
	}

	@Override
	public String platform() {
		return "120ask";
	}

	@Override
	public String home() {
		return "120ask.com";
	}

	@Override
	public String platformName() {
		return "快速问医生";
	}

	@Override
	public String[] tags() {
		return new String[] {"医疗", "生活应用" , "用药"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new KuaiSuWenSpider().checkTelephone("18210538513"));
//		System.out.println(new KuaiSuWenSpider().checkTelephone("18210530000"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://sso.120ask.com/user/name_exist";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .add("type", "reg")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "sso.120ask.com")
					.addHeader("Referer", "https://sso.120ask.com/user/register?source=ask&forward=https://www.120ask.com/")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			JSONObject result = JSON.parseObject(res);
			if (result.getJSONObject("data").getBooleanValue("exist")) {
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
