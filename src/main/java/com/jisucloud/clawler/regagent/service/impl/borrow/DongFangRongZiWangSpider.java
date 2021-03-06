package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "rongzi.com", 
		message = "东方融资网为你提供专业的贷款、融资服务。找上海、苏州、广州、杭州、合肥、宁波、南京、无锡全国各地贷款公司、中小企业贷款、银行贷款,尽在东方融资网。", 
		platform = "dfrongzi", 
		platformName = "东方融资网", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class DongFangRongZiWangSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.rongzi.com/api/PreRegister";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json"), "{\"RealName\":\"刘女式\",\"CellPhone\":\""+account+"\",\"Gender\":1,\"LoanCity\":1,\"RegisterSourceEnum\":5,\"Platform\":4}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "m.rongzi.com")
					.addHeader("Referer", "https://m.rongzi.com/dlzc?pagesource=1&returnurl=%252Fwd%252F")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("手机号已存在")) {
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