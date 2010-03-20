package com.gc.android.market.api;

import java.io.FileOutputStream;

import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.AppsRequest;
import com.gc.android.market.api.model.Market.CommentsRequest;
import com.gc.android.market.api.model.Market.GetImageRequest;
import com.gc.android.market.api.model.Market.GetImageResponse;
import com.gc.android.market.api.model.Market.ResponseContext;
import com.gc.android.market.api.model.Market.GetImageRequest.AppImageUsage;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if(args.length < 2) {
				System.out.println("Usage :\n" +
						"market email password query");
				return;
			}
		

			String login = args[0];
			String password = args[1];
			String query = args.length > 2 ? args[2] : "Test";

			MarketSession session = new MarketSession();
			System.out.println("Login...");
			session.login(login,password);
			System.out.println("Login done");
	
			AppsRequest appsRequest = AppsRequest.newBuilder()
				.setQuery(query)
				.setStartIndex(2).setEntriesCount(10)
				.setWithExtendedInfo(true)
				.build();
			
			CommentsRequest commentsRequest = CommentsRequest.newBuilder()
				.setAppId("7065399193137006744")
				.setStartIndex(0)
				.setEntriesCount(10)
				.build();
			
			//

			GetImageRequest imgReq = GetImageRequest.newBuilder().setAppId("-7934792861962808905")
				.setImageUsage(AppImageUsage.SCREENSHOT)
				.setImageId("1")
				.build();
			
			MarketSession.Callback callback = new MarketSession.Callback() {

				@Override
				public void onResult(ResponseContext context, Object response) {
					System.out.println("Response : " + response);
				}
				
			};
			session.append(appsRequest, callback);
			session.flush();
			session.append(imgReq, new Callback<GetImageResponse>() {
				
				@Override
				public void onResult(ResponseContext context, GetImageResponse response) {
					try {
						FileOutputStream fos = new FileOutputStream("icon.png");
						fos.write(response.getImageData().toByteArray());
						fos.close();
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			session.flush();
			session.append(commentsRequest, callback);
			session.flush();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
