package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "gzjkp2p.com", 
		message = "广金金服是广州市国资委辖下广州金融控股集团控股,国资背景的P2P网贷平台,稳健可靠,投资人至今零损失,平台利用先进金融科技,解决中小微企业和个人融资需求,服务社会!", 
		platform = "gzjkp2p", 
		platformName = "广金金服", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18212345678", "15161509916" })
public class GuangJinJinFuSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://www.gzjkp2p.com/querytraninput.do");
			chromeDriver.addAjaxHook(this);smartSleep(2000);
			chromeDriver.findElementByCssSelector("#userName").sendKeys(account);
			chromeDriver.findElementByCssSelector("#userRoleInvestor").click();
			chromeDriver.findElementByLinkText("发送验证码").click();smartSleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTel;
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
	public HookTracker getHookTracker() {
		// TODO Auto-generated method stub
		return HookTracker.builder().addUrl("sendForgetPwdSms.do").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	boolean checkTel;
	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = !contents.getTextContents().equals("2");
	}

}
