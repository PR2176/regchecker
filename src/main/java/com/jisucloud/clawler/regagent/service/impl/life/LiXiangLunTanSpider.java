package com.jisucloud.clawler.regagent.service.impl.life;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "55188.com", 
		message = "理想论坛建立已经超过10年,是最好的炒股论坛,其股票公式,股票软件,研究报告,新手入门,证券图书,炒股秘籍等区是全国最好的,选股公式中包含了股票公式,选股公式,通达信公式,大智慧公式,同花顺公式,飞狐公.", 
		platform = "55188", 
		platformName = "理想论坛", 
		tags = { "论坛", "理财", "股票" }, 
		testTelephones = { "13771025665", "13967075617" })
public class LiXiangLunTanSpider extends PapaSpider {


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "https://passport.55188.com/index/reg?check=mobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://passport.55188.com/index/reg?referer=")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().contains("已被占用");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
