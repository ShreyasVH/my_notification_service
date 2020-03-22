package controllers;

import play.mvc.Result;
import play.libs.Json;
import play.mvc.Http;
import com.google.inject.Inject;
import requests.MailRequest;
import services.IndexService;
import utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class IndexController extends BaseController
{
	private final IndexService indexService;
	
	@Inject
	public IndexController
	(
		IndexService indexService
	)
	{
		this.indexService = indexService;
	}

	public Result index()
    {
        return ok("INDEX");
    }

	public Result post(Http.Request request)
	{
		MailRequest mailRequest;

		try
		{
			mailRequest = Utils.convertObject(request.body().asJson(), MailRequest.class);
		}
		catch(Exception ex)
		{
			throw new RuntimeException(ex.getMessage());
		}

		boolean isSuccess = this.indexService.sendMail(mailRequest);
		Map<String, Boolean> resultMap = new HashMap<>();
		resultMap.put("success", isSuccess);
		return ok(Json.toJson(resultMap));
	}
}