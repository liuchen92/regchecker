package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ChengTieZaiRongSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "城铁在融平台作为全国首家铁路p2p互联网金融平台，利用互联网技术，为投资人和融资人提供直接对接、签订借贷款合同及办理相关法律手续等中介服务，包括但不限于提供信息发布、信用评定、贷中服务及部分贷后管理等第三方服务，并收取相应的服务中介费和管理费。同时，依据监管部门的监管要求,平台作为信息中介进行借贷撮合，自身并不参与交易，投融资人资金委托第三方机构监管。";
	}

	@Override
	public String platform() {
		return "ctzrnet";
	}

	@Override
	public String home() {
		return "ctzrnet.com";
	}

	@Override
	public String platformName() {
		return "城铁在融";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.ctzrnet.com/user/checkUserPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userPhone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "http://www.ctzrnet.com/user/to_register")
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

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15161509916");
	}

}
