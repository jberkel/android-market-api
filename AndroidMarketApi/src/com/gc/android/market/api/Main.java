package com.gc.android.market.api;

import com.gc.android.market.api.model.Market.AppsRequest;
import com.gc.android.market.api.model.Market.CommentsRequest;
import com.gc.android.market.api.model.Market.ResponseContext;

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

			MarketSession.Callback callback = new MarketSession.Callback() {

				@Override
				public void onResult(ResponseContext context, Object response) {
					System.out.println("Response : " + response);
				}
				
			};
			session.append(appsRequest, callback);
			session.flush();
			session.append(commentsRequest, callback);
			session.flush();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
