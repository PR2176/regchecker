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
		home = "douban.com", 
		message = "豆瓣（douban）是一个社区网站。网站由杨勃（网名“阿北”） 创立于2005年3月6日。该网站以书影音起家，提供关于书籍、电影、音乐等作品的信息，无论描述还是评论都由用户提供（User-generated content，UGC），是Web 2.0网站中具有特色的一个网站。", 
		platform = "douban", 
		platformName = "doubanName", 
		tags = { "社区", "影音" , "阅读" }, 
		testTelephones = { "13925306966", "18212345678" })
public class DouBanSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://accounts.douban.com/j/mobile/reset_password/request_phone_code";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("number", account)
	                .add("ck", "")
	                .add("area_code", "+86")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "accounts.douban.com")
					.addHeader("Referer", "https://accounts.douban.com/passport/get_password")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("success")) {
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
